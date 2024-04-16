package com.ling.ap.Controller;

import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.GreetingsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Greetings")
public class GreetingsController {

    @Resource
    GreetingsService greetingsService;

    //获取每日问候
    @GetMapping("/GetGreetings")
    public RestObject GetGreetings() {
        return greetingsService.GetGreetings();
    }
}
