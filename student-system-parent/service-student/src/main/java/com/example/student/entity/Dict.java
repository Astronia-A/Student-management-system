package com.example.student.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sys_dict")
public class Dict {
    @TableId
    private Long id;
    private String type;
    private String k;
    private String v;
    private Integer sort;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}