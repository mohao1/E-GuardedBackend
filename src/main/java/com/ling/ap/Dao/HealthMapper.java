package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Health;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HealthMapper {

    //根据长辈ID查询老人健康全部健康信息
    List<Health> SelectHealthById(@Param("userId") String userId);

    //添加长辈健康信息
    int InsertHealth(@Param("health") Health health);

    //根据时间删除长辈健康信息
    int DeleteHealthByTime(@Param("dateTime") String dateTime);

    //根据ID删除
    int DeleteHealthById(@Param("userId") String userId,@Param("TakeId") String TakeId);

    //晚辈查询老人健康信息
    List<Health> SelectYoungerHealthById(@Param("userId") String userId,@Param("Oldster") String Oldster);

}
