package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface CommunityService {

    //老人绑定社区
    RestObject OldsterBindCommunity(String CommunityId, String OldsterId);

    //老人退出绑定社区
    RestObject OldsterUnBindCommunity(String CommunityId, String OldsterId);

    //社区人员申请绑定社区
    RestObject CommunityUserApplicationBindCommunity(String CommunityId, String CommunityUserId);

    //社区人员退出绑定社区
    RestObject CommunityUserUnBindCommunity(String CommunityUserId,String CommunityId);

    //社区管理人员查看申请人员列表
    RestObject CommunityAdminGetBindCommunity(String AdminId);

    //社区管理人员通过绑定社区
    RestObject CommunityAdminBindCommunity(String AdminId, String CommunityUserId,String CommunityId);

    //社区管理人员拒绝绑定社区
    RestObject CommunityAdminUnBindCommunity(String AdminId, String CommunityUserId,String CommunityId);

    //社区管理人员移除社区用户
    RestObject CommunityAdminDeleteBindCommunity(String AdminId, String CommunityUserId);

    //社区管理人员移除社区长辈账号
    RestObject CommunityAdminDeleteBindOldster(String AdminId, String OldsterId);


}
