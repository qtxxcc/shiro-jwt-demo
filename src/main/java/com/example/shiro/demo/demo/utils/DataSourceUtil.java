package com.example.shiro.demo.demo.utils;

import com.example.shiro.demo.demo.po.UserBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description DataSourceUtil
 * @Author wanghanbin
 * @Date 2020/4/22 10:14
 * @Version 1.0
 **/
public class DataSourceUtil {

    private static Map<String, UserBean> userBeanMap;

    public static UserBean getData(String userName) {
        if (userBeanMap == null) {
            userBeanMap = new HashMap<>();
            UserBean userBean = UserBean.builder()
                    .userName("admin")
                    .password("admin123")
                    .role("admin")
                    .permission("view,edit")
                    .build();
            UserBean userBean2 = UserBean.builder()
                    .userName("user")
                    .password("user123")
                    .role("user")
                    .permission("view")
                    .build();
            userBeanMap.put(userBean.getUserName(), userBean);
            userBeanMap.put(userBean2.getUserName(), userBean2);
        }
        return userBeanMap.get(userName);
    }
}
