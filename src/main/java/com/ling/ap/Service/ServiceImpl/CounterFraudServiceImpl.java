package com.ling.ap.Service.ServiceImpl;

import cn.hutool.core.util.IdUtil;
import com.ling.ap.Dao.CounterFraudMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.CounterFraud;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.WordsWritten;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CounterFraudService;
import com.ling.ap.Service.ServiceInterface.WordsWrittenService;
import com.ling.ap.Utils.Const;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CounterFraudServiceImpl implements CounterFraudService {
    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    YoungerMapper youngerMapper;
    @Resource
    CounterFraudMapper counterFraudMapper;

    @Resource
    WordsWrittenService wordsWrittenService;


    //晚辈添加反诈消息订阅
    @Override
    public RestObject SubscriptionAntiFraud(String oldsterId, String youngerId) {
        Oldster oldster = oldsterMapper.GetOldster(oldsterId);
        Younger younger = youngerMapper.GetYounger(youngerId);
        if (younger==null||oldster==null){
            return new RestObject("401","没有权限设置",youngerId);
        }
        String OldsterHomeId = oldster.getHomeId();
        String YoungerHomeId = younger.getHomeId();
        if (!YoungerHomeId.equals(OldsterHomeId)){
            return new RestObject("401","没有权限设置",youngerId);
        }
        CounterFraud counterFraud = counterFraudMapper.SelectCounterFraudById(oldsterId);
        if (counterFraud!=null){
            return new RestObject("200","设置成功",null);
        }
        int i = counterFraudMapper.InsertCounterFraud(new CounterFraud(oldster.getUserId(), oldster.getName()));
        if (i==0){
            return new RestObject("200","设置失败",i);
        }
        return new RestObject("200","设置成功",i);
    }

    //晚辈取消反诈消息订阅
    @Override
    public RestObject UnSubscriptionAntiFraud(String oldsterId, String youngerId) {
        Oldster oldster = oldsterMapper.GetOldster(oldsterId);
        Younger younger = youngerMapper.GetYounger(youngerId);
        CounterFraud counterFraud = counterFraudMapper.SelectCounterFraudById(oldsterId);
        if (younger==null||oldster==null||counterFraud==null){
            return new RestObject("401","没有权限设置",youngerId);
        }
        String OldsterHomeId = oldster.getHomeId();
        String YoungerHomeId = younger.getHomeId();
        if (!YoungerHomeId.equals(OldsterHomeId)){
            return new RestObject("401","没有权限设置",youngerId);
        }
        int i = counterFraudMapper.DeleteCounterFraud(oldsterId);
        if (i==0){
            return new RestObject("200","取消失败",i);
        }
        return new RestObject("200","取消成功",i);
    }

    //进行消息反诈添加
    @Override
    public void PushCounterFraud() {

        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        wordsWrittenService.DeleteWordsWrittenByDataSenderId(Const.COUNTER_FRAUD);

        //查询订阅反诈长辈列表
        List<CounterFraud> list = counterFraudMapper.SelectCounterFraudList();
        for (CounterFraud counterFraud : list) {
            String uuid = IdUtil.simpleUUID();
            String content="温馨提醒：守住养老钱，幸福过晚年，诈骗手段花样多 不听不信不转账。";
            WordsWritten wordsWritten = new WordsWritten(uuid, Const.COUNTER_FRAUD,counterFraud.getUserId(),0,0,new Date(),strDate,content);
            wordsWrittenService.SystemInsertWordsWritten(wordsWritten);
        }
    }

    //查询某个长辈是否绑定反诈
    @Override
    public RestObject GetSubscriptionAntiFraudById(String oldsterId, String youngerId){
        Oldster oldster = oldsterMapper.GetOldster(oldsterId);
        Younger younger = youngerMapper.GetYounger(youngerId);
        if (younger==null||oldster==null){
            return new RestObject("401","没有权限设置",youngerId);
        }
        String OldsterHomeId = oldster.getHomeId();
        String YoungerHomeId = younger.getHomeId();
        if (!YoungerHomeId.equals(OldsterHomeId)){
            return new RestObject("401","没有权限设置",youngerId);
        }
        CounterFraud counterFraud = counterFraudMapper.SelectCounterFraudById(oldsterId);
        if (counterFraud==null){
            return new RestObject("200","查询成功",null);
        }
        return new RestObject("200","查询成功",counterFraud);
    }
}
