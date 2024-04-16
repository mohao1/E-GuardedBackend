package com.ling.ap.Controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.UserLogin;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Pojo.ToDo.CommunityUserRegistration;
import com.ling.ap.Pojo.ToDo.OldsterRegistrationPojo;
import com.ling.ap.Pojo.ToDo.YoungerRegistrationPojo;
import com.ling.ap.Service.ServiceInterface.RegistrationService;
import com.ling.ap.Utils.UIDS;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RegistrationController {
    //获取业务
    @Resource
    RegistrationService userLoginService;

    @Resource
    UIDS uids;

    /**
     * 长辈注册
     * @RequestBody String jsonObject==>接收数据
     *
     * {
     *       "userLogin":{
     *         "userId":"",
     *         "password":"123456",
     *         "phone":"18575842933",
     *         "email":"2689645613@qq.com",
     *         "wechat":"",
     *         "address":"某某小区",
     *     },
     *     "oldster":{
     *         "userId":"",
     *         "homeId":"",
     *         "communityId":"",
     *         "name":"老王",
     *         "age":76,
     *         "sex":1,
     *     },
     *     "code":"1234"
     * }
     *
     * @return new RestObject
     *
     * */
    @PostMapping ("/Registration/Oldster")
    public RestObject Oldster(@RequestBody String jsonObject){

        OldsterRegistrationPojo pojo = JSONUtil.toBean(jsonObject, OldsterRegistrationPojo.class, true);



        if (pojo==null){
            return new RestObject("401","data error","数据错误");
        }



        //判断手机的验证码是否正确
        String code = pojo.getCode();
        boolean bole = userLoginService.VerificationCodeConfirmation(pojo.getUserLogin().getPhone(), code);
        if (!bole){
            return new RestObject("401","error","手机的验证码错误");
        }



        //获取对象
        UserLogin userLogin = pojo.getUserLogin();
        Oldster oldster = pojo.getOldster();
        if (userLogin!=null&&oldster!=null){
            //生成UUID（用户唯一ID）
//            String UUID = IdUtil.simpleUUID();

            String UUID = uids.GetUIDS("P");
            //设置UserId
            oldster.setUserId(UUID);
            userLogin.setUserId(UUID);
        }else{
            return new RestObject("401","data error","数据错误");
        }

        //进行注册处理
        RestObject restObject = userLoginService.OldsterRegistration(oldster, userLogin);
        return restObject;
    }


    /**
     * 晚辈注册
     * @RequestBody String jsonObject==>接收数据
     *
     * {
     *       "userLogin":{
     *         "userId":"",
     *         "password":"123456",
     *         "phone":"18575842937",
     *         "email":"2689645613@qq.com",
     *         "wechat":"",
     *         "address":"某某小区",
     *     },
     *     "younger":{
     *         "userId":"",
     *         "homeId":"",
     *         "name":"小王",
     *         "age":20,
     *         "sex":1,
     *         "priority":0
     *     },
     *     "code":"1234"
     * }
     *
     * @return new RestObject
     *
     * */
    @PostMapping("/Registration/Younger")
    public RestObject Younger(@RequestBody String jsonObject){

        YoungerRegistrationPojo pojo = JSONUtil.toBean(jsonObject,YoungerRegistrationPojo.class, true);


        if (pojo==null){
            return new RestObject("401","data error","数据错误");
        }



        //判断手机的验证码是否正确
        String code = pojo.getCode();
        boolean bole = userLoginService.VerificationCodeConfirmation(pojo.getUserLogin().getPhone(), code);
        if (!bole){
            return new RestObject("401","error","手机的验证码错误");
        }



        //获取对象
        UserLogin userLogin = pojo.getUserLogin();
        Younger younger = pojo.getYounger();
        if (userLogin!=null&&younger!=null){
            //生成UUID（用户唯一ID）
//            String UUID = IdUtil.simpleUUID();
            String UUID = uids.GetUIDS("C");;
            //设置UserId
            younger.setUserId(UUID);
            userLogin.setUserId(UUID);
        }else{
            return new RestObject("401","data error","数据错误");
        }

        //进行注册处理
        RestObject restObject = userLoginService.YoungerRegistration(younger, userLogin);
        return restObject;
    }

    /**
     * 社区人员
     * */
    @PostMapping("/Registration/CommunityUser")
    public RestObject CommunityUser(@RequestBody String jsonObject) {
        CommunityUserRegistration pojo = JSONUtil.toBean(jsonObject, CommunityUserRegistration.class, true);

        if (pojo==null){
            return new RestObject("401","data error","数据错误");
        }

        String code = pojo.getCode();
        boolean bole = userLoginService.VerificationCodeConfirmation(pojo.getUserLogin().getPhone(), code);
        if (!bole){
            return new RestObject("401","error","手机的验证码错误");
        }

        CommunityUser communityUser = pojo.getCommunityUser();
        UserLogin userLogin = pojo.getUserLogin();

        if (userLogin!=null&&communityUser!=null){
            //生成UUID（用户唯一ID）
//            String UUID = IdUtil.simpleUUID();
            String UUID = uids.GetUIDS("V");
            //设置UserId
            communityUser.setUserId(UUID);
            userLogin.setUserId(UUID);
        }else{
            return new RestObject("401","data error","数据错误");
        }

        RestObject restObject = userLoginService.CommunityUserRegistration(communityUser, userLogin);
        return restObject;
    }

    //注册验证码的获取
    @PostMapping("/Registration/GetCode")
    public RestObject GetCode(@RequestBody JSONObject jsonObject){
        if (jsonObject==null) {
            return new RestObject("401", "Data is empty", "数据为空");
        }
        String phone = jsonObject.getObject("phone",String.class);
        if (phone==null){
            return new RestObject("401", "Mobile phone is empty", "手机为空");
        }

        if (phone.equals("")){
            return new RestObject("401", "Mobile phone is empty", "手机为空");
        }

        return userLoginService.AcquisitionVerificationCode(phone);
    }

}
