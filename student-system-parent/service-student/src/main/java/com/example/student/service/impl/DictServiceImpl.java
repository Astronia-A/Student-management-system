package com.example.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.entity.Dict;
import com.example.student.mapper.DictMapper;
import com.example.student.service.DictService;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    // 这里暂时不需要写额外代码，ServiceImpl 已经帮我们做好了
}