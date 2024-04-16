package com.ling.ap.Service.ServiceInterface;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Pojo.Entity.Home;
import com.ling.ap.Pojo.RestObject;

public interface HomeService {
    //长辈绑定家庭
    RestObject OldsterBindHome(JSONObject jsonObject,String UserId);
    //晚辈绑定家庭
    RestObject YoungerBindHome(JSONObject jsonObject,String UserId);
    //创建家庭(管理)
    RestObject CreateHome(Home home);
    //删除家庭(管理)
    RestObject DeleteHome(JSONObject jsonObject,String UserId);
    //长辈退出家庭
    RestObject OldsterUnBindHome(String UserId);
    //晚辈退出家庭
    RestObject YoungerUnBindHome(String UserId);


}
