package com.example.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.entity.Org;
import com.example.student.mapper.OrgMapper;
import com.example.student.service.OrgService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {

    @Override
    public List<Org> getByParentId(Long parentId) {
        LambdaQueryWrapper<Org> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Org::getParentId, parentId);
        return this.list(wrapper);
    }
}