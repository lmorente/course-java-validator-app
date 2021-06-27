package com.demo.validatorapp.authToken.service.impl;

import com.demo.validatorapp.authToken.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Value(value="${user.username}")
    private String user;

    @Value(value="${user.password}")
    private String pass;

    @Override
    public Boolean validateUserGeneric(String username, String pwd) {
        return user.equals(username) && pass.equals(pwd);
    }
}
