package com.ling.ap.Controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.Entity.Memorandum;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HomeMemberListService;
import com.ling.ap.Service.ServiceInterface.MemorandumService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Younger/Memorandum")
public class YoungerMemorandumController {
    @Resource
    MemorandumService memorandumService;
    @Resource
    HomeMemberListService homeMemberListService;


    /**
     * @晚辈添加长辈的备忘录
     *
     *         String title;
     *         String content;
     *         Date setTime;
     *         Date start;
     *         Date end;
     *         String userId;
     *
     *         String takeId;
     *         int whether;
     *
     * */
    @PostMapping("/AddTask")
    public RestObject AddTask(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT){

        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, jsonObject.getObject("userId", String.class));
        if (!b){
            return new RestObject("401","No permission","没有权限添加");
        }

        //生成uuid
        String uuid = IdUtil.simpleUUID();
        //设置缺失了的信息
        jsonObject.put("takeId",uuid);
        jsonObject.put("whether",0);
        Memorandum memorandum = JSONObject.parseObject(jsonObject.toString(), Memorandum.class);
        return  memorandumService.AddTask(memorandum);
    }

    //晚辈查询长辈没有完成任务
    //OldsterId
    @PostMapping("/SelectUnfinishedTaskList")
    public RestObject SelectUnfinishedTaskList(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String OldsterId = jsonObject.getObject("OldsterId", String.class);
        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, OldsterId);
        if (!b){
            return new RestObject("401","No permission","没有权限查询");
        }

        return memorandumService.SelectUnfinishedTaskList(OldsterId);
    }

    //晚辈查询长辈已经完成了的任务
    //OldsterId
    @PostMapping ("/SelectFinishedTasksList")
    public RestObject SelectFinishedTasksList(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String OldsterId = jsonObject.getObject("OldsterId", String.class);
        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, OldsterId);
        if (!b){
            return new RestObject("401","No permission","没有权限查询");
        }

        return memorandumService.SelectFinishedTasksList(OldsterId);
    }

    //晚辈删除长辈的备忘录
    //takeId
    //OldsterId
    @PostMapping("/DeleteTake")
    public RestObject DeleteTake(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String takeId = jsonObject.getObject("takeId", String.class);
        if(takeId==null||takeId.equals("")){
            return new RestObject ("401","error","数据为空");
        }

        String OldsterId = jsonObject.getObject("OldsterId", String.class);
        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, OldsterId);
        if (!b){
            return new RestObject("401","No permission","没有权限删除");
        }

        return memorandumService.DeleteTake(OldsterId,takeId);
    }

    //晚辈查询长辈的全部备忘录
    //OldsterId
    @PostMapping("/SelectTaskList")
    public RestObject SelectTaskList(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){

        String OldsterId = jsonObject.getObject("OldsterId", String.class);
        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, OldsterId);
        if (!b){
            return new RestObject("401","No permission","没有权限查询");
        }

        return memorandumService.SelectTaskList(OldsterId);
    }

    //晚辈恢复长辈的备忘录
    //takeId
    //OldsterId
    @PostMapping("/RecoveryTake")
    public  RestObject RecoveryTake(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String takeId = jsonObject.getObject("takeId", String.class);
        if(takeId==null||takeId.equals("")){
            return new RestObject ("401","error","数据为空");
        }

        String OldsterId = jsonObject.getObject("OldsterId", String.class);
        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, OldsterId);
        if (!b){
            return new RestObject("401","No permission","没有权限恢复");
        }


        return memorandumService.RecoveryTake(OldsterId,takeId);
    }

    //晚辈查询回收站的任务列表
    //OldsterId
    @PostMapping("/SelectDeleteTaskList")
    public  RestObject SelectDeleteTaskList(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){

        String OldsterId = jsonObject.getObject("OldsterId", String.class);
        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, OldsterId);
        if (!b){
            return new RestObject("401","No permission","没有权限查询");
        }

        return memorandumService.SelectDeleteTaskList(OldsterId);
    }


    //晚辈删除回收站的任务
    //takeId
    //OldsterId
    @PostMapping("/ClearTask")
    public RestObject ClearTask(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String takeId = jsonObject.getObject("takeId", String.class);
        if(takeId==null||takeId.equals("")){
            return new RestObject ("401","error","数据为空");
        }


        String OldsterId = jsonObject.getObject("OldsterId", String.class);
        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, OldsterId);
        if (!b){
            return new RestObject("401","No permission","没有权限删除");
        }

        return memorandumService.ClearTask(OldsterId,takeId);
    }



    //晚辈修改长辈的备忘录
    @PostMapping("/ModifyTask")
    public RestObject ModifyTask(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        Memorandum memorandum = JSONObject.parseObject(jsonObject.toString(), Memorandum.class);
        if (memorandum==null){
            return new RestObject ("401","error","数据为空");
        }


        String OldsterId = jsonObject.getObject("userId", String.class);
        boolean b = homeMemberListService.QueryYoungerBindOldster((String) JWT, OldsterId);
        if (!b){
            return new RestObject("401","No permission","没有权限修改");
        }

        return memorandumService.ModifyTask(memorandum);
    }


}
