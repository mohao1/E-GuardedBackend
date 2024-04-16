package com.ling.ap.Controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.Entity.Foodstuff;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.FoodstuffService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


//食品记录
@RestController
@RequestMapping("/Foodstuff/Oldster")
public class FoodstuffOldsterController {

    @Resource
    FoodstuffService foodstuffService;

    //长辈设置食品记录
    //Foodstuff foodstuff
    @PostMapping("/InsertFoodstuff")
    public RestObject OldsterInsertFoodstuff(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        //生成takeId
        String takeId = IdUtil.simpleUUID();
        jsonObject.put("takeId", takeId);
        //绑定用户Id
        jsonObject.put("userId",JWT);

//        Foodstuff bean = JSONUtil.toBean(jsonObject.toString(), Foodstuff.class, true);
        Foodstuff foodstuff = JSONObject.parseObject(jsonObject.toString(), Foodstuff.class);
        return foodstuffService.OldsterInsertFoodstuff(foodstuff);

    }

    //长辈查询食品记录
    @GetMapping("/SelectFoodstuff")
    public RestObject OldsterSelectFoodstuff(@RequestAttribute Object JWT) {
        return foodstuffService.OldsterSelectFoodstuff((String) JWT);
    }

    //长辈删除食品记录
    //String oldster, String takeId
    @PostMapping("/DeleteFoodstuff")
    public RestObject OldsterDeleteFoodstuff(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        String takeId = jsonObject.getObject("takeId", String.class);
        if (takeId==null){
            return new RestObject("403","信息错误",jsonObject);
        }
        return foodstuffService.OldsterDeleteFoodstuff((String) JWT,takeId);
    }

    //长辈恢复食品记录
    //String oldster, String takeId
    @PostMapping("/ResumeFoodstuff")
    public RestObject OldsterResumeFoodstuff(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        String takeId = jsonObject.getObject("takeId", String.class);
        if (takeId==null){
            return new RestObject("403","信息错误",jsonObject);
        }
        return foodstuffService.OldsterResumeFoodstuff((String) JWT,takeId);

    }

    //长辈修改食品记录
    @PostMapping("/UpdateFoodstuff")
    public RestObject OldsterUpdateFoodstuff(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        //绑定用户Id
        jsonObject.put("userId",JWT);

        Foodstuff foodstuff = JSONObject.parseObject(jsonObject.toString(), Foodstuff.class);
        if (foodstuff==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return foodstuffService.OldsterUpdateFoodstuff(foodstuff);
    }

    //长辈查询回收站的记录

    @GetMapping("/RetrieveFoodstuff")
    public RestObject OldsterSelectRetrieveFoodstuff(@RequestAttribute Object JWT) {
        return foodstuffService.OldsterSelectRetrieveFoodstuff((String) JWT);
    }

    //长辈清除回收站的记录
    //String oldster, String takeId
    @PostMapping("/CleanFoodstuff")
    public RestObject OldsterCleanFoodstuff(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {

        String takeId = jsonObject.getObject("takeId", String.class);
        if (takeId==null){
            return new RestObject("403","信息错误",jsonObject);
        }
        return foodstuffService.OldsterCleanFoodstuff((String) JWT,takeId);

    }

    //长辈清除回收站的所有数据
    @GetMapping("/CleanAllFoodstuff")
    public RestObject OldsterCleanAllFoodstuff(@RequestAttribute Object JWT) {
        return foodstuffService.OldsterCleanAllFoodstuff((String) JWT);
    }
}
