package com.example.shiro.demo.demo.service;

import com.example.shiro.demo.demo.po.UserBean;
import com.example.shiro.demo.demo.utils.DataSourceUtil;
import org.springframework.stereotype.Component;

/**
 * @Description UserService
 * @Author wanghanbin
 * @Date 2020/4/22 10:10
 * @Version 1.0
 **/
@Component
public class UserService {
    public UserBean getUser(String userName) {
        if (DataSourceUtil.getData(userName) == null) {
            return null;
        }
        return DataSourceUtil.getData(userName);
    }
}
