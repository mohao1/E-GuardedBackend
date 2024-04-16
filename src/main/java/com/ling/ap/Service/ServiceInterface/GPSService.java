package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

import java.util.List;

public interface GPSService {
    //长辈获取晚辈列表
    List<String> OldsterGetYoungerIdList(String oldsterId);
    //晚辈获取长辈列表
    List<String> YoungerGetOldsterIdList(String youngerId);
    //晚辈获取长辈位置信息
    RestObject YoungerGetOldsterGPS(List<String> list,String UserId);
    //长辈写入自己的位置信息
    boolean OldsterSetGPS(String GPS,String oldsterId);
    //长辈获取自身位置信息
    RestObject OldsterGetMyGPS(String oldsterId);
}
