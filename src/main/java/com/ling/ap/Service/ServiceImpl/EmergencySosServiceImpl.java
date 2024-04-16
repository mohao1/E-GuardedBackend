package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.SosRecordMapper;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.SosRecord;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Pojo.ToDo.Emergency_Contact;
import com.ling.ap.Service.ServiceInterface.EmergencySosService;
import com.ling.ap.Utils.Const;
import com.ling.ap.Utils.TencentSendSms;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class EmergencySosServiceImpl implements EmergencySosService {
    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    TencentSendSms tencentSendSms;
    @Resource
    SosRecordMapper sosRecordMapper;

    //给全部的晚辈发送信息
    @Override
    public synchronized RestObject MessageForHelp(String id) {

//        判断对应ID查询到的信息是否有效
        ArrayList<Emergency_Contact> YoungerList = oldsterMapper.GetEmergencyContactsList(id);
//        判断对应ID查询到的信息是否有效
        if (YoungerList.isEmpty()){
            return new RestObject("404","Not found","查询不到");
        }
//        ArrayList<Emergency_Contact> YoungerList=new ArrayList();
//        YoungerList.add(new Emergency_Contact("18575842930","小林",1));
//        YoungerList.add(new Emergency_Contact("15012916690","小黄",1));
        //进行数据处理
//        ArrayList<String> signName=new ArrayList();//签名列表
////        signName.add("\""+Const.SIGN_NAME+"\"");//拼接签名列表
//        ArrayList<String> phone=new ArrayList();//手机列表
////        phone.add("\""+"18575842930"+"\"");//拼接手机列表
//        String templateCode= Const.SOS_TEMPLATE_CODE;//求救模板
        String[] phoneList= new String[YoungerList.size()];
        String[] templateParamList={};

        for (int i=0;i<YoungerList.size();i++){
            Emergency_Contact contact = YoungerList.get(i);
            phoneList[i]=contact.getPhone();
        }
        System.out.println(phoneList[0]);

//        Map<String,String> map = new HashMap();
//
//        map.put("signName", String.valueOf(signName));//设置签名
//        map.put("phone", String.valueOf(phone));//设置手机
//        map.put("templateCode",templateCode);//设置模板
//        map.put("templateParam","[]");
        try {
            tencentSendSms.SendSms(phoneList,templateParamList,id,Const.T_SIGN_NAME,Const.T_SOS_TEMPLATE_CODE,Const.APP_KEY_ID);
            return new RestObject("200","Sending succeeded","发送成功");
        } catch (Exception e) {
            return new RestObject("402","发送错误",e);
        }
    }

    //返回最高优先级的人员信息
    @Override
    public RestObject YoungerList(String id) {

        //是否是长辈的id
        Oldster oldster = oldsterMapper.GetOldster(id);
        if (oldster==null){
            return new RestObject("401","Not an elder account","不是长辈账号");
        }

        //查询是否存在
        ArrayList<Emergency_Contact> emergency_contacts = oldsterMapper.GetMaxEmergencyContactsList(id);
        if (emergency_contacts.isEmpty()){
            return new RestObject("404","Not found","查询不到");
        }
        //返回数据
        return new RestObject("200","查询成功",emergency_contacts);
    }

    //求救记录
    @Override
    public synchronized void SosRecord(SosRecord sosRecord) {
        int i = sosRecordMapper.insertRecord(sosRecord);
    }


}
