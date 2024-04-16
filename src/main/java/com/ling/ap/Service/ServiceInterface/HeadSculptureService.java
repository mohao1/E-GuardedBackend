package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;
import org.springframework.web.multipart.MultipartFile;

public interface HeadSculptureService {

    //初始化用户的头像
    boolean OldsterInitUserHeadSculpture(String userId,int sex);

    boolean YoungerInitUserHeadSculpture(String userId,int sex);

    //修改用户的头像
    RestObject UpDataUserHeadSculpture(String userId, MultipartFile file);

    //根据Id查询用户的头像
    RestObject SelectUserHeadSculpture(String userId);
}
