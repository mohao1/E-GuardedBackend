package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.HealthScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HealthScoreMapper {

    //根据长辈获取评分信息
    HealthScore SelectHealthScoreById(@Param("OldsterId") String OldsterId);

    //根据晚辈获取评分信息
    HealthScore SelectHealthScoreByYounger(@Param("OldsterId")String OldsterId,@Param("YoungerId")String YoungerId);

    //设置评分信息
    int InsertHealthScore(@Param("healthScore")HealthScore healthScore);

    //修改评分信息
    int UpdateHealthScore(@Param("healthScore")HealthScore healthScore);

}
