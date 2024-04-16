package com.ling.ap.Utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

@Component
public class UIDS {

    public synchronized String GetUIDS(String Type){
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

        String dateStr= Type+year+monthStr+dayStr;
        return dateStr+String.format("%05d", RandomUtil.randomInt(1,100000));
    }
}
