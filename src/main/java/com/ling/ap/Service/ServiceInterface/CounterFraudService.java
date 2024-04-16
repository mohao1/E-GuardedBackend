package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface CounterFraudService {
    //晚辈添加反诈消息订阅
    RestObject SubscriptionAntiFraud(String oldsterId, String youngerId);

    //晚辈取消反诈消息订阅
    RestObject UnSubscriptionAntiFraud(String oldsterId, String youngerId);

    //进行消息反诈添加
    void PushCounterFraud();

    //查询某个长辈是否绑定反诈
    RestObject GetSubscriptionAntiFraudById(String oldsterId, String youngerId);
}
