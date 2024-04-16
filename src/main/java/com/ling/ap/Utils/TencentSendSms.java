package com.ling.ap.Utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.stereotype.Component;

@Component
public class TencentSendSms {


    //发送一条信息
    public String SendSms(String [] phoneList ,String[] templateParamList,String userId,String SignName,String TemplateId,String AppId ) throws Exception{
            //设置"SecretId""SecretId"
            Credential cred = new Credential(Const.SecretId, Const.SecretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            //设置请求网址
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            //设置地区
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            //电话设置发送号码
            req.setPhoneNumberSet(phoneList);

            req.setSmsSdkAppId(AppId);//设置应用"1400805077"
            req.setSignName(SignName);//设置签名"屹悟软件"
            req.setTemplateId(TemplateId);//设置模板ID"1799938"

            //设置对应数值
            req.setTemplateParamSet(templateParamList);

            //设置用户唯一标识
            req.setSessionContext(userId);
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            return SendSmsResponse.toJsonString(resp);
    }
}
