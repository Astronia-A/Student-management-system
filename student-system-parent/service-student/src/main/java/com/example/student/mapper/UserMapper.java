package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 继承了 BaseMapper 就自动拥有了 insert, delete, update, select 等所有方法
}