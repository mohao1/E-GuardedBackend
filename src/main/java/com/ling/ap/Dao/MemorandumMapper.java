package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Memorandum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//备忘录的操作
@Mapper
public interface MemorandumMapper {

    //长辈添加自己的备忘录(添加任务)
    int AddTask(@Param("Memorandum") Memorandum memorandum);

    //长辈查询自己的备忘录(任务列表)
    List<Memorandum> SelectTaskList(@Param("UserId") String UserId);

    //删除任务(虚拟删除)
    int DeleteTask(@Param("TakeId") String TakeId,@Param("UserId") String UserId);

    //恢复任务
    int RecoveryTask(@Param("TakeId") String TakeId,@Param("UserId") String UserId);

    //清空删除任务(证实删除)
    int ClearTask(@Param("TakeId") String TakeId,@Param("UserId") String UserId);

    //查询已被删除任务列表
    List<Memorandum> SelectDeleteTaskList(@Param("UserId") String UserId);

    //确认或者取消确认完成任务
    int ConfirmTask(@Param("TakeId") String TakeId,@Param("UserId") String UserId,@Param("Whether")int Whether);

    //查询未完成的任务(列表)
    List<Memorandum> SelectUnfinishedTasksList(@Param("UserId") String UserId);

    //查询已完成的任务(列表)
    List<Memorandum> SelectFinishedTasksList(@Param("UserId") String UserId);

    //修改任务内容
    int ModifyTask(@Param("Memorandum") Memorandum memorandum);

    //使用对应TakeId还有长辈Id查询对应任务
    Memorandum SelectMemorandumById(@Param("UserId") String UserId,@Param("TakeId") String TakeId);




}
