package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface CommunityUserService {

    //查询管理长辈信息列表
    RestObject GetOldsterList(String CommunityUserId);

    //自己所在社区信息
    RestObject GetCommunity(String CommunityUserId);

    //查询管理社区某个长辈身体状况
    RestObject GetOldsterHealth(String CommunityUserId,String OldsterId);

    //查询管理社区某个长辈身体基础疾病列表
    RestObject GetOldsterBasicDiseases(String CommunityUserId,String OldsterId);

    //查询长辈当天健康数据
    RestObject GetHealthToday(String CommunityUserId, String OldsterId);




}
