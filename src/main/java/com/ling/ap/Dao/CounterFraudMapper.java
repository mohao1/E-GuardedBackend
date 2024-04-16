package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.CounterFraud;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CounterFraudMapper {
    //添加反诈长辈
    int InsertCounterFraud(@Param("CounterFraud") CounterFraud counterFraud);
    //删除反诈长辈
    int DeleteCounterFraud(@Param("Id") String id);

    //查询全部长辈列表
    List<CounterFraud> SelectCounterFraudList();

    //查询某个长辈是否添加反诈
    CounterFraud SelectCounterFraudById(@Param("Id") String id);

    //根据长辈Id修改名称
    int UpdateCounterFraudNameById(@Param("CounterFraud") CounterFraud counterFraud);
}
