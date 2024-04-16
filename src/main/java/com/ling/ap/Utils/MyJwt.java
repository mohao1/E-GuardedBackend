package com.ling.ap.Utils;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class MyJwt {


    //定义一个加密方式
    private static JWTSigner signer = JWTSignerUtil.hs512(Const.JWT_BYTE.getBytes(StandardCharsets.UTF_8));

    /**
     * 定义jwt基本方法
     * 生成没有超时时间的jwt
     *
     * @return String jwt(token,令牌)
     * @requires Map (string,string)
     * */
    public String GetJwt(Map<String,Object> map) {

        //创建Token
        //传入map对象，传入加密密钥(Bytes)
        String token = JWTUtil.createToken(map,signer);

        //返回生成的Token
        return token;
    }


    /**
     * 判断jwt是否正确
     * @return bool
     * */
    public boolean VerifyJwt(String Token){
        //jwt的数据判断是否正确
        try {
            return JWTUtil.verify(Token,signer);
        }
        catch (Exception err){
            return false;
        }
    }

    /**
     * 在jwt之中取值
     *
     * @return JSONObject（数据对象）
     * @requires String Token（jwt，令牌）
     */
    public JSONObject GetJwtValue(String Token){
        if (this.VerifyJwt(Token)){
            //解析Token
            JWT jwt = JWTUtil.parseToken(Token);
            //生成JSONObject对象
            JSONObject payloads = jwt.getPayloads();
            //返回JSONObject对象
            return payloads;
        }else {
            //返回空的JSONObject对象
            return new JSONObject();
        }
    }


}
