package com.example.student.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.student.entity.Org;
import java.util.List;

public interface OrgService extends IService<Org> {
    // 根据父级ID查询子节点
    List<Org> getByParentId(Long parentId);
}