package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.WordsWritten;
import com.ling.ap.Pojo.RestObject;

public interface WordsWrittenService {

    //长辈写入留言
    RestObject OldsterInsertWordsWritten(WordsWritten wordsWritten);

    //晚辈写入留言
    RestObject YoungerInsertWordsWritten(WordsWritten wordsWritten);

    //根据用户Id读取留言
    RestObject SelectWordsWritten(String recipient);

    //用户确认已读
    RestObject ReadWordsWritten(String wordsId,String recipient);

    //长辈写入社区留言
    RestObject OldsterInsertWordsWrittenCommunity(WordsWritten wordsWritten);

    //社区人员写入社区留言
    RestObject CommunityInsertWordsWrittenCommunity(WordsWritten wordsWritten);

    //系统写入留言
    RestObject SystemInsertWordsWritten(WordsWritten wordsWritten);

    //定期清除消息
    void DeleteWordsWrittenByTime();

    //根据Id获取对方信息（获取晚辈）
    public RestObject GetYounger(String senderId);

    //根据Id获取对方信息（获取长辈）
    public RestObject GetOldster(String senderId);

    //根据Id获取对方信息（获取社区人员）
    public RestObject GetCommunityUser(String senderId);

    //根据发送者来删除信息
    int DeleteWordsWrittenByDataSenderId(String SenderId);

    //社区管理人员发送信息
    RestObject AdminInsertWordsWritten(WordsWritten wordsWritten);

    //长辈向社区的管理人员发送消息
    RestObject OldsterInsertWordsWrittenAdmin (WordsWritten wordsWritten);

}
