package com.example.demo.service;

import com.example.demo.vo.User;

public interface userService {
    public User retrieveUserInfo(User user);

    public User retrieveUserInfo(String user);
}
