package com.ling.ap.Controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.Entity.Memorandum;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.MemorandumService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Oldster/Memorandum")
public class OldsterMemorandumController {
    @Resource
    MemorandumService memorandumService;


    /**
     * @添加长辈的备忘录
     *
     *         String title;
     *         String content;
     *         Date setTime;
     *         String start;
     *         String end;
     *
     *         String takeId;
     *         String userId;
     *         int whether;
     *
     * */
    @PostMapping("/AddTask")
    public RestObject AddTask(@RequestBody JSONObject jsonObject, @RequestAttribute Object JWT){
        //生成uuid
        String uuid = IdUtil.simpleUUID();
        //设置缺失了的信息
        jsonObject.put("takeId",uuid);
        jsonObject.put("userId",JWT);
        jsonObject.put("whether",0);
        Memorandum memorandum = JSONObject.parseObject(jsonObject.toString(), Memorandum.class);
        return  memorandumService.AddTask(memorandum);
    }

    //删除长辈的备忘录
    //takeId
    @PostMapping("/DeleteTake")
    public RestObject DeleteTake(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String takeId = jsonObject.getObject("takeId", String.class);
        if(takeId==null||takeId.equals("")){
            return new RestObject ("401","error","数据为空");
        }
        return memorandumService.DeleteTake((String) JWT,takeId);
    }

    //恢复长辈的备忘录
    @PostMapping("/RecoveryTake")
    public  RestObject RecoveryTake(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String takeId = jsonObject.getObject("takeId", String.class);
        if(takeId==null||takeId.equals("")){
            return new RestObject ("401","error","数据为空");
        }
        return memorandumService.RecoveryTake((String) JWT,takeId);
    }

    //修改长辈的备忘录
    @PostMapping("/ModifyTask")
    public RestObject ModifyTask(@RequestBody JSONObject jsonObject){
        Memorandum memorandum = JSONObject.parseObject(jsonObject.toString(), Memorandum.class);
        if (memorandum==null){
            return new RestObject ("401","error","数据为空");
        }
        return memorandumService.ModifyTask(memorandum);
    }

    //查询长辈的全部备忘录
    @GetMapping("/SelectTaskList")
    public RestObject SelectTaskList(@RequestAttribute Object JWT){
        return memorandumService.SelectTaskList((String) JWT);
    }

    //长辈确认完成任务
    @PostMapping("/ConfirmTask")
    public RestObject ConfirmTask(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String takeId = jsonObject.getObject("takeId", String.class);
        if(takeId==null||takeId.equals("")){
            return new RestObject ("401","error","数据为空");
        }
        return memorandumService.ConfirmTask((String) JWT,takeId);
    }

    //长辈取消确认完成任务
    @PostMapping("/UnConfirmTask")
    public RestObject UnConfirmTask(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String takeId = jsonObject.getObject("takeId", String.class);
        if(takeId==null||takeId.equals("")){
            return new RestObject ("401","error","数据为空");
        }
        return memorandumService.UnConfirmTask((String) JWT,takeId);
    }

    //长辈查询没有完成任务
    @GetMapping("/SelectUnfinishedTaskList")
    public RestObject SelectUnfinishedTaskList(@RequestAttribute Object JWT){
        return memorandumService.SelectUnfinishedTaskList((String) JWT);
    }

    //长辈查询已经完成了的任务
    @GetMapping("/SelectFinishedTasksList")
    public RestObject SelectFinishedTasksList(@RequestAttribute Object JWT){
        return memorandumService.SelectFinishedTasksList((String) JWT);
    }

    //查询回收站的任务列表
    @GetMapping("/SelectDeleteTaskList")
    public  RestObject SelectDeleteTaskList(@RequestAttribute Object JWT){
        return memorandumService.SelectDeleteTaskList((String) JWT);
    }


    //删除回收站的任务
    @PostMapping("/ClearTask")
    public RestObject ClearTask(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String takeId = jsonObject.getObject("takeId", String.class);
        if(takeId==null||takeId.equals("")){
            return new RestObject ("401","error","数据为空");
        }
        return memorandumService.ClearTask((String) JWT,takeId);
    }
}
