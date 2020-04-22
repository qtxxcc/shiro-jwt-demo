package com.example.shiro.demo.demo.web;

import com.example.shiro.demo.demo.po.UserBean;
import com.example.shiro.demo.demo.service.UserService;
import com.example.shiro.demo.demo.utils.JwtUtil;
import com.example.shiro.demo.demo.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description WebController
 * @Author wanghanbin
 * @Date 2020/4/22 10:43
 * @Version 1.0
 **/
@RestController
public class WebController {
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;


    @PostMapping("/login")
    public RestResult login(@RequestParam("userName") String userName,
                            @RequestParam("password") String password) {
        UserBean userBean = userService.getUser(userName);
        if (userBean != null && userBean.getPassword().equals(password)) {
            String token = JwtUtil.sign(userName, password);
            redisUtil.set("token" + token, token, JwtUtil.EXPIRE_TIME / 1000);
            return new RestResult(200, "SUCCESS", token);
        } else {
            return new RestResult(400, "FAILD");
        }
    }

    @GetMapping("/article")
    public RestResult article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new RestResult(200, "You are already logged in");
        } else {
            return new RestResult(200, "You are guest");
        }
    }


    @GetMapping("/require_auth")
    @RequiresAuthentication
    public RestResult requireAuth() {
        return new RestResult(200, "You are requireAuth");
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public RestResult requireRole() {
        return new RestResult(200, "You are require_role");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view,edit"})
    public RestResult requirePermission() {
        return new RestResult(200, "You are visiting  permission ");
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResult unauthorized() {
        return new RestResult(200, "unauthorized");
    }

}
