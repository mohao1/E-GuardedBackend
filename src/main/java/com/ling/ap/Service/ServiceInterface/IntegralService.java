package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.Integral;
import com.ling.ap.Pojo.RestObject;

public interface IntegralService {

    //添加积分列表
    boolean InsertIntegral(Integral integer);
    //根据Id修改积分列表积分
    boolean UpdateIntegralById(Integral integer);
    //查询家庭积分列表积分
    RestObject SelectIntegralById(String id);
    //签到添加积分
    public boolean SignIntegral(String homeId);
}
