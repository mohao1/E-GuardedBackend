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

//晚辈药品接口
@RestController
@RequestMapping("/Drug/Younger")
public class DrugYoungerController {

    @Resource
    DrugService drugService;

    @Resource
    DrugNumMapper drugNumMapper;

    //晚辈设置药品提醒
    @PostMapping("/InsertDrug")
    //Drug drug, DrugNum drugNum
    public RestObject YoungerInsertDrug(@RequestBody String json, @RequestAttribute Object JWT) {
//        InsertDrugPojo pojo = JSONUtil.toBean(json, InsertDrugPojo.class, true);
        InsertDrugPojo pojo = JSONObject.parseObject(json, InsertDrugPojo.class);
        if (pojo==null){
            return new RestObject("401","data error","数据错误");
        }

        Drug drug = pojo.getDrug();
        DrugNum drugNum = pojo.getDrugNum();

        //数据补偿
        drug.setDrugTime(new Time(pojo.getDrugTime()));
        drug.setSetTime(new Date(pojo.getSetTime()));

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

        return drugService.YoungerInsertDrug(drug,drugNum, (String) JWT);
    }

    //晚辈查询药品提醒
    //String oldsterId, String youngerId
    @PostMapping("/SelectDrug")
    public RestObject YoungerSelectDrug(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT) {
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return drugService.YoungerSelectDrug(oldsterId, (String) JWT);
    }

    //晚辈修改药品提醒
    //Drug drug, DrugNum drugNum, String YoungerId
    @PostMapping("/UpdateDrug")
    public RestObject YoungerUpdateDrug(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        Drug drug = JSONUtil.toBean(jsonObject.toString(), Drug.class,true);
        if (drug==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        drug.setSetTime(new Date(jsonObject.getObject("setTime",Integer.class)));
        drug.setDrugTime(new Time(jsonObject.getObject("drugTime",Integer.class)));

        return drugService.YoungerUpdateDrug(drug, (String) JWT);
    }

    //晚辈删除药品提醒
    //String takeId, String youngerId,String OldsterId
    @PostMapping("/DeleteDrug")
    public RestObject YoungerDeleteDrug(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        String takeId = jsonObject.getObject("takeId", String.class);
        String oldsterId = jsonObject.getObject("oldsterId", String.class);

        if (takeId==null||oldsterId==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return drugService.YoungerDeleteDrug(takeId, (String) JWT,oldsterId);

    }

    //晚辈修改药品数量
    //DrugNum drugNum, String youngerId
    @PostMapping("/UpdateDrugNum")
    public RestObject YoungerUpdateDrugNum(@RequestBody JSONObject jsonObject ,@RequestAttribute Object JWT) {
        DrugNum bean = JSONUtil.toBean(jsonObject.toString(), DrugNum.class, true);

        if (bean==null){
            return new RestObject("403","信息错误",jsonObject);
        }

        return drugService.YoungerUpdateDrugNum(bean, (String) JWT);
    }
}
