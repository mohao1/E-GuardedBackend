package com.ling.ap.Service.ServiceImpl;

import cn.hutool.core.util.IdUtil;
import com.ling.ap.Dao.HealthMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Health;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.ActivityRecordService;
import com.ling.ap.Service.ServiceInterface.HealthService;
import com.ling.ap.Service.ServiceInterface.IntegralService;
import com.ling.ap.Utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HealthServiceImpl implements HealthService {

    @Resource
    HealthMapper healthMapper;

    @Resource
    RedisUtil redisUtil;

    @Resource
    OldsterMapper oldsterMapper;

    @Resource
    YoungerMapper youngerMapper;

    @Resource
    IntegralService integralService;

    @Resource
    ActivityRecordService activityRecordService;


    //长辈当天健康数据写入缓存
    @Override
    public synchronized RestObject SetHealthCache(Health health) {
        if (health==null){
            return new RestObject("403","Data is empty","数据为空");
        }

        //写入缓存
        redisUtil.set(health.getUserId(),health);

        return new RestObject("200","写入成功",health.getUserId());
    }

    //根据时间删除长辈数据（每日清除）
    @Override
    public synchronized int DeleteHealth(String Time) {
        return healthMapper.DeleteHealthByTime(Time);
    }

    //根据长辈Id加上任务Id删除数据
    @Override
    public RestObject DeleteHealthById(String UserId, String TakeId) {
        int i = healthMapper.DeleteHealthById(UserId, TakeId);

        if (i==0){
            return new RestObject("403","Delete failed","删除失败");
        }

        return new RestObject("403","Delete succeeded","删除成功");
    }

    //查询长辈当天健康数据
    @Override
    public RestObject GetHealthToday(String UserId) {
        //获取当天时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate2=year+"-"+month+"-"+dates;

        //获取数据时间
        Health health = (Health) redisUtil.get(UserId);
        if (health==null){
            Health health1 = new Health("", UserId, strDate2, 0, "0", "0","0","0",0);
            this.SetHealthCache(health1);
            return new RestObject("200","获取成功",health1);
        }
        String strDate1=health.getTime();


        //进行时间判断操作
        if (!strDate1.equals(strDate2)){
            Health health1 = new Health("", UserId, strDate2, 0, "0", "0","0","0",0);
            this.SetHealthCache(health1);
            return new RestObject("200","获取成功",health1);
        }

        return new RestObject("200","获取成功",health);
    }

    //查询长辈10天内健康数据
    @Override
    public RestObject GetHealthTenDays(String UserId) {
        List<Health> health = healthMapper.SelectHealthById(UserId);

        if (health.isEmpty()){
            return new RestObject ("200","没有数据存在",health);
        }

        return new RestObject ("200","获取数据成功",health);
    }

    //插入健康数据（数据库中数据）
    @Override
    public synchronized int SetHealthData(Health health) {
        //生成一个uuid
        String uuid = IdUtil.simpleUUID();
        health.setHealthId(uuid);
        //写入数据
        return healthMapper.InsertHealth(health);

    }

    //数据录入删除
    @Override
    public void DataEntryDeletion() {
        //获取10天前的时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -11);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;
        //删除旧的数据
        DeleteHealth(strDate);


        //获取数据当天时间
        Date date2=new Date();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(date2);
        int year2 = calendar2.get(Calendar.YEAR);//获取年份
        int month2 = calendar2.get(Calendar.MONTH)+1;//获取月份
        int dates2 = calendar2.get(Calendar.DATE);//获取日
        String strDate2=year2+"-"+month2+"-"+dates2;

        //获取老人列表
        List<String> list = oldsterMapper.SelectOldsterIdList();
        //写入数据
        for (String id : list) {
            //获取Id
            //获取缓存中的对应数据
            Health health = (Health) redisUtil.get(id);
            //生成一个uuid
            String uuid = IdUtil.simpleUUID();
            if (health == null) {
                Health health1 = new Health(uuid, id, strDate2, 0, "0", "0","0","0",0);
                healthMapper.InsertHealth(health1);
                continue;
            }
            if (!health.getTime().equals(strDate2)) {
                health.setHealthId(uuid);
                Health health1 = new Health(uuid, id, strDate2, 0, "0", "0","0","0",0);
                healthMapper.InsertHealth(health1);
                continue;
            }

            health.setHealthId(uuid);
            healthMapper.InsertHealth(health);
        }
    }


    //晚辈查询长辈10天内健康数据
    @Override
    public RestObject YoungerGetHealthTenDays(String userId, String Oldster) {
        List<Health> health = healthMapper.SelectYoungerHealthById(userId,Oldster);

        if (health.isEmpty()){
            return new RestObject ("200","没有数据存在",health);
        }

        return new RestObject ("200","获取数据成功",health);
    }




    //晚辈查询长辈当天健康数据
    @Override
    public RestObject YoungerGetHealthToday(String UserId, String OldsterId) {
        //获取晚辈信息
        Younger younger = youngerMapper.GetYounger(UserId);
        if (younger==null){
            return new RestObject("401","No permission to query","没有权限查询");
        }
        //获取长辈信息
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        if (oldster==null){
            return new RestObject("401","No permission to query","没有权限查询");
        }
        //判断晚辈是否绑定长辈
        if (!younger.getHomeId().equals(oldster.getHomeId())){
            return new RestObject("401","No permission to query","没有权限查询");
        }
        //查询数据
        return GetHealthToday(OldsterId);
    }


    //长辈签到
    @Override
    public RestObject OldsterSign(String UserId) {
        RestObject restObject = this.GetHealthToday(UserId);
        Health health = (Health) restObject.getData();
        if (health.getSign()==1){
            return new RestObject<>("200","签到成功","签到成功");
        }
        Oldster oldster = oldsterMapper.GetOldster(UserId);
        boolean b = integralService.SignIntegral(oldster.getHomeId());
        if (b){
            health.setSign(1);
            redisUtil.set(UserId,health);
            activityRecordService.SetActivityRecord(UserId,"签到","签到成功");
            return new RestObject<>("200","签到成功","签到成功");
        }
        return new RestObject<>("403","签到失败",UserId);
    }




}
