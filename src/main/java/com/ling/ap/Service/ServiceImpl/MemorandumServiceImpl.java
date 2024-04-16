package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Service.ServiceInterface.ActivityRecordService;
import com.ling.ap.Service.ServiceInterface.MemorandumService;
import com.ling.ap.Dao.MemorandumMapper;
import com.ling.ap.Pojo.Entity.Memorandum;
import com.ling.ap.Pojo.RestObject;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemorandumServiceImpl implements MemorandumService {

    @Resource
    MemorandumMapper memorandumMapper;

    //事务生成
    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    //事务对象
    @Resource
    TransactionDefinition transactionDefinition;

    @Resource
    ActivityRecordService activityRecordService;

    //长辈设置自己的备忘录
    @Override
    public synchronized RestObject AddTask(Memorandum memorandum) {
        int i = memorandumMapper.AddTask(memorandum);
        if (i==0){
            return new RestObject("403","Add failed","添加失败");
        }
        activityRecordService.SetActivityRecord(memorandum.getUserId(),"长辈设置了备忘录",memorandum.getContent());
        return new RestObject("200","添加成功",memorandum.getTakeId());
    }

    //长辈删除自己的备忘录任务(虚拟删除)
    @Override
    public synchronized RestObject DeleteTake(String UserId,String TakeId) {
        int task = memorandumMapper.DeleteTask(TakeId, UserId);
        if (task==0){
            new RestObject("403","Delete failed","删除失败");
        }
        return new RestObject("200","删除成功",TakeId);
    }

    //长辈恢复自己的备忘录任务(虚拟恢复)
    @Override
    public synchronized RestObject RecoveryTake(String UserId, String TakeId) {
        int task = memorandumMapper.RecoveryTask(TakeId, UserId);
        if (task==0){
            new RestObject("403","Recovery failed","恢复失败");
        }
        return new RestObject("200","恢复成功",TakeId);
    }

    //长辈修改任务内容
    @Override
    public synchronized RestObject ModifyTask(Memorandum memorandum) {
        TransactionStatus transaction = null;
        transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);

        try {
            Memorandum memorandumId = memorandumMapper.SelectMemorandumById(memorandum.getUserId(), memorandum.getTakeId());
            if (memorandumId==null ){
                if (transaction!=null){
                    dataSourceTransactionManager.rollback(transaction);
                }
                return new RestObject("401","The task is not available","任务不在");
            }
            memorandumMapper.ModifyTask(memorandum);
            dataSourceTransactionManager.commit(transaction);
            activityRecordService.SetActivityRecord(memorandum.getUserId(),"长辈修改了备忘录",memorandum.getContent());
            return new RestObject("200","修改成功",memorandum.getTakeId());
        }catch (Exception e){
            if (transaction!=null){
                dataSourceTransactionManager.rollback(transaction);
            }
            return new RestObject("200","修改失败",e);
        }
    }

    //长辈确认完成任务
    @Override
    public RestObject ConfirmTask(String UserId, String TakeId) {
        Memorandum memorandumId = memorandumMapper.SelectMemorandumById(UserId, TakeId);
        if (memorandumId==null){
            return new RestObject("401","The task is not available","任务不在");
        }

        memorandumMapper.ConfirmTask(TakeId,UserId,1);
        activityRecordService.SetActivityRecord(UserId,"长辈完成了备忘录",memorandumId.getContent());
        return new RestObject("200","确认成功",TakeId);
    }

    //长辈取消确认完成任务
    @Override
    public RestObject UnConfirmTask(String UserId, String TakeId) {

        Memorandum memorandumId = memorandumMapper.SelectMemorandumById(UserId, TakeId);
        if (memorandumId==null){
            return new RestObject("401","The task is not available","任务不在");
        }

        memorandumMapper.ConfirmTask(TakeId,UserId,0);
        return new RestObject("200","取消确认成功",TakeId);
    }

    //删除回收站的任务
    @Override
    public synchronized RestObject ClearTask(String UserId, String TakeId) {
        Memorandum memorandum = memorandumMapper.SelectMemorandumById(UserId, TakeId);
        if (memorandum==null){
            return new RestObject("403","The task is not available","任务不在");
        }
        memorandumMapper.ClearTask(TakeId,UserId);
        return new RestObject("200","删除成功",TakeId);
    }

    //查询回收站的任务列表
    @Override
    public  RestObject SelectDeleteTaskList(String UserId) {
        List<Memorandum> memorandums = memorandumMapper.SelectDeleteTaskList(UserId);
        if (memorandums==null){
            return new RestObject("403","Query failed","查询失败");
        }
        return new RestObject("200","查询成功",memorandums);
    }

    //长辈查询全部任务
    @Override
    public RestObject SelectTaskList(String UserId) {
        List<Memorandum> memorandumList = memorandumMapper.SelectTaskList(UserId);
        if (memorandumList==null){
            return new RestObject("403","Query failed","查询失败");
        }
        return new RestObject("200","查询成功",memorandumList);
    }

    //长辈查询没有完成任务
    @Override
    public RestObject SelectUnfinishedTaskList(String UserId) {
        List<Memorandum> memorandums = memorandumMapper.SelectUnfinishedTasksList(UserId);
        if (memorandums==null){
            return new RestObject("403","Query failed","查询失败");
        }
        return new RestObject("200","查询成功",memorandums);
    }

    //长辈查询已经完成了的任务
    @Override
    public RestObject SelectFinishedTasksList(String UserId) {
        List<Memorandum> memorandums = memorandumMapper.SelectFinishedTasksList(UserId);
        if (memorandums==null){
            return new RestObject("403","Query failed","查询失败");
        }
        return new RestObject("200","查询成功",memorandums);
    }


}
