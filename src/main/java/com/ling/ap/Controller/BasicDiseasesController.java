package com.ling.ap.Controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.Entity.Basic_Diseases;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.BasicDiseasesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/BasicDiseases")
public class BasicDiseasesController {

    @Resource
    BasicDiseasesService basicDiseasesService;

    //长辈添加长辈基础疾病
    @PostMapping("/Oldster/InsertBasicDisease")
    public RestObject OldsterInsertBasicDisease(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT) {
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("diseaseId",uuid);
        jsonObject.put("userId",JWT);
        Basic_Diseases bean = JSONUtil.toBean(jsonObject.toString(), Basic_Diseases.class, false);
        return basicDiseasesService.OldsterInsertBasicDisease(bean);
    }

    //长辈删除长辈基础疾病
    @PostMapping("/Oldster/DeleteBasicDisease")
    public RestObject OldsterDeleteBasicDisease(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT) {
        String diseaseId = jsonObject.getObject("diseaseId",String.class);
        return basicDiseasesService.OldsterDeleteBasicDisease((String) JWT,diseaseId);
    }

    //长辈修改长辈基础疾病
    @PostMapping("/Oldster/UpdateBasicDisease")
    public RestObject OldsterUpdateBasicDisease(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT) {
        jsonObject.put("userId",JWT);
        Basic_Diseases bean = JSONUtil.toBean(jsonObject.toString(), Basic_Diseases.class, false);
        return basicDiseasesService.OldsterUpdateBasicDisease(bean);
    }

    //长辈查询长辈基础疾病
    @GetMapping ("/Oldster/SelectBasicDisease")
    public RestObject OldsterSelectBasicDisease(@RequestAttribute Object JWT) {
        return basicDiseasesService.OldsterSelectBasicDisease((String) JWT);
    }

    //晚辈查询长辈基础疾病
    @PostMapping("/Younger/SelectBasicDisease")
    public RestObject YoungerSelectBasicDisease(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT) {
        String oldsterId = jsonObject.getObject("oldsterId",String.class);
        return basicDiseasesService.YoungerSelectBasicDisease((String) JWT,oldsterId);
    }

    //晚辈添加长辈基础疾病
    @PostMapping("/Younger/InsertBasicDisease")
    public RestObject YoungerInsertBasicDisease(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT) {
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("diseaseId",uuid);
        Basic_Diseases bean = JSONUtil.toBean(jsonObject.toString(), Basic_Diseases.class, false);
        return basicDiseasesService.YoungerInsertBasicDisease((String) JWT,bean);
    }

    //晚辈删除长辈基础疾病
    @PostMapping("/Younger/DeleteBasicDisease")
    public RestObject YoungerDeleteBasicDisease(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String diseaseId = jsonObject.getObject("diseaseId", String.class);
        String oldsterId = jsonObject.getObject("oldsterId", String.class);
        return basicDiseasesService.YoungerDeleteBasicDisease((String) JWT,diseaseId,oldsterId);
    }

    //晚辈修改长辈基础疾病
    @PostMapping("/Younger/UpdateBasicDisease")
    public RestObject YoungerUpdateBasicDisease(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){

        Basic_Diseases bean = JSONUtil.toBean(jsonObject.toString(), Basic_Diseases.class, false);
        return basicDiseasesService.YoungerUpdateBasicDisease((String) JWT,bean);

    }
}
