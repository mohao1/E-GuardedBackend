package com.ling.ap.Controller;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CommunityAdminService;
import com.ling.ap.Service.ServiceInterface.CommunityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Community/Admin")
public class AdminCommunityController {

    @Resource
    CommunityService communityService;

    @Resource
    CommunityAdminService communityAdminService;

    //社区管理人员查看申请人员列表
    //String AdminId
    @GetMapping("/GetBindCommunity")
    public RestObject CommunityAdminGetBindCommunity(@RequestAttribute Object JWT) {
        return communityService.CommunityAdminGetBindCommunity((String) JWT);
    }

    //社区管理人员通过绑定社区
    //String AdminId, String CommunityUserId,String CommunityId
    @PostMapping("/BindCommunity")
    public synchronized RestObject CommunityAdminBindCommunity(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT ) {
        String communityId = jsonObject.getObject("communityId", String.class);
        String communityUserId = jsonObject.getObject("communityUserId", String.class);
        if (communityId==null||communityUserId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityService.CommunityAdminBindCommunity((String) JWT,communityUserId,communityId);
    }

    //社区管理人员拒绝绑定社区
    //String AdminId, String CommunityUserId,String CommunityId
    @PostMapping("/UnBindCommunity")
    public synchronized RestObject CommunityAdminUnBindCommunity(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String communityId = jsonObject.getObject("communityId", String.class);
        String communityUserId = jsonObject.getObject("communityUserId", String.class);
        if (communityId==null||communityUserId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityService.CommunityAdminUnBindCommunity((String) JWT,communityUserId,communityId);
    }

    //社区管理人员移除社区用户
    //String AdminId, String CommunityUserId
    @PostMapping("/DeleteBindCommunity")
    public RestObject CommunityAdminDeleteBindCommunity(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String communityUserId = jsonObject.getObject("communityUserId", String.class);
        if (communityUserId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityService.CommunityAdminDeleteBindCommunity((String) JWT,communityUserId);
    }

    //查询管理社区中的长辈信息列表
    @GetMapping("/SelectedOldsterList")
    public RestObject SelectedOldsterList(@RequestAttribute Object JWT) {
        return communityAdminService.SelectedOldsterList((String) JWT);
    }

    //查询管理社区中的社区人员信息列表
    @GetMapping("/SelectedCommunityUserList")
    public RestObject SelectedCommunityUserList(@RequestAttribute Object JWT) {
        return communityAdminService.SelectedCommunityUserList((String) JWT);
    }

    //社区管理人员移除社区长辈账号
    @PostMapping("/DeleteBindOldster")
    public RestObject CommunityAdminDeleteBindOldster(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        if (oldsterId==null){
            return new RestObject("401","数据错误","error");
        }
        return communityService.CommunityAdminDeleteBindOldster((String) JWT,oldsterId);
    }

    //社区管理人员查询社区信息
    @GetMapping("/SelectedCommunity")
    public RestObject SelectedCommunity(@RequestAttribute Object JWT) {
        return communityAdminService.SelectedCommunity((String) JWT);
    }

    //社区管理人员查询社区某个长辈健康信息
    @PostMapping("/SelectedOldsterHealth")
    public RestObject SelectedOldsterHealth(@RequestAttribute Object JWT, @RequestBody JSONObject jsonObject){
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return communityAdminService.SelectedOldsterHealth((String) JWT,oldsterId);
    }
}
