package com.ling.ap.Controller;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CommunityOldsterService;
import com.ling.ap.Service.ServiceInterface.CommunityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Community/Oldster")
public class OldsterCommunityController {

    @Resource
    CommunityService communityService;

    @Resource
    CommunityOldsterService communityOldsterService;

    //长辈绑定社区
    //String CommunityId, String OldsterId
    @PostMapping("/BindCommunity")
    public RestObject OldsterBindCommunity(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT){
        String communityId = jsonObject.getObject("communityId", String.class);
        if (communityId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityService.OldsterBindCommunity(communityId, (String) JWT);
    }

    //长辈退出绑定社区
    //String CommunityId, String OldsterId
    @PostMapping("/UnBindCommunity")
    public RestObject OldsterUnBindCommunity(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String communityId = jsonObject.getObject("communityId", String.class);
        if (communityId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityService.OldsterUnBindCommunity(communityId, (String) JWT);
    }

    //长辈查询社区人员列表
    //String OldsterId
    @GetMapping("/GetCommunityUserList")
    public RestObject OldsterGetCommunityUserList(@RequestAttribute Object JWT) {
        return communityOldsterService.OldsterGetCommunityUserList((String) JWT);
    }

    //长辈查询某个社区人员信息
    //String OldsterId, String CommunityUserId
    @PostMapping("/GetCommunityUser")
    public RestObject OldsterGetCommunityUser(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String communityUserId = jsonObject.getObject("communityUserId", String.class);
        if (communityUserId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityOldsterService.OldsterGetCommunityUser((String) JWT,communityUserId);

    }

    //长辈查询社区信息
    //String OldsterId
    @GetMapping("/GetCommunity")
    public RestObject OldsterGetCommunity(@RequestAttribute Object JWT) {
        return communityOldsterService.OldsterGetCommunity((String) JWT);
    }

}
