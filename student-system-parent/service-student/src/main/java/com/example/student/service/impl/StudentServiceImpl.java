package com.example.student.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.entity.Student;
import com.example.student.mapper.StudentMapper;
import com.example.student.service.StudentService;
import org.springframework.stereotype.Service;
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService { }