package com.ling.ap.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.ManagementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping(value = "/Management",method = {RequestMethod.POST,RequestMethod.GET})

public class ManagementController {
    @Resource
    ManagementService managementService;

    //获取自身信息
    @GetMapping("/GetManagementData")
    public RestObject GetManagementData(@RequestAttribute Object JWT) {
        return managementService.GetManagementData((String) JWT);
    }

    //查询长辈人员列表
    @GetMapping("/GetOldsterDataList")
    public RestObject GetOldsterDataList(@RequestAttribute Object JWT) {
        return managementService.GetOldsterDataList((String) JWT);
    }

    //查询晚辈列表
    @GetMapping("/GetYoungerDataList")
    public RestObject GetYoungerDataList(@RequestAttribute Object JWT) {
        return managementService.GetYoungerDataList((String) JWT);
    }

    //查询社区人员列表
    @GetMapping("/GetCommunityUserDataList")
    public RestObject GetCommunityUserDataList(@RequestAttribute Object JWT) {
        return managementService.GetCommunityUserDataList((String) JWT);
    }

    //查询社区管理人员列表
    @GetMapping("/GetCommunityAdminDataList")
    public RestObject GetCommunityAdminDataList(@RequestAttribute Object JWT) {
        return managementService.GetCommunityAdminDataList((String) JWT);
    }

    //查询家庭列表
    @GetMapping("/GetHomeDataList")
    public RestObject GetHomeDataList(@RequestAttribute Object JWT) {
        return managementService.GetHomeDataList((String) JWT);
    }

    //查询社区列表
    @GetMapping("/GetCommunityDataList")
    public RestObject GetCommunityDataList(@RequestAttribute Object JWT) {
        return managementService.GetCommunityDataList((String) JWT);
    }

    //根据id查询长辈当天健康情况
    //String Id, String oldsterId
    @PostMapping("/GetOldsterHealthData")
    public RestObject GetOldsterHealthData(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return managementService.GetOldsterHealthData((String) JWT,oldsterId);
    }

    //根据id查询长辈10天内健康情况
    //String Id, String oldsterId
    @PostMapping("/GetOldsterHealthDataDays")
    public RestObject GetOldsterHealthDataDays(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return managementService.GetOldsterHealthDataDays((String) JWT,oldsterId);
    }

    //查询求救所有记录
    //String Id
    @GetMapping("/SelectRecordList")
    public RestObject SelectRecordList(@RequestAttribute Object JWT) {
        return managementService.SelectRecordList((String) JWT);
    }

    //根据时间信息查询信息
    //String Id, String Time
    @PostMapping("/SelectRecordListByTime")
    public RestObject SelectRecordListByTime(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String time = jsonObject.getObject("time", String.class);
        return managementService.SelectRecordListByTime((String) JWT,time);

    }

    //根据长辈id查询信息
    //String Id, String oldsterId
    @PostMapping("/SelectRecordListByOldsterId")
    public RestObject SelectRecordListByOldsterId(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return managementService.SelectRecordListByOldsterId((String) JWT,oldsterId);
    }

    //根据信息id查询信息
    //String Id, String RecordId
    @PostMapping("/SelectRecordById")
    public RestObject SelectRecordById(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String recordId = jsonObject.getObject("recordId", String.class);
        return managementService.SelectRecordById((String) JWT,recordId);
    }
}
