package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Management;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface ManagementMapper {

    //查询用户信息根据登录名称
    Management GetManagement(@Param("name") String name);

    //根据id获取全部信息
    Map<String,Object> GetManagementById(@Param("id") String id);
}
