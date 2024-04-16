package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//社区工作人员
public class CommunityUser {
    String userId;
    String communityId;
    String name;
    int age;
    int sex;
}
