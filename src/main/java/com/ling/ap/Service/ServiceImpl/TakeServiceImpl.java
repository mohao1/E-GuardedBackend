package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Service.ServiceInterface.*;
import com.ling.ap.Stoker.WebStoker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TakeServiceImpl implements TakeService {
    @Resource
    HealthService healthService;
    @Resource
    WordsWrittenService wordsWrittenService;
    @Resource
    CounterFraudService counterFraudService;
    @Resource
    HealthScoreService healthScoreService;
    @Resource
    ActivityRecordServiceImpl activityRecordService;
    @Resource
    WebStoker webStoker;


    @Override
    @Scheduled(cron = "0 50 23 * * ?")//设置每天23:50分执行数据存储语句
    public void DataEntryDeletion() {
        healthService.DataEntryDeletion();//健康数据管理
        wordsWrittenService.DeleteWordsWrittenByTime();//留言消息管理
    }

    @Override
    @Scheduled(cron = "0 50 22 * * ?")//设置每天22:50分执行数据存储语句
    public void DataEntryDeletionL() {
        healthScoreService.UpdateHealthScore();
    }

    //反诈消息定时发送
    @Override
    @Scheduled(cron = "0 0 06 * * ?")
    public void CounterFraudDataDelete() {
        counterFraudService.PushCounterFraud();
    }


    @Scheduled(cron = "0 0 04 * * ?")
    public void Data() {
        activityRecordService.TakeActivityRecord();
    }


    //Ping
    @Override
    @Scheduled(fixedDelay = 30000)
    public void Ping(){
        webStoker.SendPing();
    }

}
