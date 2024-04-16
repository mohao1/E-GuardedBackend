package com.ling.ap.Controller;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Pojo.Entity.Home;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HomeService;
import com.ling.ap.Utils.UIDS;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Binding")
public class BindHomeController {

    @Resource
    HomeService homeService;

    @Resource
    UIDS uids;

//    String homeName;
    //创建家庭
    @PostMapping("/CreateHome")
    public RestObject  CreateHome(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String homeId=uids.GetUIDS("H");
        jsonObject.put("homeId", homeId);
        jsonObject.put("admin",JWT);
        jsonObject.put("userNum",1);
        Home home = jsonObject.toJavaObject(Home.class);
        return homeService.CreateHome(home);
    }

    //删除家庭
    //"homeId"
    @PostMapping("/DeleteHome")
    public RestObject DeleteHome(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT){

        return homeService.DeleteHome(jsonObject, (String) JWT);
    }


    //长辈绑定家庭
    //HomeId
    @PostMapping("/OldsterBindHome")
    public RestObject OldsterBindHome(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT){
        return homeService.OldsterBindHome(jsonObject, (String) JWT);
    }

    //晚辈绑定家庭
    //HomeId
    //priority
    @PostMapping("/YoungerBindHome")
    public RestObject YoungerBindHome(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT){
        return homeService.YoungerBindHome(jsonObject, (String) JWT);
    }

    //长辈解除绑定家庭
    @GetMapping("/OldsterUnBindHome")
    public RestObject OldsterUnBindHome(@RequestAttribute Object JWT){
        return homeService.OldsterUnBindHome((String) JWT);
    }

    //晚辈解除绑定家庭
    @GetMapping("/YoungerUnBindHome")
    public RestObject YoungerUnBindHome(@RequestAttribute Object JWT){
        return homeService.YoungerUnBindHome((String) JWT);
    }

}
