package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//长辈健康状态记录
public class Health {
    String healthId;
    String userId;
    String time;
    int step;
    String bloodLow;
    String bloodPressure;
    String heartRate;
    String bloodSugar;
    int sign;
}
