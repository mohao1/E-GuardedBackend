package com.ling.ap.Utils;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

//生成手机的验证码
@Component
public class CodeUtil {

    //生成验证码并且返回出去
    public String GetCode(){

        StringBuilder CodeBuilder=new StringBuilder();

        for (int i=0;i<5;i++){
            int anInt = RandomUtil.randomInt(0, 10);
            CodeBuilder.append(anInt);
        }
        String Code = CodeBuilder.toString();

        return Code;
    }
}
