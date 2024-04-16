package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//晚辈信息
public class Younger {
    String userId;
    String homeId;
    String name;
    int age;
    int sex;
    int priority;

}
