package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//用户账号登录信息
public class UserLogin {
    String userId;
    String password;
    String phone;
    String email;
    String wechat;
    String address;
}
