package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.*;
import com.ling.ap.Pojo.Entity.*;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HeadSculptureService;
import com.ling.ap.Service.ServiceInterface.UserDataService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDataServiceImpl implements UserDataService {
    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    YoungerMapper youngerMapper;
    @Resource
    CommunityUserMapper communityUserMapper;

    @Resource
    HeadSculptureService headSculptureService;

    @Resource
    CommunityAdminMapper communityAdminMapper;

    @Resource
    CounterFraudMapper counterFraudMapper;

    //事务生成
    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    //事务对象
    @Resource
    TransactionDefinition transactionDefinition;
    //长辈获取自身信息
    @Override
    public RestObject OldsterGetUserData(String oldsterId) {
        Oldster oldster = oldsterMapper.GetOldster(oldsterId);
        Object data = headSculptureService.SelectUserHeadSculpture(oldsterId).getData();
        Map<String,Object> map =new HashMap<>();
        map.put("oldster",oldster);
        map.put("headSculptureUrl",data);
        if (oldster==null){
            return new RestObject<>("401","用户不在","error");
        }
        return new RestObject<>("200","查询成功",map);
    }

    //晚辈获取自身信息
    @Override
    public RestObject YoungerGetUserData(String YoungerId) {
        Younger younger = youngerMapper.GetYounger(YoungerId);
        Object data = headSculptureService.SelectUserHeadSculpture(YoungerId).getData();
        Map<String,Object> map =new HashMap<>();
        map.put("younger",younger);
        map.put("headSculptureUrl",data);
        if (younger==null){
            return new RestObject<>("401","用户不在","error");
        }
        return new RestObject<>("200","查询成功",map);
    }

    //社区的管理员获取自身信息
    @Override
    public RestObject CommunityAdminGetUserData(String CommunityAdminId) {
        CommunityAdmin admin = communityAdminMapper.SelectedCommunityAdminById(CommunityAdminId);
        if (admin==null){
            return new RestObject<>("401","用户不在","error");
        }
        return new RestObject<>("200","查询成功",admin);
    }

    //社区工作人员获取自身信息
    @Override
    public RestObject CommunityUserGetUserData(String CommunityUserId) {
        CommunityUser communityUser = communityUserMapper.GetCommunityUser(CommunityUserId);

        Object data = headSculptureService.SelectUserHeadSculpture(CommunityUserId).getData();
        Map<String,Object> map =new HashMap<>();
        map.put("communityUser",communityUser);
        map.put("headSculptureUrl",data);

        if (communityUser==null){
            return new RestObject<>("401","用户不在","error");
        }
        return new RestObject<>("200","查询成功",map);
    }

    //修改长辈个人信息
    @Override
    public synchronized RestObject OldsterUpDataUserData(Oldster oldster) {
        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            int i = oldsterMapper.UpdateOldster(oldster);
            CounterFraud counterFraud = counterFraudMapper.SelectCounterFraudById(oldster.getUserId());
            if (counterFraud!=null){
                int i1 = counterFraudMapper.UpdateCounterFraudNameById(new CounterFraud(oldster.getUserId(), oldster.getName()));
                if (i1==0){
                    if (transaction!=null){
                        dataSourceTransactionManager.rollback(transaction);
                    }
                    return new RestObject<>("403","修改失败",oldster.getUserId());
                }
            }
            if (i==0){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject<>("403","修改失败",oldster.getUserId());
            }
            dataSourceTransactionManager.commit(transaction);
            return new RestObject<>("200","修改成功",oldster.getUserId());
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject<>("403","修改失败",oldster.getUserId());
        }
    }

    //修改晚辈个人信息
    @Override
    public synchronized RestObject YoungerUpDataUserData(Younger younger) {
        int i = youngerMapper.UpdateYounger(younger);
        if (i==0){
            return new RestObject<>("403","修改失败",younger.getUserId());
        }
        return new RestObject<>("200","修改成功",younger.getUserId());
    }

    //修改社区人员个人信息
    @Override
    public synchronized RestObject CommunityUserUpDataUserData(CommunityUser communityUser) {
        int i = communityUserMapper.UpdateYounger(communityUser);

        if (i==0){
            return new RestObject<>("403","修改失败",communityUser.getUserId());
        }
        return new RestObject<>("200","修改成功",communityUser.getUserId());
    }

}
