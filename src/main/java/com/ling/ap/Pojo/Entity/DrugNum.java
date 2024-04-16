package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//长辈药品信息
public class DrugNum {
    String userId;
    String drugId;
    String name;
    int num;
    String method;
}
