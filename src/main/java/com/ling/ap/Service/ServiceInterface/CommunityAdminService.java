package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface CommunityAdminService {

    //查询管理社区中的老人信息列表
    RestObject SelectedOldsterList(String AdminId);
    //查询管理社区中的社区人员信息列表
    RestObject SelectedCommunityUserList(String AdminId);

    //社区管理人员查询社区信息
    RestObject SelectedCommunity(String AdminId);

    //社区管理人员查询社区某个长辈健康信息
    RestObject SelectedOldsterHealth(String AdminId,String OldsterId);


}
