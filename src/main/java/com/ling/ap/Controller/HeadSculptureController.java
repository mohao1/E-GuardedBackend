package com.ling.ap.Controller;

import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HeadSculptureService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/HeadSculpture")
public class HeadSculptureController {
    @Resource
    HeadSculptureService headSculptureService;

    //修改头像
    @PostMapping("/UpDataHeadSculpture")
    public RestObject UpDataHeadSculpture(@RequestAttribute Object JWT, @RequestParam("file") MultipartFile file){
        return headSculptureService.UpDataUserHeadSculpture((String) JWT,file);
    }

    //根据Id查询头像
    @PostMapping("/SelectDataHeadSculpture")
    public RestObject SelectDataHeadSculptureById(@RequestAttribute Object JWT){
        return headSculptureService.SelectUserHeadSculpture((String) JWT);
    }
}
