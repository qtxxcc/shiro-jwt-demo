package com.example.shiro.demo.demo.token;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Description JwtToken
 * @Author wanghanbin
 * @Date 2020/4/22 10:55
 * @Version 1.0
 **/
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
