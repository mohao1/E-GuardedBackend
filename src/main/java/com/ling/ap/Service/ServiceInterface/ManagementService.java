package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface ManagementService {

    //获取自身信息
    RestObject GetManagementData(String Id);
    //查询长辈人员列表
    RestObject GetOldsterDataList(String Id);
    //查询晚辈列表
    RestObject GetYoungerDataList(String Id);
    //查询社区人员列表
    RestObject GetCommunityUserDataList(String Id);
    //查询社区管理人员列表
    RestObject GetCommunityAdminDataList(String Id);
    //查询家庭列表
    RestObject GetHomeDataList(String Id);
    //查询社区列表
    RestObject GetCommunityDataList(String Id);
    //根据id查询长辈当天健康情况
    RestObject GetOldsterHealthData(String Id,String oldsterId);
    //根据id查询长辈10天内健康情况
    RestObject GetOldsterHealthDataDays(String Id,String oldsterId);

    //查询求救所有记录
    RestObject SelectRecordList(String Id);

    //根据时间信息查询信息
    RestObject SelectRecordListByTime(String Id,String Time);

    //根据长辈id查询信息
    RestObject SelectRecordListByOldsterId(String Id,String oldsterId);

    //根据信息id查询信息
    RestObject SelectRecordById(String Id,String RecordId);
}
