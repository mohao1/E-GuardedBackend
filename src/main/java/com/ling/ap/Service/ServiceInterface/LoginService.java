package com.ling.ap.Service.ServiceInterface;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Pojo.RestObject;

public interface LoginService {
    //手机登录
    RestObject Phone(JSONObject jsonObject);

    //邮箱登录
    RestObject Email(JSONObject jsonObject);

    //微信登录
    RestObject Wechat(JSONObject jsonObject);

    //手机的验证码登录
    RestObject Code(JSONObject jsonObject);

    //登录验证码的发送
    public RestObject AcquisitionVerificationCode(String phone);

    //社区管理人员登录
    RestObject AdminLogin(JSONObject jsonObject);

    //管理平台登录
    RestObject ManagementLogin(JSONObject jsonObject);



}
