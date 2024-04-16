package com.ling.ap.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//返回数据格式规范
public class RestObject<T> {
    String Code;//状态码
    String Msg;//数据状态信息
    T Data;//数据信息
}