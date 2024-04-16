package com.ling.ap.Utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class Character_recognition {
    private static  String PlayID="grant_type=client_credentials&client_id="
            + Const.API_KEY //API_KEY百度获取
            + "&client_secret="
            + Const.SECRET_KEY;//SECRET_KEY百度获取

    private static  String SpeechID="grant_type=client_credentials&client_id="
            + Const.SPEECH_API_KEY//API_KEY百度获取
            + "&client_secret="
            + Const.SPEECH_SECRET_KEY;//SECRET_KEY百度获取

    //转码的工具类
    @Resource
    Encoded encoded;


    /**
     * URL的结构
     * https://tsn.baidu.com/text2audio ?
     * tex=%e7%99%be%e5%ba%a6%e4%bd%a0%e5%a5%bd &
     * lan=zh &
     * cuid= %E4%BD%A0%E5%A5%BD &
     * ctp=1 &
     * tok= + getAccessToken()
     * */
    //进行文字转为语音请求播放路径（返回处理的URL链接）
    public String GetPlayUrl(String Text,String id) {
        //转换文本（UrlEncoded转换）
        String tex= encoded.UrlEncode(Text);
        //获取对应用户ID（UrlEncoded转换）
        String cuid = id==null || id.equals("") ? encoded.UrlEncode("12345Java") : encoded.UrlEncode(id);
        //拼接数据
        String Url="https://tsn.baidu.com/text2audio?tex=" + tex + "&lan=zh&cuid=" + cuid + "&ctp=1&tok=" + getAccessToken(PlayID);
        return Url;
    }

    /**
     * 语音转换文字
     *
     *
     *     "format":"pcm",
     *     "rate":16000,
     *     "dev_pid":1537,
     *     "channel":1,
     *     "token":xxx,
     *     "cuid":"baidu_workshop",
     *     "len":4096,
     *     "speech":"xxx", // xxx为 base64（FILE_CONTENT）
     *
     *
     * */
    public Object SpeechToText(MultipartFile file, String id) throws IOException {
        String token = getAccessToken(SpeechID);
        //File数据类型转换成为Base64
        String base64 = getFileContentAsBase64(file);
        //编写请求头的数据
        Map<String, String> Header = new HashMap<>();
        Header.put("Content-Type","application/json");
        Header.put("Accept", "application/json");
        //编写请求数据
        JSONObject jsonObject = new JSONObject();
        jsonObject
                .set("format","pcm")
                .set("rate",16000)
                .set("channel",1)
                .set("token",token)
                .set("cuid",id)
                .set("len",file.getBytes().length)
                .set("speech",base64);

        String body = HttpRequest.post("https://vop.baidu.com/server_api")
                .addHeaders(Header)
                .contentType("application/json")
                .body(jsonObject.toString()).execute().body();
        JSONObject entries = JSONUtil.parseObj(body);
        String err_msg = (String) entries.get("err_msg");
        if (err_msg !=null && err_msg.equals("success.")){
            return entries.get("result");
        }
        return "";
    }


    /***
     *
     * 获取数据格式转换Base64
     *
     *
     */
    static String getFileContentAsBase64(MultipartFile file) throws IOException {
//        byte[] b = Files.readAllBytes(Paths.get(path));
        byte[] b = file.getBytes();
        return Base64.getEncoder().encodeToString(b);

    }




    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     */
    static String getAccessToken( String ID)  {
        //发送请求
        String atoken=HttpRequest.post("https://aip.baidubce.com/oauth/2.0/token")
                .contentType("application/x-www-form-urlencoded")
                .body(ID)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .execute().body();
        return (String) JSONUtil.parseObj(atoken).get("access_token");
    }
}