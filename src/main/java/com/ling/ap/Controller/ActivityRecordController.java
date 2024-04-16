package com.ling.ap.Controller;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.ActivityRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ActivityRecord")
public class ActivityRecordController {

    @Resource
    ActivityRecordService activityRecordService;

    @PostMapping("/GetActivityRecord")
    public RestObject GetActivityRecord(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT ){
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        if (oldsterId==null){
            return new RestObject("401","数据错误","error");
        }
        return activityRecordService.GetActivityRecordList(oldsterId, (String) JWT);
    }


}
