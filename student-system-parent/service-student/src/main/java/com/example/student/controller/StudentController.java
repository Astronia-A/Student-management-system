package com.example.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 1. 分页模糊查询 (中等难度重点)
    // 请求示例：/student/list?current=1&size=10&name=张
    @GetMapping("/list")
    public Result getStudentPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name) {

        // 创建分页对象
        Page<Student> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like(Student::getName, name); // 模糊查询
        }
        wrapper.orderByDesc(Student::getCreateTime); // 按时间倒序

        Page<Student> resultPage = studentService.page(page, wrapper);
        return Result.ok(resultPage);
    }

    // 2. 新增学生
    @PostMapping("/add")
    public Result addStudent(@RequestBody Student student) {
        boolean saved = studentService.save(student);
        return saved ? Result.ok("添加成功") : Result.fail("添加失败");
    }

    // 3. 修改学生
    @PutMapping("/update")
    public Result updateStudent(@RequestBody Student student) {
        boolean updated = studentService.updateById(student);
        return updated ? Result.ok("修改成功") : Result.fail("修改失败");
    }

    // 4. 删除学生
    @DeleteMapping("/delete/{id}")
    public Result deleteStudent(@PathVariable Long id) {
        boolean removed = studentService.removeById(id);
        return removed ? Result.ok("删除成功") : Result.fail("删除失败");
    }
}