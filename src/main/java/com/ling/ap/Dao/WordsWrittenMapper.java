package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.WordsWritten;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WordsWrittenMapper {
    //写入消息
    int InsertWordsWritten(@Param("wordsWritten") WordsWritten wordsWritten);

    //根据读取者Id查询消息
    List<WordsWritten> SelectWordsWrittenById(@Param("recipientId") String recipientId);

    //根据时间删除消息
    int DeleteWordsWrittenByDataTime(@Param("dateTime") String dateTime);

    //设置消息已读
    int UpdateWordsWrittenReadById(@Param("wordsId") String wordsId ,@Param("recipientId") String recipientId);

    //根据发送者来删除信息
    int DeleteWordsWrittenByDataSenderId(@Param("SenderId") String SenderId);

}
