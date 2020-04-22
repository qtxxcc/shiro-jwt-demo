package com.example.shiro.demo.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Description JwtUtil
 * @Author wanghanbin
 * @Date 2020/4/22 10:22
 * @Version 1.0
 **/
public class JwtUtil {

    public static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * @description 校验token是否正确
     * @author wanghanbin
     * @date 2020/4/22 10:26
     **/
    public static boolean verify(String token, String userName, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withClaim("userName", userName)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @description 获得token中的信息无需secret解密也能获得
     * @author wanghanbin
     * @date 2020/4/22 10:27
     **/
    public static String getUserName(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("userName").asString();
    }

    /**
     * @description 生成签名, 5min后过期
     * @author wanghanbin
     * @date 2020/4/22 10:28
     **/
    public static String sign(String userName, String password) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return JWT.create()
                .withClaim("userName", userName)
                .withExpiresAt(date)
                .sign(algorithm);
    }

}
