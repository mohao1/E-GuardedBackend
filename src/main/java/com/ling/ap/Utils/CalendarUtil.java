package com.ling.ap.Utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

@Component
public class CalendarUtil {

    //判断是否是节假日
    public String calendar(){
        //获取当前时间日期
        DateTime date = DateUtil.date();
        int year = date.year();
        int month = date.monthBaseOne();
        int i = date.dayOfMonth();

        String monthStr;
        String dayStr;

        //处理月份
        if (month<10){
            monthStr="0"+month;
        }else{
            monthStr=""+month;
        }
        //处理日期
        if (i<10){
            dayStr="0"+i;
        }else{
            dayStr=""+i;
        }

        String dateStr= year+monthStr+dayStr;


        String url="https://www.mxnzp.com/api/holiday/single/"+"20230325"+"?ignoreHoliday=false&app_id="+Const.APP_ID+"&app_secret="+Const.APP_SECRET;

        String body = HttpRequest.get(url).execute().body();

        JSONObject jsonObject = JSONUtil.parseObj(body);

        String code = jsonObject.get("code").toString();

        if (!code.equals("1")){
            return "0";
        }

        String dataStr = jsonObject.get("data").toString();

        JSONObject data = JSONUtil.parseObj(dataStr);

        return data.get("typeDes").toString();
    }
}
