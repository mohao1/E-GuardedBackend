package com.ling.ap.Pojo.ToDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GPSInformation {
    private String UserId;//用户id
    private String Longitude;//经度
    private String Latitude;//纬度
}
