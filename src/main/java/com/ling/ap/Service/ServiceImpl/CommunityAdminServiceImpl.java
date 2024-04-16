package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.CommunityAdminMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Pojo.Entity.*;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CommunityAdminService;
import com.ling.ap.Service.ServiceInterface.HealthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommunityAdminServiceImpl implements CommunityAdminService {
    @Resource
    CommunityAdminMapper communityAdminMapper;

    @Resource
    HealthService healthService;

    @Resource
    OldsterMapper oldsterMapper;

    //查询管理社区中的老人信息列表
    @Override
    public RestObject SelectedOldsterList(String AdminId) {
        List<Oldster> list = communityAdminMapper.SelectedOldsterList(AdminId);
        if (list.isEmpty()){
            return new RestObject("403","查询错误",list);
        }
        return new RestObject ("200","查询成功",list);
    }

    //查询管理社区中的社区人员信息列表
    @Override
    public RestObject SelectedCommunityUserList(String AdminId) {
        List<CommunityUser> list = communityAdminMapper.SelectedCommunityUserList(AdminId);
        if (list.isEmpty()){
            return new RestObject("403","查询错误",list);
        }
        return new RestObject ("200","查询成功",list);
    }


    //社区管理人员查询社区信息
    @Override
    public RestObject SelectedCommunity(String AdminId) {
        Community community = communityAdminMapper.SelectedCommunity(AdminId);
        if (community==null){
            return new RestObject("403","查询错误",null);
        }
        return new RestObject ("200","查询成功",community);
    }


    //社区管理人员查询社区某个长辈健康信息
    @Override
    public RestObject SelectedOldsterHealth(String AdminId, String OldsterId) {

        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        CommunityAdmin communityAdmin = communityAdminMapper.SelectedCommunityAdminById(AdminId);
        if (oldster==null||communityAdmin==null){
            return new RestObject<>("403","用户不在",null);
        }
        if (!oldster.getCommunityId().equals(communityAdmin.getCommunityId())){
            return new RestObject<>("401","没有权限查询",null);
        }
        RestObject restObject = healthService.GetHealthTenDays(OldsterId);
        List<Health> data = (List<Health>) restObject.getData();
        RestObject today = healthService.GetHealthToday(OldsterId);
        Health todayData = (Health) today.getData();
        data.add(todayData);
        restObject.setData(data);
        return restObject;
    }

}
