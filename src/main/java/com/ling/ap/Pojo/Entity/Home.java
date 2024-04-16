package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//家庭信息
public class Home {
    String homeId;
    String homeName;
    int userNum;
    String admin;
}
