package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HomeMemberListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class HomeMemberListServiceImpl implements HomeMemberListService {

    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    YoungerMapper youngerMapper;


    //长辈查询晚辈列表
    @Override
    public RestObject QueryYounger(String UserId) {
        //确认用户是否存在
        Oldster oldster = oldsterMapper.GetOldster(UserId);
        if (oldster==null){
            return new RestObject("401","The user's account does not exist","用户的账号不存在");
        }
        if (oldster.getHomeId().equals("")){
            return new RestObject("401","The user did not join the Home","用户没有加入家庭");
        }

        //查询晚辈列表
        ArrayList<Younger> youngers = oldsterMapper.GetYoungerList(UserId);

        return new RestObject("200","查询成功",youngers);
    }

    //晚辈查询长辈列表
    @Override
    public RestObject QueryOldster(String UserId) {
        //确认用户是否存在
        Younger younger = youngerMapper.GetYounger(UserId);
        if (younger==null){
            return new RestObject("401","The user's account does not exist","用户的账号不存在");
        }
        if (younger.getHomeId().equals("")){
            return new RestObject("401","The user did not join the Home","用户没有加入家庭");
        }

        ArrayList<Oldster> oldsters = youngerMapper.GetOldsterList(UserId);

        return new RestObject("200","查询成功",oldsters);
    }

    //查询家庭全部人员
    @Override
    public RestObject QueryAll(String UserId) {
        return null;
    }

    //查询年轻人是否绑定了老人
    @Override
    public boolean QueryYoungerBindOldster(String youngerId, String OldsterId) {
        String oldsterId = youngerMapper.SelectYoungerBindOldster(youngerId, OldsterId);
        if (oldsterId==null){
            return false;
        }
        return true;
    }

    //长辈查询自己的家庭信息
    @Override
    public RestObject OldsterGetHome(String OldsterId){
        //确认用户是否存在
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        if (oldster==null){
            return new RestObject("401","The user's account does not exist","用户的账号不存在");
        }
        if (oldster.getHomeId().equals("")){
            return new RestObject("401","The user did not join the Home","用户没有加入家庭");
        }

        Map<String, String> map = oldsterMapper.GetHome(OldsterId);

        return new RestObject("200","查询成功",map);
    }
    //晚辈查询自己的家庭信息
    @Override
    public RestObject YoungerGetHome(String YoungerId){
        //确认用户是否存在
        Younger younger = youngerMapper.GetYounger(YoungerId);
        if (younger==null){
            return new RestObject("401","The user's account does not exist","用户的账号不存在");
        }
        if (younger.getHomeId().equals("")){
            return new RestObject("401","The user did not join the Home","用户没有加入家庭");
        }

        Map<String, String> map = youngerMapper.GetHome(YoungerId);

        return new RestObject("200","查询成功",map);
    }
}
