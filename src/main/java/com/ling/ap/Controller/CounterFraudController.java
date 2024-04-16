package com.ling.ap.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CounterFraudService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/CounterFraud")
public class CounterFraudController {

    @Resource
    CounterFraudService counterFraudService;


    //晚辈添加反诈消息订阅
    //String oldsterId, String youngerId
    @PostMapping("/Subscription")
    public RestObject SubscriptionAntiFraud(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        if (jsonObject==null){
            return new RestObject("403","信息错误",null);
        }
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return counterFraudService.SubscriptionAntiFraud(oldsterId, (String) JWT);
    }

    //晚辈取消反诈消息订阅
    //String oldsterId, String youngerId
    @PostMapping("/UnSubscription")
    public RestObject UnSubscriptionAntiFraud(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        if (jsonObject==null){
            return new RestObject("403","信息错误",null);
        }
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return counterFraudService.UnSubscriptionAntiFraud(oldsterId, (String) JWT);
    }

    //查询某个长辈是否绑定反诈
    @PostMapping("/GetSubscription")
    public RestObject GetSubscriptionAntiFraudById(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT){
        if (jsonObject==null){
            return new RestObject("403","信息错误",null);
        }
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return counterFraudService.GetSubscriptionAntiFraudById(oldsterId, (String) JWT);
    }
}
