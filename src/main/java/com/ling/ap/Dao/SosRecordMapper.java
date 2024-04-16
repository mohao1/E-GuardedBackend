package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.SosRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SosRecordMapper {
    //添加一条求救信息
    int insertRecord(@Param("SosRecord")SosRecord sosRecord);

    //查询全部求救信息
    List<SosRecord> SelectRecordList();

    //根据信息id查询信息
    SosRecord SelectRecordById(@Param("Id")String id);

    //根据长辈id查询信息
    List<SosRecord> SelectRecordListByOldsterId(@Param("UserId")String UserId);

    //根据时间信息查询信息
    List<SosRecord> SelectRecordListByTime(@Param("Time")String Time);


}
