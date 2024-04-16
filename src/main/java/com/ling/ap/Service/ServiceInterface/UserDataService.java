package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;

public interface UserDataService {

    //长辈获取自身信息
    RestObject OldsterGetUserData(String oldsterId);

    //晚辈获取自身信息
    RestObject YoungerGetUserData(String YoungerId);

    //社区的管理员获取自身信息
     RestObject CommunityAdminGetUserData(String CommunityAdminId);

    //社区工作人员获取自身信息
    RestObject CommunityUserGetUserData(String CommunityUserId);

    //修改长辈个人信息
    RestObject OldsterUpDataUserData(Oldster oldster);

    //修改晚辈个人信息
    RestObject YoungerUpDataUserData(Younger younger);

    //修改社区人员个人信息
    RestObject CommunityUserUpDataUserData(CommunityUser communityUser);
}
