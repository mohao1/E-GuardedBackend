package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.CommunityManageMapper;
import com.ling.ap.Dao.CommunityUserMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Pojo.Entity.Basic_Diseases;
import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Health;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CommunityUserService;
import com.ling.ap.Service.ServiceInterface.HealthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CommunityUserServiceImpl implements CommunityUserService {
    @Resource
    CommunityManageMapper communityManageMapper;

    @Resource
    CommunityUserMapper communityUserMapper;

    @Resource
    HealthService healthService;

    @Resource
    OldsterMapper oldsterMapper;

    //查询管理长辈信息列表
    @Override
    public RestObject GetOldsterList(String CommunityUserId) {
        List<Oldster> list = communityManageMapper.GetOldsterList(CommunityUserId);
        if (list.isEmpty()){
            return new RestObject<>("403","查询错误",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //自己所在社区信息
    @Override
    public RestObject GetCommunity(String CommunityUserId) {
        Map<String, String> map = communityUserMapper.GetCommunity(CommunityUserId);
        if (map.isEmpty()){
            return new RestObject<>("403","查询错误",map);
        }
        return new RestObject<>("200","查询成功",map);
    }

    //查询管理社区某个长辈身体状况十天
    @Override
    public RestObject GetOldsterHealth(String CommunityUserId, String OldsterId) {
        List<Health> list = communityManageMapper.GetOldsterHealth(CommunityUserId,OldsterId);
        if (list.isEmpty()){
            return new RestObject<>("403","查询错误",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //查询管理社区某个长辈身体基础疾病列表
    @Override
    public RestObject GetOldsterBasicDiseases(String CommunityUserId, String OldsterId) {
        List<Basic_Diseases> list = communityManageMapper.GetOldsterBasicDiseases(CommunityUserId,OldsterId);
        if (list.isEmpty()){
            return new RestObject<>("403","查询错误",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //查询长辈当天健康数据
    public RestObject GetHealthToday(String CommunityUserId, String OldsterId){
        CommunityUser communityUser = communityUserMapper.GetCommunityUser(CommunityUserId);
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        if (oldster==null||communityUser==null){
            return new RestObject<>("403","用户不在",null);
        }
        if (!oldster.getCommunityId().equals(communityUser.getCommunityId())){
            return new RestObject<>("403","用户不在",null);
        }

        return healthService.GetHealthToday(OldsterId);
    }
}
