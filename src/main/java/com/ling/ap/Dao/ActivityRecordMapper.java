package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.ActivityRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityRecordMapper {
    //设置活动记录
    int InsertActivityRecord(@Param("activityRecord") ActivityRecord activityRecord);

    //删除活动记录（根据设置时间）
    int DeleteActivityRecord(@Param("Time") String Time);

    //查询活动记录（根据长辈id）
    List<ActivityRecord> SelectActivityRecordList(@Param("OldsterId")String OldsterId,@Param("YoungerId")String YoungerId);
}
