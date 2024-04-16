package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//食品记录
public class Foodstuff {
    String takeId;
    String userId;
    String title;
    String content;
    String name;
    Date start;
    Date end;
}
