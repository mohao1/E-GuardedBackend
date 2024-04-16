package com.ling.ap.Controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.Entity.WordsWritten;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.WordsWrittenService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;


//消息留言
@CrossOrigin
@RestController
@RequestMapping(value ="/Words/Written",method = {RequestMethod.POST,RequestMethod.GET})
public class WordsWrittenController {

    @Resource
    WordsWrittenService wordsWrittenService;

    //长辈写入家庭留言
    //WordsWritten wordsWritten
    @PostMapping("/OldsterInsertWordsWritten")
    public RestObject OldsterInsertWordsWritten(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT) {
        jsonObject.put("sender",JWT);//写入发送用户
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("wordsId",uuid);

        //获取当时日期时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        jsonObject.put("sendDate",strDate);
        

        WordsWritten wordsWritten = JSONObject.parseObject(jsonObject.toString(), WordsWritten.class);

        if (wordsWritten==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.OldsterInsertWordsWritten(wordsWritten);
    }


    //晚辈写入家庭留言
    //WordsWritten wordsWritten
    @PostMapping("/YoungerInsertWordsWritten")
    public RestObject YoungerInsertWordsWritten(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT) {
        jsonObject.put("sender",JWT);//写入发送用户
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("wordsId",uuid);

        //获取当时日期时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        jsonObject.put("sendDate",strDate);

        WordsWritten wordsWritten = JSONObject.parseObject(jsonObject.toString(), WordsWritten.class);
        if (wordsWritten==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.YoungerInsertWordsWritten(wordsWritten);
    }

    //长辈写入社区留言
    //WordsWritten wordsWritten
    @PostMapping("/OldsterInsertWordsWrittenCommunity")
    public RestObject OldsterInsertWordsWrittenCommunity(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        jsonObject.put("sender",JWT);//写入发送用户
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("wordsId",uuid);

        //获取当时日期时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        jsonObject.put("sendDate",strDate);

        WordsWritten wordsWritten = JSONObject.parseObject(jsonObject.toString(), WordsWritten.class);
        if (wordsWritten==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.OldsterInsertWordsWrittenCommunity(wordsWritten);
    }

    //社区人员写入社区留言
    //WordsWritten wordsWritten
    @PostMapping("/CommunityInsertWordsWrittenCommunity")
    public RestObject CommunityInsertWordsWrittenCommunity(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        jsonObject.put("sender",JWT);//写入发送用户
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("wordsId",uuid);


        //获取当时日期时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        jsonObject.put("sendDate",strDate);

        WordsWritten wordsWritten = JSONObject.parseObject(jsonObject.toString(), WordsWritten.class);
        if (wordsWritten==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.CommunityInsertWordsWrittenCommunity(wordsWritten);
    }

    //系统写入留言
    //WordsWritten wordsWritten
    @PostMapping("/SystemInsertWordsWritten")
    public RestObject SystemInsertWordsWritten(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT){
        jsonObject.put("sender",JWT);//写入发送用户
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("wordsId",uuid);

        //获取当时日期时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        jsonObject.put("sendDate",strDate);

        WordsWritten wordsWritten = JSONObject.parseObject(jsonObject.toString(), WordsWritten.class);
        if (wordsWritten==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.SystemInsertWordsWritten(wordsWritten);
    }


    //根据用户Id读取留言
    //String recipientId
    @GetMapping("/SelectWordsWritten")
    public RestObject SelectWordsWritten(@RequestAttribute Object JWT) {
        return wordsWrittenService.SelectWordsWritten((String) JWT);
    }


    //用户确认已读
    //String wordsId, String recipient
    @PostMapping("/ReadWordsWritten")
    public RestObject ReadWordsWritten(@RequestBody JSONObject jsonObject,@RequestAttribute Object JWT) {
        String wordsId = jsonObject.getObject("wordsId", String.class);
        if (wordsId==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.ReadWordsWritten(wordsId, (String) JWT);
    }



    //根据Id获取对方信息（获取晚辈）
    //String senderId
    @PostMapping("/GetYounger")
    public RestObject GetYounger(@RequestBody JSONObject jsonObject){
        String senderId = jsonObject.getObject("senderId", String.class);
        if (senderId==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.GetYounger(senderId);
    }

    //根据Id获取对方信息（获取长辈）
    //String senderId
    @PostMapping("/GetOldster")
    public RestObject GetOldster(@RequestBody JSONObject jsonObject){
        String senderId = jsonObject.getObject("senderId", String.class);
        if (senderId==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.GetOldster(senderId);
    }

    //根据Id获取对方信息（获取社区人员）
    //String senderId
    @PostMapping("/GetCommunityUser")
    public RestObject GetCommunityUser(@RequestBody JSONObject jsonObject){
        String senderId = jsonObject.getObject("senderId", String.class);
        if (senderId==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.GetCommunityUser(senderId);
    }

    //社区管理人员发送信息
    @PostMapping("/AdminInsertWordsWritten")
    public RestObject AdminInsertWordsWritten(@RequestAttribute Object JWT, @RequestBody JSONObject jsonObject){
        jsonObject.put("sender",JWT);//写入发送用户
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("wordsId",uuid);

        //获取当时日期时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        jsonObject.put("sendDate",strDate);

        WordsWritten wordsWritten = JSONObject.parseObject(jsonObject.toString(), WordsWritten.class);

        if (wordsWritten==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.AdminInsertWordsWritten(wordsWritten);
    }

    //长辈向社区的管理人员发送消息
    @PostMapping("/OldsterInsertWordsWrittenAdmin")
    public RestObject OldsterInsertWordsWrittenAdmin (@RequestAttribute Object JWT, @RequestBody JSONObject jsonObject){
        jsonObject.put("sender",JWT);//写入发送用户
        String uuid = IdUtil.simpleUUID();
        jsonObject.put("wordsId",uuid);

        //获取当时日期时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        jsonObject.put("sendDate",strDate);

        WordsWritten wordsWritten = JSONObject.parseObject(jsonObject.toString(), WordsWritten.class);

        if (wordsWritten==null){
            return new RestObject<>("403","数据错误",null);
        }
        return wordsWrittenService.OldsterInsertWordsWrittenAdmin(wordsWritten);
    }
}
