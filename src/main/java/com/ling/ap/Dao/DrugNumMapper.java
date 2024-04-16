package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.DrugNum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DrugNumMapper {

    //设置药品名称数量使用方法
    int InsertDrugNum(@Param("drugNum") DrugNum drugNum);

    //修改药品数量
    int UpdateDrugNum(@Param("drugNum")DrugNum drugNum);

    //删除药品数量名称
    int DeleteDrugNum(@Param("drugId")String drugId);

    //查询药品数量
    int SelectDrugNum(@Param("drugId") String drugId);

    //查询药品信息
    DrugNum SelectDrugNumById(@Param("drugId") String drugId);

    //根据用户Id和药品名称查询药品信息
    DrugNum SelectDrugNumByOldsterIdAndName(@Param("OldsterId") String OldsterId,@Param("Name") String Name);
}
