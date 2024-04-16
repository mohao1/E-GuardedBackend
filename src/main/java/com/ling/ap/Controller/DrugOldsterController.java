package com.ling.ap.Controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Dao.DrugNumMapper;
import com.ling.ap.Pojo.Entity.Drug;
import com.ling.ap.Pojo.Entity.DrugNum;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Pojo.ToDo.InsertDrugPojo;
import com.ling.ap.Service.ServiceInterface.DrugService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.Date;

//长辈药品接口
@RestController
@RequestMapping("/Drug/Oldster")
public class DrugOldsterController {

    @Resource
    DrugService drugService;

    @Resource
    DrugNumMapper drugNumMapper;



    //长辈设置药品提醒
    //Drug drug, DrugNum drugNum
    @PostMapping("/InsertDrug")
    public RestObject OldsterInsertDrug(@RequestBody String json, @RequestAttribute Object JWT){

//        InsertDrugPojo pojo = JSONUtil.toBean(json, InsertDrugPojo.class, true);
        InsertDrugPojo pojo = JSONObject.parseObject(json, InsertDrugPojo.class);

        if (json==null){
            return new RestObject("401","data error","数据错误");
        }

        Drug drug = pojo.getDrug();
        DrugNum drugNum = pojo.getDrugNum();

        //数据补偿
        drug.setDrugTime(new Time(pojo.getDrugTime()));
        drug.setSetTime(new Date(pojo.getSetTime()));


        //设置用户Id
        drug.setUserId((String) JWT);
        drugNum.setUserId((String) JWT);
        //设置TakeId
        String takeId = IdUtil.simpleUUID();
        drug.setTakeId(takeId);
        //设置DrugId
        DrugNum drugNum1 = drugNumMapper.SelectDrugNumByOldsterIdAndName(drug.getUserId(), drugNum.getName());
        if (drugNum1==null){
            String drugId= IdUtil.simpleUUID();
            drug.setDrugId(drugId);
            drugNum.setDrugId(drugId);
        }else {
            drug.setDrugId(drugNum1.getDrugId());
        }

        return drugService.OldsterInsertDrug(drug,drugNum);
    }

    //长辈确认药品提醒
    //String takeId, int DeleteNum,String drugId
    @PostMapping("/ConfirmDrug")
    public RestObject OldsterConfirmDrug(@RequestBody JSONObject jsonObject , @RequestAttribute Object JWT) {
        String takeId = jsonObject.getObject("takeId", String.class);
        String drugId = jsonObject.getObject("drugId", String.class);
        int deleteNum = jsonObject.getObject("deleteNum", Integer.class);

        if (takeId==null||drugId==null||deleteNum==0){
            return new RestObject("403","信息错误",jsonObject);
        }

        return drugService.OldsterConfirmDrug((String) JWT,takeId,deleteNum,drugId);
    }

    //长辈查询药品提醒
    @GetMapping("/SelectDrug")
    public RestObject OldsterSelectDrug(@RequestAttribute Object JWT){
//        System.out.println(JWT);
        return drugService.OldsterSelectDrug((String) JWT);
    }

    //长辈修改药品提醒
    //Drug drug
    @PostMapping("/UpdateDrug")
    public RestObject OldsterUpdateDrug(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        jsonObject.put("userId", JWT);

        Drug drug = JSONUtil.toBean(jsonObject.toString(), Drug.class,true);
        if (drug==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        drug.setSetTime(new Date(jsonObject.getObject("setTime",Integer.class)));
        drug.setDrugTime(new Time(jsonObject.getObject("drugTime",Integer.class)));

        return drugService.OldsterUpdateDrug(drug);
    }

    //长辈删除药品提醒
    //String takeId
    @PostMapping("/DeleteDrug")
    public RestObject OldsterDeleteDrug(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        String takeId = jsonObject.getObject("takeId",String.class);
        if (takeId==null){
            return new RestObject("403","信息错误",jsonObject);
        }
        return drugService.OldsterDeleteDrug(takeId, (String) JWT);
    }

    //长辈修改药品数量
    @PostMapping("/UpdateDrugNum")
    public RestObject OldsterUpdateDrugNum(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        jsonObject.put("userId",JWT);
        DrugNum bean = JSONUtil.toBean(jsonObject.toString(), DrugNum.class, true);
        if (bean==null){
            return new RestObject("403","信息错误",jsonObject);
        }
        return drugService.OldsterUpdateDrugNum(bean);
    }

}
