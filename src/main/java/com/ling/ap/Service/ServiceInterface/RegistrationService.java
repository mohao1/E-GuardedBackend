package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.UserLogin;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;


public interface RegistrationService {

    //用户注册功能（长辈）
    RestObject OldsterRegistration(Oldster oldster, UserLogin userLogin);

    //用户注册功能（晚辈）
    RestObject YoungerRegistration(Younger younger, UserLogin userLogin);

    //用户注册功能（晚辈）
    RestObject CommunityUserRegistration(CommunityUser communityUser, UserLogin userLogin);

    //验证码的发送
    RestObject AcquisitionVerificationCode(String phone);

    //验证码的验证
    boolean VerificationCodeConfirmation( String phone,String code);
}
