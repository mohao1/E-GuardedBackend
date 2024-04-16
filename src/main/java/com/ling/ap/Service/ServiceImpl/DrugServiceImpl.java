package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.DrugMapper;
import com.ling.ap.Dao.DrugNumMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Drug;
import com.ling.ap.Pojo.Entity.DrugNum;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.ActivityRecordService;
import com.ling.ap.Service.ServiceInterface.DrugService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DrugServiceImpl implements DrugService {
    @Resource
    DrugMapper drugMapper;
    @Resource
    DrugNumMapper drugNumMapper;
    @Resource
    YoungerMapper youngerMapper;
    @Resource
    OldsterMapper oldsterMapper;
    //事务生成
    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    //事务对象
    @Resource
    TransactionDefinition transactionDefinition;

    @Resource
    ActivityRecordService activityRecordService;


    //长辈设置药品提醒
    @Override
    public synchronized RestObject OldsterInsertDrug(Drug drug, DrugNum drugNum) {
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //获取当天时间
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);//获取年份
            int month = calendar.get(Calendar.MONTH)+1;//获取月份
            int dates = calendar.get(Calendar.DATE);//获取日
            String strDate=year+"-"+month+"-"+dates;
            //写入当前日期
            drug.setSelectTime(strDate);

            drugMapper.InsertDrug(drug);
            DrugNum numById = drugNumMapper.SelectDrugNumByOldsterIdAndName(drug.getUserId(),drugNum.getName());
            if (numById==null){
                drugNumMapper.InsertDrugNum(drugNum);
            }
            dataSourceTransactionManager.commit(transaction);
            return new RestObject<>("200","插入成功",1);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            e.printStackTrace();
            return new RestObject<>("403","插入失败",e);
        }
    }

    //长辈确认药品提醒
    @Override
    public synchronized RestObject OldsterConfirmDrug(String oldsterId, String takeId, int DeleteNum,String drugId) {
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            int i = drugNumMapper.SelectDrugNum(drugId);
            drugMapper.ConfirmDrugs(oldsterId,takeId);
            DrugNum drugNum = new DrugNum();
            drugNum.setDrugId(drugId);
            drugNum.setUserId(oldsterId);
            drugNum.setNum(i-DeleteNum);
            drugNumMapper.UpdateDrugNum(drugNum);

            dataSourceTransactionManager.commit(transaction);
            DrugNum drug = drugNumMapper.SelectDrugNumById(drugId);
            String drugStr="药品名称:"+drug.getName()+",服用:"+DeleteNum+",剩余:"+drug.getNum();
            activityRecordService.SetActivityRecord(oldsterId,"吃药",drugStr);
            return new RestObject<>("200","确认成功",takeId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject<>("200","确认失败",e);
        }
    }

    //长辈查询药品提醒
    @Override
    public RestObject OldsterSelectDrug(String oldsterId) {
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
        //获取当天时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        List<String> dataTime = drugMapper.GetSelectTime(oldsterId);
            for (String str : dataTime) {
                if (!dataTime.isEmpty() && !str.equals(strDate) ){
                    drugMapper.SetSelectTime(oldsterId,strDate);
                }
            }
        List<Map<String, Object>> maps = drugMapper.OldsterSelectDrug(oldsterId);
        dataSourceTransactionManager.commit(transaction);
        return new RestObject("200","查询成功",maps);

        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            e.printStackTrace();
            return new RestObject<>("403","查询失败",e);
        }
    }

    //长辈修改药品提醒
    @Override
    public RestObject OldsterUpdateDrug(Drug drug) {
        int i = drugMapper.UpdateDrug(drug);
        if (i==0){
            return new RestObject<>("403","修改失败","error");
        }
        return new RestObject<>("200","修改成功",i);
    }

    //长辈删除药品提醒
    @Override
    public RestObject OldsterDeleteDrug(String takeId,String oldsterId) {
        int i = drugMapper.DeleteDrug(takeId,oldsterId);
        if (i==0){
            return new RestObject<>("403","删除失败","error");
        }
        return new RestObject<>("200","删除成功",i);
    }

    //长辈修改药品数量
    @Override
    public RestObject OldsterUpdateDrugNum(DrugNum drugNum) {
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            int i = drugNumMapper.UpdateDrugNum(drugNum);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","修改成功",i);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject<>("403","修改失败",e);
        }
    }

    //晚辈设置药品提醒
    @Override
    public RestObject YoungerInsertDrug(Drug drug, DrugNum drugNum, String YoungerId) {
        Younger younger = youngerMapper.GetYounger(YoungerId);
        Oldster oldster = oldsterMapper.GetOldster(drug.getUserId());
        if (!oldster.getHomeId().equals(younger.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //获取当天时间
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);//获取年份
            int month = calendar.get(Calendar.MONTH)+1;//获取月份
            int dates = calendar.get(Calendar.DATE);//获取日
            String strDate=year+"-"+month+"-"+dates;
            //写入当前日期
            drug.setSelectTime(strDate);

            drugMapper.InsertDrug(drug);
            DrugNum numById = drugNumMapper.SelectDrugNumByOldsterIdAndName(drug.getUserId(),drugNum.getName());
            if (numById==null){
                drugNumMapper.InsertDrugNum(drugNum);
            }
            dataSourceTransactionManager.commit(transaction);
            return new RestObject<>("200","插入成功",1);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            e.printStackTrace();
            return new RestObject<>("403","插入失败",e);
        }
    }

    //晚辈查询药品提醒
    @Override
    public RestObject YoungerSelectDrug(String oldsterId, String youngerId) {
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //获取当天时间
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);//获取年份
            int month = calendar.get(Calendar.MONTH)+1;//获取月份
            int dates = calendar.get(Calendar.DATE);//获取日
            String strDate=year+"-"+month+"-"+dates;

            List<String> dataTime = drugMapper.GetSelectTime(oldsterId);
            for (String str : dataTime) {
                if (!dataTime.isEmpty() && !str.equals(strDate) ){
                    drugMapper.SetSelectTime(oldsterId,strDate);
                }
            }

            List<Map<String, Object>> maps = drugMapper.YoungerSelectDrug(oldsterId,youngerId);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","查询成功",maps);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject<>("403","查询失败",e);
        }
    }

    //晚辈修改药品提醒
    @Override
    public RestObject YoungerUpdateDrug(Drug drug, String YoungerId) {

        Younger younger = youngerMapper.GetYounger(YoungerId);
        Oldster oldster = oldsterMapper.GetOldster(drug.getUserId());
        if (!oldster.getHomeId().equals(younger.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }

        int i = drugMapper.UpdateDrug(drug);
        if (i==0){
            return new RestObject<>("403","修改失败","error");
        }

        return new RestObject<>("200","修改成功",i);
    }

    //晚辈删除药品提醒
    @Override
    public RestObject YoungerDeleteDrug(String takeId, String youngerId,String OldsterId) {
        Younger younger = youngerMapper.GetYounger(youngerId);
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        if (!oldster.getHomeId().equals(younger.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }

        int i = drugMapper.DeleteDrug(takeId,OldsterId);
        if (i==0){
            return new RestObject<>("403","删除失败","error");
        }
        return new RestObject<>("200","删除成功",i);
    }

    //晚辈修改药品数量
    @Override
    public RestObject YoungerUpdateDrugNum(DrugNum drugNum, String youngerId) {

        Younger younger = youngerMapper.GetYounger(youngerId);
        Oldster oldster = oldsterMapper.GetOldster(drugNum.getUserId());
        if (!oldster.getHomeId().equals(younger.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {

            int i = drugNumMapper.UpdateDrugNum(drugNum);
            if (i==0){
                return new RestObject<>("403","修改失败",i);
            }
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","修改成功",i);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject<>("403","修改失败",e);
        }
    }
}
