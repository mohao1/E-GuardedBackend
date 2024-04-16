package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//吃药列表设置
public class Drug {
    String takeId;
    String userId;
    String drugId;
    String title;
    String content;
    Date setTime;
    Time drugTime;
    int whether;
    String selectTime;
}
