package com.ling.ap.Service.ServiceImpl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Dao.CommunityAdminMapper;
import com.ling.ap.Dao.ManagementMapper;
import com.ling.ap.Dao.UserLoginMapper;
import com.ling.ap.Pojo.Entity.Management;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.LoginService;
import com.ling.ap.Utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    UserLoginMapper userLoginMapper;

    @Resource
    CommunityAdminMapper communityAdminMapper;

    @Resource
    MyJwt myJwt;

    @Resource
    CodeUtil codeUtil;

    @Resource
    TencentSendSms tencentSendSms;

    @Resource
    ManagementMapper managementMapper;

    private TimedCache<String, String> RegistrationCodeCache = CacheUtil.newTimedCache(300000);

    //手机登录
    @Override
    public RestObject Phone(JSONObject jsonObject) {
        String Phone = jsonObject.getObject("phone", String.class);
        String password = jsonObject.getObject("password", String.class);

        Map<String, String> map = userLoginMapper.PhoneGetInformation(Phone);

        //用户是否注册
        if (map==null){
            return new RestObject("200","user does not exist","用户不存在");
        }

        //登录认证
        if (!password.equals(map.get("password"))){
            return new RestObject("200","Password error","密码错误");
        }
        HashMap<String,Object> m=new HashMap();
        m.put("UserId",map.get("user_id"));

        String Token = myJwt.GetJwt(m);


        return new RestObject("200","登录成功",Token);
    }

    //邮箱登录
    @Override
    public RestObject Email(JSONObject jsonObject) {
        String email = jsonObject.getObject("email", String.class);
        if (email.equals("")){
            return new RestObject("401","邮箱为空",null);
        }
        Object password = jsonObject.getObject("password",String.class);

        Map<String, String> map = userLoginMapper.EmailGetInformation(email);

        //用户是否注册
        if (map==null){
            return new RestObject("200","user does not exist","用户不存在");
        }

        //登录认证
        if (!password.equals(map.get("password"))){
            return new RestObject("200","Password error","密码错误");
        }
        HashMap<String,Object> m=new HashMap();
        m.put("UserId",map.get("user_id"));

        String Token = myJwt.GetJwt(m);

        return new RestObject("200","登录成功",Token);
    }

    //微信登录(等待接入微信)
    @Override
    public RestObject Wechat(JSONObject jsonObject) {
        return null;
    }

    //手机的验证码登录(等待接入手机的验证码)
    @Override
    public RestObject Code(JSONObject jsonObject) {
        String phone = jsonObject.getObject("phone", String.class);
        String code = jsonObject.getObject("code", String.class);

        Map<String, String> map = userLoginMapper.PhoneGetInformation(phone);
        //用户是否注册
        if (map==null){
            return new RestObject("200","user does not exist","用户不存在");
        }


        //获取code
        String rCode = RegistrationCodeCache.get(phone,false);
        if (rCode == null) {
            return new RestObject("401","验证码不存在",null);
        }
        if (!rCode.equals(code)){
            return new RestObject("401","匹配错误",code);
        }

        HashMap<String,Object> m=new HashMap<>();
        m.put("UserId",map.get("user_id"));
        String Token = myJwt.GetJwt(m);

        return new RestObject("200","登录成功",Token);
    }


    //登录验证码的发送
    @Override
    public synchronized RestObject AcquisitionVerificationCode(String phone) {

        Map<String, String> mapUser = userLoginMapper.PhoneGetInformation(phone);

        //用户是否注册
        if (mapUser==null){
            return new RestObject("200","user does not exist","用户不存在");
        }


        String rCode = RegistrationCodeCache.get(phone,false);
        if (rCode!=null){
            return new RestObject<>("401","这个手机已经发送过验证码",phone);
        }

        //生成一个新验证码
        String code = codeUtil.GetCode();
//        Map<String, String> map = new HashMap<>();
//        map.put("phone",phone);
//        map.put("signName", Const.SIGN_NAME);
//        map.put("templateCode",Const.VERIFICATION_TEMPLATE_CODE);
//        map.put("templateParam","{\"code\":\""+code+"\"}");
        String[] phoneList={phone};
        String[] templateParamList={code};

        try {
            RegistrationCodeCache.put(phone,code);
            tencentSendSms.SendSms(phoneList,templateParamList,phone, Const.T_SIGN_NAME,Const.T_VERIFICATION_TEMPLATE_CODE_L,Const.APP_KEY_ID);
            return new RestObject<>("200","发送成功",phone);
        } catch (Exception e) {
            e.printStackTrace();
            return new RestObject<>("200","发送失败",e);
        }
    }



    //社区管理人员登录
    @Override
    public RestObject AdminLogin(JSONObject jsonObject) {
        String adminName = jsonObject.getObject("adminName", String.class);
        if (adminName.equals("")){
            return new RestObject("401","用户账号",null);
        }

        Object password = jsonObject.getObject("password",String.class);

        Map<String, String> map = communityAdminMapper.SelectedLogin(adminName);

        //用户是否注册
        if (map==null){
            return new RestObject("200","user does not exist","用户不存在");
        }

        //登录认证
        if (!password.equals(map.get("admin_password"))){
            return new RestObject("200","Password error","密码错误");
        }

        HashMap<String,Object> m=new HashMap();
        m.put("UserId",map.get("admin_id"));

        String Token = myJwt.GetJwt(m);

        return new RestObject("200","登录成功",Token);
    }

    //管理平台登录
    @Override
    public RestObject ManagementLogin(JSONObject jsonObject){
        String managementName = jsonObject.getObject("managementName", String.class);
        if (managementName.equals("")){
            return new RestObject("401","用户账号",null);
        }
        Management management = managementMapper.GetManagement(managementName);
        if (management==null){
            return new RestObject("200","user does not exist","用户不存在");
        }

        Object password = jsonObject.getObject("password",String.class);
        if (!password.equals(management.getPassword())){
            return new RestObject("200","Password error","密码错误");
        }

        HashMap<String,Object> m=new HashMap();
        m.put("UserId",management.getId());
        String Token = myJwt.GetJwt(m);

        return new RestObject("200","登录成功",Token);
    }
}
