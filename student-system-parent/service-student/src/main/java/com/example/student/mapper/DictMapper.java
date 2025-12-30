package com.example.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.student.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}
