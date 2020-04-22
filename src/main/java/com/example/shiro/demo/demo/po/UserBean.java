package com.example.shiro.demo.demo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description UserBean
 * @Author wanghanbin
 * @Date 2020/4/22 10:11
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBean {
    private String userName;
    private String password;
    private String role;
    private String permission;
}
