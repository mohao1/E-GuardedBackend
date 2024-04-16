package com.ling.ap.Controller;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CommunityService;
import com.ling.ap.Service.ServiceInterface.CommunityUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Community/User")
public class UserCommunityController {

    @Resource
    CommunityService communityService;

    @Resource
    CommunityUserService communityUserService;

    //社区人员申请绑定社区
    //String CommunityId, String CommunityUserId
    @PostMapping("/BindCommunity")
    public RestObject CommunityUserApplicationBindCommunity(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String communityId = jsonObject.getObject("communityId", String.class);
        if (communityId==null){
            return new RestObject("401","数据错误","error");
        }

        return communityService.CommunityUserApplicationBindCommunity(communityId, (String) JWT);
    }

    //社区人员退出绑定社区
    //String CommunityUserId,String CommunityId
    @PostMapping("/UnBindCommunity")
    public RestObject CommunityUserUnBindCommunity(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String communityId = jsonObject.getObject("communityId", String.class);
        if (communityId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityService.CommunityUserUnBindCommunity((String) JWT,communityId);
    }

    //查询管理长辈信息列表
    //String CommunityUserId
    @GetMapping("/GetOldsterList")
    public RestObject GetOldsterList(@RequestAttribute Object JWT) {
        return communityUserService.GetOldsterList((String) JWT);
    }

    //自己所在社区信息
    //String CommunityUserId
    @GetMapping("/GetCommunity")
    public RestObject GetCommunity(@RequestAttribute Object JWT) {
        return communityUserService.GetCommunity((String) JWT);
    }

    //查询管理社区某个长辈身体状况十天
    //String CommunityUserId, String OldsterId
    @PostMapping("/GetOldsterHealth")
    public RestObject GetOldsterHealth(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        if (oldsterId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityUserService.GetOldsterHealth((String) JWT,oldsterId);
    }

    //查询管理社区某个长辈身体基础疾病列表
    //String CommunityUserId, String OldsterId
    @PostMapping("/GetOldsterBasicDiseases")
    public RestObject GetOldsterBasicDiseases(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        if (oldsterId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityUserService.GetOldsterBasicDiseases((String) JWT,oldsterId);
    }


    //查询长辈当天健康数据
    //String CommunityUserId, String OldsterId
    @PostMapping("/GetHealthToday")
    public RestObject GetHealthToday(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT){
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        if (oldsterId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityUserService.GetHealthToday((String) JWT,oldsterId);
    }
}
