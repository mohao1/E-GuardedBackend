package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.VoiceTextService;
import com.ling.ap.Utils.Character_recognition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class VoiceTextServiceImpl implements VoiceTextService {

    @Resource
    Character_recognition character_recognition;

    //文字转换语音
    @Override
    public RestObject VoiceToText(String Text, String id) {
        try{
            String playUrl = character_recognition.GetPlayUrl(Text, id);
            return new RestObject("200","转换成功",playUrl);
        }catch (Exception e){
            return new RestObject("402","Conversion error","转换错误");
        }
    }

    //语音转换文字
    @Override
    public RestObject TextToVoice(MultipartFile file,String Id) {
        try {
            Object speech = character_recognition.SpeechToText(file, Id);
            return new RestObject("200","转换成功",speech);
        }catch (Exception e){
            return new RestObject("402","Conversion error","转换错误");
        }
    }
}
