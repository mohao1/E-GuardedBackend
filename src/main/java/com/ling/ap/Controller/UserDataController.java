package com.ling.ap.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.UserDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//获取用户数据信息
@RestController
@RequestMapping("/UserData")
public class UserDataController {
    @Resource
    UserDataService userDataService;

    //晚辈获取自身信息
    @GetMapping("/Oldster")
    public RestObject OldsterGetUserData(@RequestAttribute Object JWT){
        return userDataService.OldsterGetUserData((String) JWT);
    }

    //长辈获取自身信息
    @GetMapping("/Younger")
    public RestObject YoungerGetUserData(@RequestAttribute Object JWT){
        return userDataService.YoungerGetUserData((String) JWT);
    }

    //社区工作人员获取自身信息
    @GetMapping("/CommunityUser")
    public RestObject CommunityUserGetUserData(@RequestAttribute Object JWT){
        return userDataService.CommunityUserGetUserData((String) JWT);
    }


    //修改长辈个人信息
    @PostMapping("/OldsterUpData")
    public RestObject OldsterUpDataUserData(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        jsonObject.put("userId",JWT);
        jsonObject.put("homeId","");
        jsonObject.put("communityId","");
        Oldster oldster = JSONObject.parseObject(jsonObject.toString(), Oldster.class);
        return userDataService.OldsterUpDataUserData(oldster);
    }

    //修改晚辈个人信息
    @PostMapping("/YoungerUpData")
    public RestObject YoungerUpDataUserData(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        jsonObject.put("userId",JWT);
        jsonObject.put("homeId","");
        Younger younger = JSONObject.parseObject(jsonObject.toString(), Younger.class);
        return userDataService.YoungerUpDataUserData(younger);
    }

    //修改社区人员个人信息
    @PostMapping("/CommunityUserUpData")
    public RestObject CommunityUserUpDataUserData(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        jsonObject.put("userId",JWT);
        jsonObject.put("communityId","");
        CommunityUser communityUser = JSONObject.parseObject(jsonObject.toString(), CommunityUser.class);
        return userDataService.CommunityUserUpDataUserData(communityUser);
    }

}
