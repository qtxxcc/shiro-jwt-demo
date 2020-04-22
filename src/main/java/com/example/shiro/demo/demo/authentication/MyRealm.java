package com.example.shiro.demo.demo.authentication;

import com.example.shiro.demo.demo.po.UserBean;
import com.example.shiro.demo.demo.token.JwtToken;
import com.example.shiro.demo.demo.utils.JwtUtil;
import com.example.shiro.demo.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description MyRealm
 * @Author wanghanbin
 * @Date 2020/4/22 10:56
 * @Version 1.0
 **/
@Slf4j
@Service
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     * @description 默认使用此方法进行用户名正确与否验证，错误抛出异常即可
     * @author wanghanbin
     * @date 2020/4/22 11:06
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUserName(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        UserBean userBean = userService.getUser(username);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (!JwtUtil.verify(token, username, userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    /**
     * @description 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * @author wanghanbin
     * @date 2020/4/22 11:06
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtil.getUserName(principals.toString());
        UserBean user = userService.getUser(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole());
        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }
}
