package com.ling.ap.Utils;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendBatchSmsResponse;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class AlibabaSendSms {

    //短信单条发送（验证码的发送）
    public String SendSms(Map<String,String> map) throws Exception {
        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(Const.ACCESS_KEY_ID)//设置ACCESS_KEY_ID
                .accessKeySecret(Const.ACCESS_KEY_SECRE)//设置ACCESS_KEY_ID
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region("cn-guangdong") // 设置地区ID
                //.httpClient(httpClient) //设置某个httpClient的设置否则使用默认设置
                .credentialsProvider(provider)//设置凭证的提供者provider
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")//设置发送请求服务器的地址
                )
                .build();

        //获取参数
        String phone=map.get("phone");//手机
        String signName = map.get("signName");//签名
        String templateCode = map.get("templateCode");//模板
        String templateParam = map.get("templateParam");//手机的验证码

        // 设置请求参数(设置手机等等信息)
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phone)
                .signName(signName)
                .templateCode(templateCode)
                .templateParam(templateParam)
                .build();

        // 获取异步数据
        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        // 等待获取返回数据
        SendSmsResponse resp = response.get();
        String json = new Gson().toJson(resp);
        // 返回值的异步处理
//        response.thenAccept(resp -> {
//            System.out.println(new Gson().toJson(resp));
//        }).exceptionally(throwable -> { // 处理返回异常
//            System.out.println(throwable.getMessage());
//            return null;
//        });

        // 关闭客户端
        client.close();

        return json;
    }


    //群体发送数据(用于发送求救信息)
    public String BatchSms(Map<String,String> map) throws Exception {
        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(Const.ACCESS_KEY_ID)
                .accessKeySecret(Const.ACCESS_KEY_SECRE)
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region("cn-guangdong") // 设置地区ID
                //.httpClient(httpClient) //设置某个httpClient的设置否则使用默认设置
                .credentialsProvider(provider)//设置凭证的提供者provider
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")//设置发送请求服务器的地址
                )
                .build();

        //获取参数
        String phone=map.get("phone");//手机("[\"18575842930\",\"15012916690\"]")
        String signName = map.get("signName");//签名("[\"lingmohao\",\"lingmohao\"]")
        String templateCode = map.get("templateCode");//模板("SMS_273735375")
        String templateParam = map.get("templateParam");//手机的验证码("[{\"code\":\"123456\"},{\"code\":\"123456\"}]")

        // 设置请求参数(设置手机等等信息)
        SendBatchSmsRequest sendBatchSmsRequest = SendBatchSmsRequest.builder()
                .signNameJson(signName)//签名数组
                .templateParamJson(templateParam)//信息数据
                .phoneNumberJson(phone)//手机号码数组
                .templateCode(templateCode)//模板
                .build();

        // 获取异步数据
        CompletableFuture<SendBatchSmsResponse> response = client.sendBatchSms(sendBatchSmsRequest);
        // 等待获取返回数据
        SendBatchSmsResponse resp = response.get();
//        System.out.println(new Gson().toJson(resp));
        String json = new Gson().toJson(resp);
        // 关闭客户端
        client.close();

        return json;
    }
}


