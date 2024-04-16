package com.ling.ap.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Dao.UserLoginMapper;
import com.ling.ap.Pojo.RestObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class GetPhoneController {
    @Resource
    UserLoginMapper userLoginMapper;

    @PostMapping("/GetPhone")
    public RestObject GetPhone(@RequestAttribute Object JWT, @RequestBody JSONObject jsonObject){
        String id = jsonObject.getObject("id", String.class);
        Map<String, Object> map = userLoginMapper.GetPhoneById(id);
        return new RestObject("200", (String) JWT,map);
    }
}
