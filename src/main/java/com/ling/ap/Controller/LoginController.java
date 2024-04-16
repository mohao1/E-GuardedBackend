package com.ling.ap.Controller;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping(value = "/Login",method = {RequestMethod.POST,RequestMethod.GET})
public class LoginController {

    @Resource
    LoginService loginService;


    //手机登录
    @PostMapping("/Phone")
    public RestObject PhoneLogin(@RequestBody JSONObject jsonObject){
        if (jsonObject!=null){
            return loginService.Phone(jsonObject);
        }
        return new RestObject("401","Null","数据为空");
    }

    //邮箱登录
    @PostMapping("/Email")
    public RestObject EmailLogin(@RequestBody JSONObject jsonObject){
        if (jsonObject!=null){
            return loginService.Email(jsonObject);
        }
        return new RestObject("401","Null","数据为空");
    }


    //社区管理人员登录
    @PostMapping("/Admin")
    public RestObject AdminLogin(@RequestBody JSONObject jsonObject) {
        if (jsonObject!=null){
            return loginService.AdminLogin(jsonObject);
        }
        return new RestObject("401","Null","数据为空");
    }

    //手机的验证码登录
    @PostMapping("/Code")
    public RestObject Code(@RequestBody JSONObject jsonObject) {
        if (jsonObject!=null){
            return loginService.Code(jsonObject);
        }
        return new RestObject("401","Null","数据为空");
    }


    //登录验证码的发送
    @PostMapping("/AVCode")
    public RestObject AcquisitionVerificationCode(@RequestBody JSONObject jsonObject) {
        if (jsonObject!=null){
            String phone = jsonObject.getObject("phone", String.class);
            return loginService.AcquisitionVerificationCode(phone);
        }
        return new RestObject("401","Null","数据为空");

    }

    //管理平台登录
    @PostMapping("/ManagementLogin")
    public RestObject ManagementLogin(@RequestBody JSONObject jsonObject){
        if (jsonObject!=null){
            return loginService.ManagementLogin(jsonObject);
        }
        return new RestObject("401","Null","数据为空");
    }

}
