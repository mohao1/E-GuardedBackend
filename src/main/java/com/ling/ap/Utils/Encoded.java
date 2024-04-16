package com.ling.ap.Utils;

import cn.hutool.core.util.URLUtil;
import org.springframework.stereotype.Component;


//转码工具
@Component
public class Encoded {

    //进行两次UrlEncode(百度文字语音使用)
    //获取更高精度数据
    public String UrlEncode(String value){
        //第一次的处理
        String OneValue = URLUtil.encode(value);
        //第二次的处理
        return URLUtil.encode(OneValue);
    }
}
