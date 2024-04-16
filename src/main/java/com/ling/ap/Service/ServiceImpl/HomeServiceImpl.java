package com.ling.ap.Service.ServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.ling.ap.Dao.HomeMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Home;
import com.ling.ap.Pojo.Entity.Integral;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HomeService;
import com.ling.ap.Service.ServiceInterface.IntegralService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    //操作家庭
    @Resource
    HomeMapper homeMapper;
    //操作长辈
    @Resource
    OldsterMapper oldsterMapper;
    //操作晚辈
    @Resource
    YoungerMapper youngerMapper;

    @Resource
    IntegralService integralService;

    //事务生成
    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    //事务对象
    @Resource
    TransactionDefinition transactionDefinition;
    //长辈绑定家庭
    @Override
    public synchronized RestObject OldsterBindHome(JSONObject jsonObject, String UserId) {

        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //判断长辈是否绑定家庭
            Map<String, String> map = oldsterMapper.GetHome(UserId);
            if (map!=null){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("200","Existing Home","存在家庭");
            }
            //判断家庭是否存在
            String homeId = jsonObject.getObject("HomeId", String.class);
            Home home = homeMapper.SelectHome(homeId);
            if (home==null){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("200","The current family does not exist","当前的家庭不存在");
            }

            //锁定数量
            int num = homeMapper.SelectNum(homeId);
            num++;//修改数量
            oldsterMapper.OldsterBindHome(homeId,UserId);
            //修改表的数量
            homeMapper.UpdateNum(num,homeId);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject<>("200","Modified successfully","修改成功");
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("200","Existing Home","存在家庭");
        }
    }

    //晚辈绑定家庭
    @Override
    public synchronized RestObject YoungerBindHome(JSONObject jsonObject, String UserId) {
        //定义事务的类
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //判断晚辈是否绑定家庭
            Map<String, String> map = youngerMapper.GetHome(UserId);
            if (map!=null){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("200","Existing Home","存在家庭");
            }
            //判断家庭是否存在
            String homeId = jsonObject.getObject("HomeId", String.class);
            Home home = homeMapper.SelectHome(homeId);
            if (home==null){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("200","The current family does not exist","当前的家庭不存在");
            }

            //锁定数量
            int num = homeMapper.SelectNum(homeId);
            num++;//修改数量
            int priority = jsonObject.getObject("priority", int.class);
            youngerMapper.YoungerBindHome(homeId,UserId,priority);
            //修改表的数量
            homeMapper.UpdateNum(num,homeId);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject<>("200","Modified successfully","修改成功");
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("200","Existing Home","存在家庭");
        }
    }

    //创建家庭(管理)
    @Override
    public synchronized RestObject CreateHome(Home home) {

        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            String admin = home.getAdmin();
            Younger younger = youngerMapper.GetYounger(admin);
            if (!younger.getHomeId().equals("")){
                return new RestObject("401","error","创建失败用户存在家庭绑定");
            }
            //创建一个家庭
            int i = homeMapper.CreateHome(home);
            if (i==0){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("401","error","创建失败");
            }
            boolean b = integralService.InsertIntegral(new Integral(home.getHomeId(), 0));
            if (!b){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("401","error","创建失败");
            }
            //创建者绑定到家庭之中
            youngerMapper.YoungerBindHome(home.getHomeId(), admin,2);
            dataSourceTransactionManager.commit(transaction);
            return new RestObject<>("200","创建成功",home.getHomeId());
        }catch (Exception e){
            e.printStackTrace();
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("401","error","创建失败");
        }
    }

    //删除家庭(管理)
    @Override
    public synchronized RestObject DeleteHome(JSONObject jsonObject, String UserId) {
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            //查询Home是否存在
            String homeId = jsonObject.getObject("homeId", String.class);
            Home home = homeMapper.SelectHome(homeId);
            if (home==null){
                return new RestObject("401","error","删除失败,家庭不存在");
            }
            //清除绑定家庭用户的家庭的家庭Id
            homeMapper.DeleteYoungerBindHome(homeId,"");
            homeMapper.DeleteOldsterBindHome(homeId,"");
            //删除家庭
            int i = homeMapper.DeleteHome(homeId, UserId);
            //判断是否删除成功
            if (i==0){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("401","error","删除失败");
            }
            //提交数据
            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","删除成功",homeId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("401","error","删除失败");
        }

    }

    //长辈退出家庭
    @Override
    public synchronized RestObject OldsterUnBindHome(String UserId) {
        //开启事务
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            //判断长辈是否绑定家庭
            Oldster oldster = oldsterMapper.GetOldster(UserId);
            String homeId = oldster.getHomeId();
            if (homeId.equals("")){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("401","error","长辈没有绑定家庭");
            }
            //锁定HomeNum的数量
            int num = homeMapper.SelectNum(homeId);
            num--;
            //修改HomeNum的大小
            homeMapper.UpdateNum(num,homeId);
            //长辈删除绑定家庭
            oldsterMapper.OldsterBindHome("",UserId);

            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","删除成功",homeId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("401","error","删除失败");
        }
    }

    //晚辈退出家庭
    @Override
    public synchronized RestObject YoungerUnBindHome(String UserId) {
        //开启事务
        TransactionStatus transaction=null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            //判断晚辈是否绑定家庭
            Younger younger = youngerMapper.GetYounger(UserId);
            String homeId = younger.getHomeId();
            if (homeId.equals("")){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("401","error","晚辈没有绑定家庭");
            }
            //锁定HomeNum的数量
            int num = homeMapper.SelectNum(homeId);
            num--;
            //修改HomeNum的大小
            homeMapper.UpdateNum(num,homeId);
            //晚辈删除绑定家庭
            youngerMapper.YoungerBindHome("",UserId,0);

            dataSourceTransactionManager.commit(transaction);
            return new RestObject("200","删除成功",homeId);
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("401","error","删除失败");
        }
    }
}
