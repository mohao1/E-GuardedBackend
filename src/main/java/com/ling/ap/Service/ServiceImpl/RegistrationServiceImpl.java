package com.ling.ap.Service.ServiceImpl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.ling.ap.Dao.UserLoginMapper;
import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.UserLogin;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HeadSculptureService;
import com.ling.ap.Service.ServiceInterface.RTransactionalService;
import com.ling.ap.Service.ServiceInterface.RegistrationService;
import com.ling.ap.Utils.CodeUtil;
import com.ling.ap.Utils.Const;
import com.ling.ap.Utils.TencentSendSms;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Resource
    UserLoginMapper userLoginMapper;

    @Resource
    RTransactionalService rTransactionalService;

    @Resource
    CodeUtil codeUtil;

    @Resource
    TencentSendSms tencentSendSms;

    @Resource
    HeadSculptureService headSculptureService;

    //有效时间
    private TimedCache<String, String> RegistrationCodeCache = CacheUtil.newTimedCache(300000);


    //用户注册功能（长辈）
    @Override
    public RestObject OldsterRegistration(Oldster oldster, UserLogin userLogin) {
        //获取手机号是否的存在
        Map<String, String> p = userLoginMapper.PhoneGetInformation(userLogin.getPhone());
        if (p!=null){
            return new RestObject("200","Mobile account exists","手机存在账号");
        }
        if (!userLogin.getEmail().equals("")){
            Map<String, String> e = userLoginMapper.EmailGetInformation(userLogin.getEmail());
            if (e!=null){
                return new RestObject("200","Email account exists","邮箱存在账号");
            }
        }
        boolean flag = headSculptureService.OldsterInitUserHeadSculpture(oldster.getUserId(), oldster.getSex());
        if (!flag){
            return new RestObject("200","login has failed","注册失败");
        }

        //写入数据
        int i = rTransactionalService.ORTransactional(oldster, userLogin);
        if (i==0){
            return new RestObject("200","login has failed","注册失败");
        }
        return new RestObject("200","login was successful","注册成功");
    }

    @Transactional
    //用户注册功能（晚辈）
    @Override
    public RestObject YoungerRegistration(Younger younger, UserLogin userLogin) {
        //获取手机号是否的存在
        Map<String, String> p = userLoginMapper.PhoneGetInformation(userLogin.getPhone());
        if (p!=null){
            return new RestObject("200","Mobile account exists","手机存在账号");
        }

        if (!userLogin.getEmail().equals("")) {
            Map<String, String> e = userLoginMapper.EmailGetInformation(userLogin.getEmail());
            if (e != null) {
                return new RestObject("200", "Email account exists", "邮箱存在账号");
            }
        }
        boolean flag = headSculptureService.YoungerInitUserHeadSculpture(younger.getUserId(), younger.getSex());
        if (!flag){
            return new RestObject("200","login has failed","注册失败");
        }

        //写入数据
        int i = rTransactionalService.YRTransactional(younger,userLogin);
        if (i==0){
            return new RestObject("200","login has failed","注册失败");
        }
        return new RestObject("200","login was successful","注册成功");
    }


    //用户注册功能（社区人员）
    @Override
    public RestObject CommunityUserRegistration(CommunityUser communityUser, UserLogin userLogin) {
        Map<String, String> p = userLoginMapper.PhoneGetInformation(userLogin.getPhone());
        if (p!=null){
            return new RestObject("200","Mobile account exists","手机存在账号");
        }

        if (!userLogin.getEmail().equals("")) {
            Map<String, String> e = userLoginMapper.EmailGetInformation(userLogin.getEmail());
            if (e != null) {
                return new RestObject("200", "Email account exists", "邮箱存在账号");
            }
        }

        boolean flag = headSculptureService.YoungerInitUserHeadSculpture(communityUser.getUserId(), communityUser.getSex());
        if (!flag){
            return new RestObject("200","login has failed","注册失败");
        }

        int i = rTransactionalService.CRTransactional(communityUser, userLogin);
        if (i==0){
            return new RestObject("200","login has failed","注册失败");
        }
        return new RestObject("200","login was successful","注册成功");
    }


    //注册验证码的获取发送(发送验证码并存入缓存)
    public synchronized RestObject AcquisitionVerificationCode(String phone){
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
//        String [] phoneList ,String[] templateParamList,String userId,String SignName,String TemplateId,String AppId
        String[] phoneList={phone};
        String[] templateParamList={code};

        try {
            RegistrationCodeCache.put(phone,code);
            tencentSendSms.SendSms(phoneList,templateParamList,phone, Const.T_SIGN_NAME,Const.T_VERIFICATION_TEMPLATE_CODE_R,Const.APP_KEY_ID);
            return new RestObject<>("200","发送成功",phone);
        } catch (Exception e) {
            e.printStackTrace();
            return new RestObject<>("200","发送失败",e);
        }
    }


    //注册验证码的确认
    public synchronized boolean  VerificationCodeConfirmation( String phone,String code){
        //获取code
        String rCode = RegistrationCodeCache.get(phone,false);
        if (rCode==null){
            return false;
        }
        //判断code
        return rCode.equals(code);
    }

}
