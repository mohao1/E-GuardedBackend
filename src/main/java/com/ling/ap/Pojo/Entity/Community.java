package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//社区信息
public class Community {
    String communityId;
    String name;
    String address;
    int oldsterNum;
    int userNum;
    String admin;
}
