package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.IntegralMapper;
import com.ling.ap.Pojo.Entity.Integral;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.IntegralService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;

@Service
public class IntegralServiceImpl implements IntegralService {
    @Resource
    IntegralMapper integralMapper;

    //事务生成
    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    //事务对象
    @Resource
    TransactionDefinition transactionDefinition;

    //添加积分列表
    @Override
    public boolean InsertIntegral(Integral integer){
        int i = integralMapper.InsertIntegral(integer);
        if (i==0){
            return false;
        }
        return true;
    }
    //根据Id修改积分列表积分
    @Override
    public boolean UpdateIntegralById(Integral integer){
        int i = integralMapper.UpdateIntegralById(integer);
        if (i==0){
            return false;
        }
        return true;
    }
    //查询家庭积分列表积分
    @Override
    public RestObject SelectIntegralById(String id){
        Integral Integral = integralMapper.SelectIntegralById(id);
        if (Integral==null){
            return new RestObject<>("403","查询失败",null);
        }
        return new RestObject("200","查询成功",Integral);
    }

    //签到添加积分
    @Override
    public synchronized boolean SignIntegral(String homeId){
        //定义事务的类
        TransactionStatus transaction=null;
        //生成事务
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Integral integral = integralMapper.SelectIntegralById(homeId);
            int fraction = integral.getFraction();
            fraction++;
            integral.setFraction(fraction);
            int i = integralMapper.UpdateIntegralById(integral);
            if (i==0){
                dataSourceTransactionManager.rollback(transaction);
                return false;
            }
            dataSourceTransactionManager.commit(transaction);
            return true;
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transaction);
            return false;
        }
    }
}
