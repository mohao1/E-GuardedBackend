package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.RestObject;
import org.springframework.web.multipart.MultipartFile;

public interface VoiceTextService {

    //文字转换语音
     RestObject VoiceToText(String Text, String id);

     //语音转换文字
     RestObject TextToVoice(MultipartFile file, String Id);
}
