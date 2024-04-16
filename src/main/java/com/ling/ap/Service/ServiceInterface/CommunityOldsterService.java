package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface CommunityOldsterService {

    //长辈查询社区人员列表
    RestObject OldsterGetCommunityUserList(String OldsterId);
    //长辈查询某个社区人员信息
    RestObject OldsterGetCommunityUser(String OldsterId,String CommunityUserId);
    //长辈查询社区信息
    RestObject OldsterGetCommunity(String OldsterId);


}
