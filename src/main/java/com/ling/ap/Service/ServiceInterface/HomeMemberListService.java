package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface HomeMemberListService {

    //长辈查询晚辈列表
    RestObject  QueryYounger(String UserId);
    //晚辈查询长辈列表
    RestObject  QueryOldster(String UserId);
    //查询家庭全部人员
    RestObject  QueryAll(String UserId);

    //查询年轻人是否绑定了老人
    boolean QueryYoungerBindOldster(String youngerId,String OldsterId);

    //长辈查询自己的家庭信息
    RestObject OldsterGetHome(String OldsterId);
    //晚辈查询自己的家庭信息
    RestObject YoungerGetHome(String OldsterId);

}
