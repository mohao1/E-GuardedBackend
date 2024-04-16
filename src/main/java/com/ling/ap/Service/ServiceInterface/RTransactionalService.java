package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.UserLogin;
import com.ling.ap.Pojo.Entity.Younger;

//注册事务封装
public interface RTransactionalService {
    //长辈注册事务
    int ORTransactional(Oldster oldster, UserLogin userLogin);
    //晚辈注册事务
    int YRTransactional(Younger younger, UserLogin userLogin);

    //社区人员注册事务
    int CRTransactional(CommunityUser communityUser, UserLogin userLogin);

}
