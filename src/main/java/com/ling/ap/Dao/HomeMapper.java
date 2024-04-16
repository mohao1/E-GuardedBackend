package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Home;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HomeMapper {

    //创建家庭
    int CreateHome(@Param("Home") Home home);
    //删除家庭
    int DeleteHome(@Param("HomeId") String HomeId,@Param("adminId") String adminId);

    //成员数量更新
    int UpdateNum(@Param("Num") int Num,@Param("HomeId") String HomeId);

    //查询成员数量
    int SelectNum(@Param("HomeId") String HomeId);

    //查询家庭数据
    Home SelectHome(@Param("HomeId") String HomeId);

    //清除所有长辈绑定某个家庭的人员的绑定
    void DeleteOldsterBindHome(@Param("HomeId") String HomeId,@Param("D") String d);

    //清除所有晚辈绑定某个家庭的人员的绑定
    void DeleteYoungerBindHome(@Param("HomeId") String HomeId,@Param("D") String d);

    //查询家庭列表
    List<Home> SelectHomeList();



}
