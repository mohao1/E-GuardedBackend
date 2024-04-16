package com.ling.ap.Service.ServiceImpl;

import cn.hutool.core.util.IdUtil;
import com.ling.ap.Dao.*;
import com.ling.ap.Pojo.Entity.*;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.CommunityService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    CommunityMapper communityMapper;

    @Resource
    ApprovalListMapper approvalListMapper;

    @Resource
    CommunityUserMapper communityUserMapper;

    @Resource
    CommunityAdminMapper communityAdminMapper;

    //事务生成
    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    //事务对象
    @Resource
    TransactionDefinition transactionDefinition;

    //长辈绑定社区
    @Override
    public synchronized RestObject OldsterBindCommunity(String CommunityId, String OldsterId) {

        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Community community = communityMapper.SelectCommunity(CommunityId);
            if (community==null){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("404","没有这个社区",null);
            }
            Oldster oldster = oldsterMapper.GetOldster(OldsterId);
            if (oldster.getCommunityId().equals(CommunityId)){
                dataSourceTransactionManager.rollback(transaction);
                return new  RestObject("200","社区绑定成功",CommunityId);
            }

            int i = communityMapper.SelectCommunityOldsterNum(CommunityId);
            i++;
            communityMapper.UpdateCommunityOldster(i, CommunityId);
            oldsterMapper.OldsterBindCommunity(CommunityId,OldsterId);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","社区绑定成功",CommunityId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            e.printStackTrace();
            return new RestObject("404","社区绑定失败",e);
        }
    }

    //长辈退出绑定社区
    @Override
    public synchronized RestObject OldsterUnBindCommunity(String CommunityId, String OldsterId) {
        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Community community = communityMapper.SelectCommunity(CommunityId);
            if (community==null){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("404","没有这个社区",null);
            }

            Oldster oldster = oldsterMapper.GetOldster(OldsterId);
            if (oldster.getCommunityId().equals("")){
                dataSourceTransactionManager.rollback(transaction);
                return new RestObject("200","社区解除绑定成功",CommunityId);
            }

            int i = communityMapper.SelectCommunityOldsterNum(CommunityId);
            i--;
            communityMapper.UpdateCommunityOldster(i, CommunityId);

            oldsterMapper.OldsterBindCommunity("",OldsterId);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","社区解除绑定成功",CommunityId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("404","社区解除绑定失败",e);
        }
    }

    //社区人员申请绑定社区
    @Override
    public synchronized RestObject CommunityUserApplicationBindCommunity(String CommunityId, String CommunityUserId) {
        //查询申请记录
        ApprovalList approvalList = approvalListMapper.SelectByUserIdAndCommunityId(CommunityUserId, CommunityId);
        if (approvalList!=null){
            return new RestObject("403","已经存在这个社区申请数据",approvalList);
        }

        //添加申请记录
        String uuid = IdUtil.simpleUUID();
        ApprovalList approval = new ApprovalList(uuid, CommunityUserId, CommunityId);
        int i = approvalListMapper.InsertApprovalList(approval);
        if (i==0){
            return new RestObject("403","添加失败",i);
        }

        return new RestObject<>("200","添加成功",approval);

    }

    //社区人员退出绑定社区
    @Override
    public synchronized RestObject CommunityUserUnBindCommunity(String CommunityUserId,String CommunityId) {
        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Community community = communityMapper.SelectCommunity(CommunityId);
            if (community==null){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("404","没有这个社区",null);
            }

            CommunityUser communityUser = communityUserMapper.GetCommunityUser(CommunityUserId);
            if (communityUser.getCommunityId().equals("")){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("200","社区解除绑定成功",CommunityId);
            }

            int i = communityMapper.SelectCommunityUserNum(CommunityId);
            i--;
            communityMapper.UpdateCommunityYounger(i, CommunityId);
            communityUserMapper.CommunityUserBindCommunity("", CommunityUserId);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","社区解除绑定成功",CommunityId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("404","社区解除绑定失败",e);
        }
    }

    //社区管理人员查看申请人员列表
    @Override
    public RestObject CommunityAdminGetBindCommunity(String AdminId) {
        List<ApprovalList> list = approvalListMapper.AdminSelectApprovalList(AdminId);
        if (list.isEmpty()){
            return new RestObject<>("403","查询错误",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //社区管理人员通过绑定社区
    @Override
    public synchronized RestObject CommunityAdminBindCommunity(String AdminId, String CommunityUserId,String CommunityId ) {

        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            //进行权限认证
            CommunityAdmin admin = communityAdminMapper.SelectedCommunityAdminById(AdminId);
            String communityId = admin.getCommunityId();
            if (!communityId.equals(CommunityId)){
                return new RestObject("401","没有权限进行批准",AdminId);
            }
            int i1 = approvalListMapper.DeleteByCommunityIdAndUserId(CommunityId, CommunityUserId);
            int i2 = communityUserMapper.CommunityUserBindCommunity(CommunityId, CommunityUserId);
            if(i1==0||i2==0){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("403","批准任务不在","error");

            }

            //添加社区人员
            int i = communityMapper.SelectCommunityUserNum(CommunityId);
            i++;
            communityMapper.UpdateCommunityYounger(i,CommunityId);


            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","批准成功",CommunityId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("404","批准失败",e);
        }
    }

    //社区管理人员拒绝绑定社区
    @Override
    public synchronized RestObject CommunityAdminUnBindCommunity(String AdminId, String CommunityUserId,String CommunityId) {
        //进行权限认证
        CommunityAdmin admin = communityAdminMapper.SelectedCommunityAdminById(AdminId);
        String communityId = admin.getCommunityId();
        if (!communityId.equals(CommunityId)){
            return new RestObject("401","没有权限进行批准",AdminId);
        }

        int i = approvalListMapper.DeleteByCommunityIdAndUserId(CommunityId, CommunityUserId);
        if(i==0){
            return new RestObject("403","批准任务不在","error");
        }


        return new RestObject("200","审批拒绝成功",CommunityUserId);

    }

    //社区管理人员移除社区用户
    @Override
    public synchronized RestObject CommunityAdminDeleteBindCommunity(String AdminId, String CommunityUserId) {
        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //进行权限认证
            CommunityAdmin admin = communityAdminMapper.SelectedCommunityAdminById(AdminId);
            String communityId = admin.getCommunityId();
            CommunityUser communityUser = communityUserMapper.GetCommunityUser(CommunityUserId);
            String communityId1 = communityUser.getCommunityId();
            if (!communityId.equals(communityId1)){
                return new RestObject("401","没有权限进行删除",AdminId);
            }

            int i = communityMapper.SelectCommunityUserNum(communityId);
            i--;
            communityMapper.UpdateCommunityYounger(i,communityId);
            communityUserMapper.CommunityUserBindCommunity("", CommunityUserId);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","删除成功",CommunityUserId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("404","删除失败",e);
        }
    }

    //社区管理人员移除社区长辈账号
    @Override
    public synchronized RestObject CommunityAdminDeleteBindOldster(String AdminId, String OldsterId) {
        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //进行权限认证
            CommunityAdmin admin = communityAdminMapper.SelectedCommunityAdminById(AdminId);
            String communityId = admin.getCommunityId();
            Oldster oldster = oldsterMapper.GetOldster(OldsterId);
            String communityId1 = oldster.getCommunityId();
            if (!communityId.equals(communityId1)){
                return new RestObject("401","没有权限进行删除",AdminId);
            }

            int i = communityMapper.SelectCommunityOldsterNum(communityId);
            i--;
            communityMapper.UpdateCommunityOldster(i,communityId);
            oldsterMapper.OldsterBindCommunity("",OldsterId);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","删除成功",OldsterId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("404","删除失败",e);
        }
    }



}
