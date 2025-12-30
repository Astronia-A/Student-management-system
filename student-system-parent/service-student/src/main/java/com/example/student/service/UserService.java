package com.example.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.student.entity.User;

public interface UserService extends IService<User> {
    // 继承 IService 可以直接获得 MyBatis-Plus 提供的强大业务方法
}