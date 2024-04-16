package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.HealthScoreMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Pojo.Entity.Health;
import com.ling.ap.Pojo.Entity.HealthScore;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HealthScoreService;
import com.ling.ap.Utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HealthScoreServiceImpl implements HealthScoreService {


    @Resource
    HealthScoreMapper healthScoreMapper;

    @Resource
    OldsterMapper oldsterMapper;

    @Resource
    RedisUtil redisUtil;


    //晚辈查询长辈健康评分
    @Override
    public RestObject SelectHealthScore(String Younger, String OldsterId) {
        HealthScore healthScore = healthScoreMapper.SelectHealthScoreByYounger(OldsterId, Younger);
        if (healthScore!=null){
            return new RestObject<>("200","健康评分获取成功",healthScore);
        }

        //获取当天时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate2=year+"-"+month+"-"+dates;

        //初始化长辈的健康数据
        HealthScore NewHealthScore = new HealthScore(OldsterId, 60, strDate2);
        healthScoreMapper.InsertHealthScore(NewHealthScore);
        return new RestObject<>("200","健康评分获取成功",NewHealthScore);

    }

    //更新健康评分(定时操作)
    @Override
    public void UpdateHealthScore() {

        //获取当天时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate2=year+"-"+month+"-"+dates;

        List<String> list = oldsterMapper.SelectOldsterIdList();

        for (String id : list) {
            //获取Id
            //获取缓存中的对应数据
            Health health = (Health) redisUtil.get(id);
            HealthScore healthScore = healthScoreMapper.SelectHealthScoreById(id);
            if (healthScore==null){
                HealthScore healthScore1 = new HealthScore(id, 60, strDate2);
                if (health != null && health.getTime().equals(strDate2)) {
                    int score = healthScore1.getScore();
                    if (health.getSign() == 0) {
                        score = score - 2;
                    } else {
                        score++;
                    }
                    int low = Integer.parseInt(health.getBloodLow());
                    int pressure = Integer.parseInt(health.getBloodPressure());
                    if (low > 90 || low < 60) {
                        score = score - 3;
                    } else {
                        score = score + 1;
                    }
                    if (pressure > 120 || pressure < 90) {
                        score = score - 3;
                    } else {
                        score = score + 1;
                    }
                    healthScore1.setScore(score);
                    System.out.println(healthScore1);
                }
                healthScoreMapper.InsertHealthScore(healthScore1);
            }else {
                if (health==null||!health.getTime().equals(strDate2)){
                    int score = healthScore.getScore();
                    healthScore.setScore(score-10);
                    healthScoreMapper.UpdateHealthScore(healthScore);
                    continue;
                }
                int score = healthScore.getScore();
                if (health.getSign()==0){
                    score=score-2;
                }else {
                    score++;
                }
                int low =Integer.parseInt(health.getBloodLow());
                int pressure= Integer.parseInt(health.getBloodPressure());
                if (low>90||low<60){
                    score =score-3;
                }else {
                    score =score+1;
                }
                if (pressure>120||pressure<90){
                    score =score-3;
                }else {
                    score =score+1;
                }
                healthScore.setScore(score);
                healthScore.setDate(strDate2);
                healthScoreMapper.UpdateHealthScore(healthScore);
            }
        }

    }
}
