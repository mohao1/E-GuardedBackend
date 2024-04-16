package com.ling.ap.Controller;

import cn.hutool.core.util.IdUtil;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.SosRecord;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.EmergencySosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;


//老人紧急呼救
@RestController
@RequestMapping("/Sos")
public class EmergencySosController {

    @Resource
    EmergencySosService emergencySosService;

    @Resource
    OldsterMapper oldsterMapper;

    //发送信息
    @GetMapping("/MessageForHelp")
    public RestObject MessageForHelp(@RequestAttribute Object JWT){
        //判断是否合法长辈账号
        Oldster oldster = oldsterMapper.GetOldster((String) JWT);
        if (oldster==null){
            return new RestObject("401","Not an elder account","不是长辈账号");
        }

        String homeId = oldster.getHomeId();

        if (homeId.equals("")){
            return new RestObject("401","No binding home","没有绑定家庭");
        }

//        发送紧急求救信息
        RestObject restObject = emergencySosService.MessageForHelp((String) JWT);

        //获取当天时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate2=year+"-"+month+"-"+dates;

        emergencySosService.SosRecord(new SosRecord(IdUtil.simpleUUID(), (String) JWT,strDate2,0));

        return restObject;
    }


    @GetMapping("/EmergencyCall")
    public RestObject EmergencyCall(@RequestAttribute Object JWT){

        //获取当天时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate2=year+"-"+month+"-"+dates;

        emergencySosService.SosRecord(new SosRecord(IdUtil.simpleUUID(), (String) JWT,strDate2,1));

        return emergencySosService.YoungerList((String) JWT);
    }
}
