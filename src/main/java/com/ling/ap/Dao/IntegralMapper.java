package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Integral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IntegralMapper {
    //添加积分列表
    int InsertIntegral(@Param("Integral") Integral integer);
    //根据Id修改积分列表积分
    int UpdateIntegralById(@Param("Integral") Integral integer);
    //查询家庭积分列表积分
    Integral SelectIntegralById(@Param("Id") String id);
}
