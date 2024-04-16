package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserLoginMapper {
    //使用手机号查询对象
    UserLogin PhoneGetUserLogin(@Param("Phone") String Phone);

    //写入一个用户
    int SetUserLogin(@Param("UserLogin") UserLogin userLogin);

    //根据手机查询ID,Password
    Map<String,String> PhoneGetInformation(@Param("phone") String phone);
    //根据邮箱查询ID,Password
    Map<String,String> EmailGetInformation(@Param("email") String email);

    //根据id获取用户手机
    Map<String ,Object> GetPhoneById(@Param("id") String id);

}
