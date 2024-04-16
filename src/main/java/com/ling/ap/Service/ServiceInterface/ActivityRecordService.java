package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface ActivityRecordService {

    //设置活动记录
    void SetActivityRecord(String oldsterId,String activityName,String activityContent);

    //查询活动记录
    RestObject GetActivityRecordList(String oldsterId, String youngerId);

    //每天重置长辈活动记录
    void TakeActivityRecord();
}
