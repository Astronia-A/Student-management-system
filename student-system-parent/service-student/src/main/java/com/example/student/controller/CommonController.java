package com.example.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Result;
import com.example.student.entity.Dict;
import com.example.student.entity.Org;
import com.example.student.service.DictService;
import com.example.student.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private DictService dictService;

    // 1. 获取班级联动数据
    // 前端调用示例：/common/org/0 获取院系列表；/common/org/1 获取院系1下的专业列表
    @GetMapping("/org/{parentId}")
    public Result getOrgList(@PathVariable Long parentId) {
        List<Org> list = orgService.getByParentId(parentId);
        return Result.ok(list);
    }

    // 2. 获取数据字典数据
    // 前端调用示例：/common/dict/gender 获取性别选项
    @GetMapping("/dict/{type}")
    public Result getDictByType(@PathVariable String type) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getType, type).orderByAsc(Dict::getSort);
        List<Dict> list = dictService.list(wrapper);
        return Result.ok(list);
    }
}