package com.ling.ap.Service.ServiceImpl;

import cn.hutool.core.util.IdUtil;
import com.ling.ap.Dao.ActivityRecordMapper;
import com.ling.ap.Pojo.Entity.ActivityRecord;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.ActivityRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 *     String recordId;
 *     String oldsterId;
 *     String activityName;
 *     String activityContent;
 *     String setTime;
 *     Date executionTime;
 *
 * */

@Service
public class ActivityRecordServiceImpl implements ActivityRecordService {

    @Resource
    ActivityRecordMapper activityRecordMapper;

    //设置活动记录
    @Override
    public synchronized void SetActivityRecord(String oldsterId,String activityName,String activityContent) {
        String uuid = IdUtil.simpleUUID();


        //获取当天时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate2=year+"-"+month+"-"+dates;


        ActivityRecord activityRecord = new ActivityRecord(uuid, oldsterId, activityName, activityContent, strDate2, date);

        activityRecordMapper.InsertActivityRecord(activityRecord);
    }

    //查询活动记录
    @Override
    public RestObject GetActivityRecordList(String oldsterId,String youngerId){
        try {
            List<ActivityRecord> list = activityRecordMapper.SelectActivityRecordList(oldsterId, youngerId);
            return new RestObject ("200","获取数据成功",list);
        }catch (Exception e){
            return new RestObject<>("403","查询失败",null);
        }
    }

    //每天重置长辈活动记录
    @Override
    public void TakeActivityRecord(){
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;
        //每天重置长辈活动记录
        activityRecordMapper.DeleteActivityRecord(strDate);
    }
}
