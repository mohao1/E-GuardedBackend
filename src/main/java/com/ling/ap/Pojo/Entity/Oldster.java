package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//长辈信息
public class Oldster {
    String userId;
    String homeId;
    String communityId;
    String name;
    int age;
    int sex;
}
