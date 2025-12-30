package com.example.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.entity.User;
import com.example.student.mapper.UserMapper;
import com.example.student.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 这里处理的是 User
}