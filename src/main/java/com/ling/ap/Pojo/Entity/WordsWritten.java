package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordsWritten {
    String wordsId;
    String sender;
    String recipient;
    int type;//0=系统通知,1=家庭通知,2=社区通知
    int read;//0=未读,1=已读
    Date sendTime;
    String sendDate;
    String content;
}
