package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.Memorandum;
import com.ling.ap.Pojo.RestObject;

public interface MemorandumService {
    //长辈设置自己的备忘录
    RestObject AddTask(Memorandum memorandum);

    //长辈删除自己的备忘录任务(虚拟删除)
    RestObject DeleteTake(String UserId,String TakeId);

    //长辈恢复自己的备忘录任务(虚拟恢复)
    RestObject RecoveryTake(String UsedId,String TakeId);

    //长辈修改任务内容
    RestObject ModifyTask(Memorandum memorandum);

    //长辈确认完成任务
    RestObject ConfirmTask(String UserId,String TakeId);

    //长辈取消确认完成任务
    RestObject UnConfirmTask(String UserId,String TakeId);

    //删除回收站的任务
    RestObject ClearTask(String UserId,String TakeId);

    //查询回收站的任务列表
    RestObject SelectDeleteTaskList(String UserId);

    //长辈查询全部任务
    RestObject  SelectTaskList(String UserId);

    //长辈查询没有完成任务
    RestObject  SelectUnfinishedTaskList(String UserId);

    //长辈查询已经完成了的任务
    RestObject SelectFinishedTasksList(String UserId);

}
