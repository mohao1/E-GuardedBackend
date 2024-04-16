package com.ling.ap.Service.ServiceInterface;

public interface TakeService {
    //定时清除录入
    public void DataEntryDeletion();
    public void DataEntryDeletionL();
    //反诈消息定时发送
    public void CounterFraudDataDelete();

    //Ping
    void Ping();

}
