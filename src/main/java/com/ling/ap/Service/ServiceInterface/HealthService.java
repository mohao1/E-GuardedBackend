package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.Health;
import com.ling.ap.Pojo.RestObject;

public interface HealthService {

    //长辈当天健康数据写入缓存
    RestObject SetHealthCache(Health health);

    //根据时间删除长辈数据（每日清除）
    int DeleteHealth(String Time);

    //根据长辈Id加上任务Id删除数据
    RestObject DeleteHealthById(String UserId,String TakeId);

    //查询长辈当天健康数据
    RestObject GetHealthToday(String UserId);

    //查询长辈10天内健康数据
    RestObject GetHealthTenDays(String UserId);

    //插入健康数据（数据库中数据）
    int SetHealthData(Health health);

    //数据录入删除
    void DataEntryDeletion();

    //晚辈查询长辈10天内健康数据
    RestObject YoungerGetHealthTenDays(String userId,String Oldster);

    //晚辈查询长辈当天健康数据
    RestObject YoungerGetHealthToday(String UserId,String Oldster);

    //长辈签到
    RestObject OldsterSign(String UserId);


}
