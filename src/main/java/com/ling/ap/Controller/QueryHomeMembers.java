package com.ling.ap.Controller;

import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HomeMemberListService;
import com.ling.ap.Service.ServiceInterface.IntegralService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/HomeMembers")
public class QueryHomeMembers {

    @Resource
    HomeMemberListService homeMemberListService;

    @Resource
    IntegralService integralService;

    @Resource
    YoungerMapper youngerMapper;

    //长辈查询晚辈列表
    @GetMapping("/QueryYounger")
    public RestObject QueryYounger(@RequestAttribute Object JWT) {
        return homeMemberListService.QueryYounger((String) JWT);
    }

    //晚辈查询长辈列表
    @GetMapping("/QueryOldster")
    public RestObject QueryOldster(@RequestAttribute Object JWT) {
        return homeMemberListService.QueryOldster((String) JWT);
    }


    //长辈查询自己的家庭信息
    @GetMapping("/OldsterGetHome")
    public RestObject OldsterGetHome(@RequestAttribute Object JWT){
        return homeMemberListService.OldsterGetHome((String) JWT);
    }
    //晚辈查询自己的家庭信息
    @GetMapping("/YoungerGetHome")
    public RestObject YoungerGetHome(@RequestAttribute Object JWT){
        return homeMemberListService.YoungerGetHome((String) JWT);
    }

    //查询家庭积分列表积分
    @GetMapping ("/SelectIntegralById")
    public RestObject SelectIntegralById(@RequestAttribute Object JWT){
        Younger younger = youngerMapper.GetYounger((String) JWT);
        if (younger==null){
            return new RestObject<>("401","数据错误","error");
        }
        return integralService.SelectIntegralById(younger.getHomeId());
    }
}
