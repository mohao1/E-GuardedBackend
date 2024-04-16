package com.ling.ap.Utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.PutObjectRequest;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TencentCOSUtil {
    //上传数据函数(COS桶)
    public boolean UpLoadFile(File file, String fileName){
        boolean flag = true;
        //地域
        ClientConfig clientConfig = new ClientConfig(Const.COS_REGION);

        COSCredentials cred = new BasicCOSCredentials(Const.COS_SECRET_ID, Const.COS_SECRET_KEY);
        // 生成 COS 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            //发送数据(COS桶)
            PutObjectRequest putObjectRequest = new PutObjectRequest(Const.COS_BUCKET_NAME, fileName, file);
            cosClient.putObject(putObjectRequest);
        }catch (Exception e){
            flag=false;
            e.printStackTrace();
        }finally {
            //关闭数据链接
            cosClient.shutdown();
        }
        return flag;
    }

    //获取数据原的地址(限时获取)
    public String GetFile(String fileName){
        //地域
        ClientConfig clientConfig = new ClientConfig(Const.COS_REGION);

        COSCredentials cred = new BasicCOSCredentials(Const.COS_SECRET_ID, Const.COS_SECRET_KEY);
        // 生成 COS 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
        Date expirationDate = new Date(System.currentTimeMillis() + 20 * 60 * 1000);

        // 填写本次请求的参数，能够防止用户篡改此签名的 HTTP 请求的参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("content-type", "image/png");

        // 填写本次请求的头部，能够防止用户篡改此签名的 HTTP 请求的头部
        Map<String, String> headers = new HashMap<String, String>();
        params.put("key", "e-守护");

        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = HttpMethodName.GET;

        URL url = cosClient.generatePresignedUrl(Const.COS_BUCKET_NAME, fileName, expirationDate, method, headers, params);
        //关闭数据链接
        cosClient.shutdown();

        return url.toString();
    }


}
