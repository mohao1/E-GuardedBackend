package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;

public interface HealthScoreService {

    //晚辈查询长辈健康评分
    RestObject SelectHealthScore(String Younger , String OldsterId);

    //更新健康评分(定时操作)
    void UpdateHealthScore();
}
