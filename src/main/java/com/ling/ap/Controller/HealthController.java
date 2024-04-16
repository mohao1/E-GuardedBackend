package com.ling.ap.Controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.Entity.Health;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.ActivityRecordService;
import com.ling.ap.Service.ServiceInterface.HealthScoreService;
import com.ling.ap.Service.ServiceInterface.HealthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


//健康数据
@RestController
@RequestMapping("/Health")
public class HealthController {

    @Resource
    HealthService healthService;
    @Resource
    HealthScoreService healthScoreService;

    @Resource
    ActivityRecordService activityRecordService;

    //写入长辈健康数据
    @PostMapping("/SetHealthCache")
    public RestObject SetHealthCache(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT){
        jsonObject.put("userId", JWT);
        Health bean = JSONUtil.toBean(jsonObject.toString(), Health.class, false);
        RestObject restObject = healthService.SetHealthCache(bean);
        if (restObject.getCode().equals("200")){
            String HealthStr=
                    "低血压为:"+bean.getBloodLow()+
                            ",高血压为:"+bean.getBloodPressure()+
                            ",饭前血糖:"+bean.getBloodSugar()+
                            ",饭后血糖:"+bean.getHeartRate();
            activityRecordService.SetActivityRecord(bean.getUserId(),"录入健康数值",HealthStr);
        }
        return restObject;
    }

    //查询当天健康数据
    @GetMapping("/GetHealthToday")
    public RestObject GetHealthToday(@RequestAttribute Object JWT){
        return healthService.GetHealthToday((String) JWT);
    }

    //查询长辈10天内健康数据
    @GetMapping("/GetHealthTenDays")
    public RestObject GetHealthTenDays(@RequestAttribute Object JWT){
        return healthService.GetHealthTenDays((String) JWT);
    }


    //晚辈查询长辈10天内健康数据
    @PostMapping("/YoungerGetHealthTenDays")
    public RestObject YoungerGetHealthTenDays(@RequestAttribute Object JWT, @RequestBody JSONObject jsonObject){
        if (jsonObject.isEmpty()){
            return new RestObject("403","Missing data","数据缺失");
        }
        String oldsterId = jsonObject.getObject("OldsterId", String.class);
        return healthService.YoungerGetHealthTenDays((String) JWT,oldsterId);
    }

    //晚辈查询长辈当天健康数据
    @PostMapping("/YoungerGetHealthToday")
    public RestObject YoungerGetHealthToday(@RequestAttribute Object JWT, @RequestBody JSONObject jsonObject){
        if (jsonObject.isEmpty()){
            return new RestObject("403","Missing data","数据缺失");
        }
        String oldsterId = jsonObject.getObject("OldsterId", String.class);

        return healthService.YoungerGetHealthToday((String) JWT,oldsterId);
    }

    //长辈签到
    @GetMapping("/OldsterSign")
    public RestObject OldsterSign(@RequestAttribute Object JWT){
        return healthService.OldsterSign((String) JWT);
    }


    //晚辈查询长辈健康评分
    @PostMapping("/GetHealthScore")
    public RestObject GetHealthScore(@RequestAttribute Object JWT,@RequestBody JSONObject jsonObject){
        if (jsonObject.isEmpty()){
            return new RestObject("403","Missing data","数据缺失");
        }
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return healthScoreService.SelectHealthScore((String) JWT,oldsterId);
    }

}
