package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.*;
import com.ling.ap.Pojo.Entity.*;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HealthService;
import com.ling.ap.Service.ServiceInterface.ManagementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ManagementServiceImpl implements ManagementService {
    @Resource
    ManagementMapper managementMapper;
    @Resource
    YoungerMapper youngerMapper;
    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    CommunityUserMapper communityUserMapper;
    @Resource
    CommunityAdminMapper communityAdminMapper;
    @Resource
    HomeMapper homeMapper;
    @Resource
    CommunityMapper communityMapper;
    @Resource
    HealthService healthService;
    @Resource
    SosRecordMapper sosRecordMapper;

    //获取自身信息
    @Override
    public RestObject GetManagementData(String Id) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        return new RestObject<>("200","查询成功",map);
    }

    //查询长辈人员列表
    @Override
    public RestObject GetOldsterDataList(String Id) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<Oldster> List = oldsterMapper.SelectOldsterList();
        return new RestObject<>("200","查询成功",List);
    }

    //查询晚辈列表
    @Override
    public RestObject GetYoungerDataList(String Id) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<Younger> List = youngerMapper.SelectYoungerList();
        return new RestObject<>("200","查询成功",List);
    }

    //查询社区人员列表
    @Override
    public RestObject GetCommunityUserDataList(String Id) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<CommunityUser> List = communityUserMapper.SelectCommunityUserList();
        return new RestObject<>("200","查询成功",List);
    }

    //查询社区管理人员列表
    @Override
    public RestObject GetCommunityAdminDataList(String Id) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<CommunityAdmin> List = communityAdminMapper.SelectCommunityAdminList();
        return new RestObject<>("200","查询成功",List);
    }

    //查询家庭列表
    @Override
    public RestObject GetHomeDataList(String Id) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<Home> List = homeMapper.SelectHomeList();
        return new RestObject<>("200","查询成功",List);
    }

    //查询社区列表
    @Override
    public RestObject GetCommunityDataList(String Id) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<Community> List = communityMapper.SelectCommunityList();
        return new RestObject<>("200","查询成功",List);
    }

    //根据id查询长辈当天健康情况
    @Override
    public RestObject GetOldsterHealthData(String Id, String oldsterId) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        return healthService.GetHealthToday(oldsterId);
    }

    //根据id查询长辈10天内健康情况
    @Override
    public RestObject GetOldsterHealthDataDays(String Id, String oldsterId) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        return healthService.GetHealthTenDays(oldsterId);
    }

    //查询求救所有记录
    @Override
    public RestObject SelectRecordList(String Id) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<SosRecord> List = sosRecordMapper.SelectRecordList();
        return new RestObject<>("200","查询成功",List);
    }

    //根据时间信息查询信息
    @Override
    public RestObject SelectRecordListByTime(String Id, String Time) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<SosRecord> List = sosRecordMapper.SelectRecordListByTime(Time);
        return new RestObject<>("200","查询成功",List);
    }

    //根据长辈id查询信息
    @Override
    public RestObject SelectRecordListByOldsterId(String Id, String oldsterId) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        List<SosRecord> List = sosRecordMapper.SelectRecordListByOldsterId(oldsterId);
        return new RestObject<>("200","查询成功",List);
    }

    //根据信息id查询信息
    @Override
    public RestObject SelectRecordById(String Id, String RecordId) {
        Map<String, Object> map = managementMapper.GetManagementById(Id);
        if (map.isEmpty()){
            return new RestObject<>("403","查询失败",map);
        }
        SosRecord sosRecord = sosRecordMapper.SelectRecordById(RecordId);
        return new RestObject<>("200","查询成功",sosRecord);
    }
}
