package com.ling.ap.Service.ServiceImpl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.GreetingsService;
import com.ling.ap.Utils.CalendarUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class GreetingsServiceImpl implements GreetingsService {

    @Resource
    CalendarUtil calendarUtil;

    private TimedCache<String, String> RegistrationCodeCache = CacheUtil.newTimedCache(43200000);


    //获取每日问候
    @Override
    public synchronized RestObject GetGreetings() {
        //获取当前时间日期
        DateTime date = DateUtil.date();
        int year = date.year();
        int month = date.monthBaseOne();
        int i = date.dayOfMonth();
        String StrDate=year+":"+month+":"+i;

        Map<String, String> map = new HashMap<>();
        map.put("date",StrDate);

        String Msg = RegistrationCodeCache.get(StrDate);
        if (Msg==null){
            String calendar = calendarUtil.calendar();
            if (calendar.equals("0")||calendar.equals("工作日")||calendar.equals("休息日")){
                map.put("msg","开启新的一天生活，祝您生活愉快!");
                RegistrationCodeCache.put(StrDate,"开启新的一天生活，祝您生活愉快!");
            }else {
                map.put("msg","今天是"+calendar+"节,祝您"+calendar+"节日快乐！");
                RegistrationCodeCache.put(StrDate,"今天是"+calendar+"节,祝您"+calendar+"节日快乐！");
            }
            return new RestObject<>("200","获取成功",map);
        }
        map.put("msg",Msg);

        return new RestObject<>("200","获取成功",map);
    }
}
