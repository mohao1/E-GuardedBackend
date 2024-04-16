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
@RequestMapping("/Foodstuff/Younger")
public class FoodstuffYoungerController {

    @Resource
    FoodstuffService foodstuffService;


    //晚辈查询食品记录
    //String oldster, String younger
    @PostMapping("/SelectFoodstuff")
    public RestObject YoungerSelectFoodstuff(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldster = jsonObject.getObject("oldster", String.class);
        if (oldster==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return foodstuffService.YoungerSelectFoodstuff(oldster, (String) JWT);
    }

    //晚辈查询回收站的记录
    //String oldster, String younger
    @PostMapping("/SelectRetrieveFoodstuff")
    public RestObject YoungerSelectRetrieveFoodstuff(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldster = jsonObject.getObject("oldster", String.class);
        if (oldster==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return foodstuffService.YoungerSelectRetrieveFoodstuff(oldster, (String) JWT);
    }

    //晚辈清除回收站的记录
    //String oldster, String takeId, String younger
    @PostMapping("/CleanFoodstuff")
    public RestObject YoungerCleanFoodstuff(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldster = jsonObject.getObject("oldster", String.class);
        String takeId = jsonObject.getObject("takeId", String.class);
        if (oldster==null||takeId==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return foodstuffService.YoungerCleanFoodstuff(oldster,takeId, (String) JWT);
    }

    //晚辈清除回收站的所有数据
    //String oldster, String younger
    @PostMapping("/CleanAllFoodstuff")
    public RestObject YoungerCleanAllFoodstuff(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldster = jsonObject.getObject("oldster", String.class);
        if (oldster==null){
            return new RestObject("403","信息错误",jsonObject);
        }
        return foodstuffService.YoungerCleanAllFoodstuff(oldster, (String) JWT);
    }

    //晚辈删除食品记录
    //String oldster, String takeId, String younger
    @PostMapping("/DeleteFoodstuff")
    public RestObject YoungerDeleteFoodstuff(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldster = jsonObject.getObject("oldster", String.class);
        String takeId = jsonObject.getObject("takeId", String.class);
        if (oldster==null||takeId==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return foodstuffService.YoungerDeleteFoodstuff(oldster,takeId, (String) JWT);
    }

    //晚辈恢复食品记录
    //String oldster, String takeId, String younger
    @PostMapping("/ResumeFoodstuff")
    public RestObject YoungerResumeFoodstuff(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String oldster = jsonObject.getObject("oldster", String.class);
        String takeId = jsonObject.getObject("takeId", String.class);
        if (oldster==null||takeId==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return foodstuffService.YoungerResumeFoodstuff(oldster,takeId, (String) JWT);
    }

    //晚辈设置食品记录
    //Foodstuff foodstuff, String younger
    @PostMapping("/InsertFoodstuff")
    public RestObject YoungerInsertFoodstuff(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        //生成takeId
        String takeId = IdUtil.simpleUUID();
        jsonObject.put("takeId", takeId);

        Foodstuff foodstuff = JSONObject.parseObject(jsonObject.toString(), Foodstuff.class);
        return foodstuffService.YoungerInsertFoodstuff(foodstuff, (String) JWT);
    }

    //修改食品记录
    //Foodstuff foodstuff, String younger
    @PostMapping("/UpdateFoodstuff")
    public RestObject YoungerUpdateFoodstuff(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {

        Foodstuff foodstuff = JSONObject.parseObject(jsonObject.toString(), Foodstuff.class);
        if (foodstuff==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return foodstuffService.YoungerUpdateFoodstuff(foodstuff, (String) JWT);
    }
}
