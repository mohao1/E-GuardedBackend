package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Pojo.Entity.Community;
import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CommunityOldsterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommunityOldsterServiceImpl implements CommunityOldsterService {

    @Resource
    OldsterMapper oldsterMapper;


    //长辈查询社区人员列表
    @Override
    public RestObject OldsterGetCommunityUserList(String OldsterId) {
        List<CommunityUser> list = oldsterMapper.SelectCommunityUserList(OldsterId);
        if (list.isEmpty()){
            return new RestObject<>("403","查询错误",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //长辈查询某个社区人员信息
    @Override
    public RestObject OldsterGetCommunityUser(String OldsterId, String CommunityUserId) {
        CommunityUser user = oldsterMapper.SelectCommunityUser(OldsterId, CommunityUserId);
        if (user==null){
            return new RestObject<>("403","查询错误",null);
        }
        return new RestObject<>("200","查询成功",user);
    }

    //长辈查询社区信息
    @Override
    public RestObject OldsterGetCommunity(String OldsterId) {
        Community community = oldsterMapper.SelectCommunity(OldsterId);
        if (community==null){
            return new RestObject<>("403","查询错误",null);
        }
        return new RestObject<>("200","查询成功",community);
    }
}
