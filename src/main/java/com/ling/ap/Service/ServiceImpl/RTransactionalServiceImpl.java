package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.CommunityUserMapper;
import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Service.ServiceInterface.RTransactionalService;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.UserLoginMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.UserLogin;
import com.ling.ap.Pojo.Entity.Younger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class RTransactionalServiceImpl implements RTransactionalService {
    @Resource
    UserLoginMapper userLoginMapper;
    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    YoungerMapper youngerMapper;
    @Resource
    CommunityUserMapper communityUserMapper;

    //事务生成
    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    //事务对象
    @Resource
    TransactionDefinition transactionDefinition;

    //长辈注册写入
    @Override
    public synchronized int ORTransactional(Oldster oldster, UserLogin userLogin) {
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //写入userLogin数据
            //获取手机号是否的存在
            Map<String, String> p = userLoginMapper.PhoneGetInformation(userLogin.getPhone());
            if (p!=null){
                //失败返回
                dataSourceTransactionManager.rollback(transaction);
                return 0;
            }
            if (!userLogin.getEmail().equals("")){
                Map<String, String> e = userLoginMapper.EmailGetInformation(userLogin.getEmail());
                if (e!=null){
                    //失败返回
                    dataSourceTransactionManager.rollback(transaction);
                    return 0;
                }
            }
            userLoginMapper.SetUserLogin(userLogin);
            oldsterMapper.SetOldster(oldster);
            //成功返回
            dataSourceTransactionManager.commit(transaction);
            return 1;
        }catch (Exception e){
            //事务回滚
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
                e.printStackTrace();
            }
            //失败返回
            return 0;
        }
    }

    //晚辈注册写入
    @Transactional
    @Override
    public synchronized int YRTransactional(Younger younger, UserLogin userLogin) {
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //写入userLogin数据
            //获取手机号是否的存在
            Map<String, String> p = userLoginMapper.PhoneGetInformation(userLogin.getPhone());
            if (p!=null){
                //失败返回
                dataSourceTransactionManager.rollback(transaction);
                return 0;
            }

            if (!userLogin.getEmail().equals("")) {
                Map<String, String> e = userLoginMapper.EmailGetInformation(userLogin.getEmail());
                if (e != null) {
                    //失败返回
                    dataSourceTransactionManager.rollback(transaction);
                    return 0;
                }
            }
            userLoginMapper.SetUserLogin(userLogin);
            youngerMapper.SetYounger(younger);
            //成功返回
            dataSourceTransactionManager.commit(transaction);
            return 1;
        }catch (Exception e){
            //事务回滚
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            e.printStackTrace();
            //失败返回
            return 0;
        }
    }


    //社区人员注册事务
    @Override
    public synchronized int CRTransactional(CommunityUser communityUser, UserLogin userLogin) {
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Map<String, String> p = userLoginMapper.PhoneGetInformation(userLogin.getPhone());
            if (p!=null){
                //失败返回
                dataSourceTransactionManager.rollback(transaction);
                return 0;
            }
            if (!userLogin.getEmail().equals("")) {
                Map<String, String> e = userLoginMapper.EmailGetInformation(userLogin.getEmail());
                if (e != null) {
                    //失败返回
                    dataSourceTransactionManager.rollback(transaction);
                    return 0;
                }
            }
            userLoginMapper.SetUserLogin(userLogin);
            communityUserMapper.SetCommunityUser(communityUser);
            //成功返回
            dataSourceTransactionManager.commit(transaction);
            return 1;
        }catch (Exception e){
            //事务回滚
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            e.printStackTrace();
            //失败返回
            return 0;
        }
    }
}
