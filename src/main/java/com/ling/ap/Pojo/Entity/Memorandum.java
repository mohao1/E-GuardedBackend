package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//备忘录的信息
public class Memorandum {
    String takeId;
    String userId;
    String title;
    String content;
    Date setTime;
    //DeLoge
    String start;
    String end;
    int whether;
}
