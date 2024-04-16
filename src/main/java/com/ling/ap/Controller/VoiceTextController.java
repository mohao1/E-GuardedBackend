package com.ling.ap.Controller;


import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.VoiceTextService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

//文字语音转换接口
@RestController
@RequestMapping("/Conversion")
public class VoiceTextController {

    @Resource
    VoiceTextService voiceTextService;

    @PostMapping("/VoiceToText")
    public RestObject VoiceToText(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        String test = jsonObject.getObject("test", String.class);
        return voiceTextService.VoiceToText(test, (String) JWT);
    }

    @PostMapping("/SpeechToText")
    public RestObject SpeechToText(@RequestAttribute Object JWT, @RequestParam("file") MultipartFile file) {
        return voiceTextService.TextToVoice(file, (String) JWT);
    }
}
