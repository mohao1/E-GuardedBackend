package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.*;
import com.ling.ap.Pojo.Entity.*;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HeadSculptureService;
import com.ling.ap.Service.ServiceInterface.WordsWrittenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WordsWrittenServiceImpl implements WordsWrittenService {

    @Resource
    WordsWrittenMapper wordsWrittenMapper;
    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    YoungerMapper youngerMapper;

    @Resource
    CommunityUserMapper communityUserMapper;

    @Resource
    HeadSculptureService headSculptureService;

    @Resource
    CommunityAdminMapper communityAdminMapper;


    //长辈写入家庭留言
    @Override
    public RestObject OldsterInsertWordsWritten(WordsWritten wordsWritten) {
        //权限认证
        String OldsterId = wordsWritten.getSender();
        String YoungerId = wordsWritten.getRecipient();
        Younger younger = youngerMapper.GetYounger(YoungerId);
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        if (oldster==null||younger==null){
            return new RestObject<>("403","用户不在",null);
        }
        if (!oldster.getHomeId().equals(younger.getHomeId())){
            return new RestObject<>("401","没有权限发送",null);
        }
        //发送数据
        int i = wordsWrittenMapper.InsertWordsWritten(wordsWritten);
        if (i==0){
            return new RestObject<>("403","写入失败",i);
        }
        return new RestObject<>("200","写入成功",i);
    }


    //晚辈写入家庭留言
    @Override
    public RestObject YoungerInsertWordsWritten(WordsWritten wordsWritten) {
        //权限认证
        String OldsterId = wordsWritten.getRecipient();
        String YoungerId = wordsWritten.getSender();
        Younger younger = youngerMapper.GetYounger(YoungerId);
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        if (oldster==null||younger==null){
            return new RestObject<>("403","用户不在",null);
        }
        if (!oldster.getHomeId().equals(younger.getHomeId())){
            return new RestObject<>("401","没有权限发送",null);
        }
        //发送数据
        int i = wordsWrittenMapper.InsertWordsWritten(wordsWritten);
        if (i==0){
            return new RestObject<>("403","写入失败",i);
        }
        return new RestObject<>("200","写入成功",i);
    }

    //长辈写入社区留言
    @Override
    public RestObject OldsterInsertWordsWrittenCommunity(WordsWritten wordsWritten){
        //权限认证
        String OldsterId = wordsWritten.getSender();
        String UserId = wordsWritten.getRecipient();
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        CommunityUser user = communityUserMapper.GetCommunityUser(UserId);
        if (oldster==null||user==null){
            return new RestObject<>("403","用户不在",null);
        }
        if (!oldster.getCommunityId().equals(user.getCommunityId())){
            return new RestObject<>("401","没有权限发送",null);
        }
        //发送数据
        int i = wordsWrittenMapper.InsertWordsWritten(wordsWritten);
        if (i==0){
            return new RestObject<>("403","写入失败",i);
        }
        return new RestObject<>("200","写入成功",i);
    }

    //社区人员写入社区留言
    @Override
    public RestObject CommunityInsertWordsWrittenCommunity(WordsWritten wordsWritten){
        //权限认证
        String OldsterId = wordsWritten.getRecipient();
        String UserId = wordsWritten.getSender();
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        CommunityUser user = communityUserMapper.GetCommunityUser(UserId);
        if (oldster==null||user==null){
            return new RestObject<>("403","用户不在",null);
        }
        if (!oldster.getCommunityId().equals(user.getCommunityId())){
            return new RestObject<>("401","没有权限发送",null);
        }
        //发送数据
        int i = wordsWrittenMapper.InsertWordsWritten(wordsWritten);
        if (i==0){
            return new RestObject<>("403","写入失败",i);
        }
        return new RestObject<>("200","写入成功",i);
    }

    //系统写入留言
    @Override
    public RestObject SystemInsertWordsWritten(WordsWritten wordsWritten){
        //权限认证
        String OldsterId = wordsWritten.getRecipient();
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        if (oldster==null){
            return new RestObject<>("403","用户不在",null);
        }

        //发送数据
        int i = wordsWrittenMapper.InsertWordsWritten(wordsWritten);
        if (i==0){
            return new RestObject<>("403","写入失败",i);
        }
        return new RestObject<>("200","写入成功",i);
    }


    //根据用户Id读取留言
    @Override
    public RestObject SelectWordsWritten(String recipientId) {
        List<WordsWritten> wordsWritten = wordsWrittenMapper.SelectWordsWrittenById(recipientId);
        return new RestObject<>("200","查询成功",wordsWritten);
    }


    //用户确认已读
    @Override
    public RestObject ReadWordsWritten(String wordsId, String recipient) {
        int i = wordsWrittenMapper.UpdateWordsWrittenReadById(wordsId, recipient);
        if (i==0){
            return new RestObject<>("403","确认失败",i);
        }
        return new RestObject<>("200","确认成功",i);
    }

    //定期清除消息
    @Override
    public void DeleteWordsWrittenByTime() {
        //获取2天前的时间
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int dates = calendar.get(Calendar.DATE);//获取日
        String strDate=year+"-"+month+"-"+dates;

        wordsWrittenMapper.DeleteWordsWrittenByDataTime(strDate);
    }

    //根据Id获取对方信息（获取晚辈）
    @Override
    public RestObject GetYounger(String senderId){
        Younger younger = youngerMapper.GetYounger(senderId);
        Object data = headSculptureService.SelectUserHeadSculpture(senderId).getData();
        Map<String,Object> map =new HashMap<>();
        map.put("younger",younger);
        map.put("headSculptureUrl",data);
        return new RestObject<>("200","查询成功",map);
    }

    //根据Id获取对方信息（获取长辈）
    @Override
    public RestObject GetOldster(String senderId){
        Oldster oldster = oldsterMapper.GetOldster(senderId);
        Object data = headSculptureService.SelectUserHeadSculpture(senderId).getData();
        Map<String,Object> map =new HashMap<>();
        map.put("oldster",oldster);
        map.put("headSculptureUrl",data);
        return new RestObject<>("200","查询成功",map);
    }

    //根据Id获取对方信息（获取社区人员）
    @Override
    public RestObject GetCommunityUser(String senderId){
        CommunityUser communityUser = communityUserMapper.GetCommunityUser(senderId);
        Object data = headSculptureService.SelectUserHeadSculpture(senderId).getData();
        Map<String,Object> map =new HashMap<>();
        map.put("communityUser",communityUser);
        map.put("headSculptureUrl",data);
        return new RestObject<>("200","查询成功",map);
    }

    //根据发送者来删除信息
    @Override
    public int DeleteWordsWrittenByDataSenderId(String SenderId){
        return wordsWrittenMapper.DeleteWordsWrittenByDataSenderId(SenderId);
    }

    //社区管理人员发送信息
    @Override
    public RestObject AdminInsertWordsWritten(WordsWritten wordsWritten) {
        //权限认证
        String AdminId = wordsWritten.getSender();
        String OldsterId = wordsWritten.getRecipient();
        CommunityAdmin Admin = communityAdminMapper.SelectedCommunityAdminById(AdminId);
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        if (oldster==null||Admin==null){
            return new RestObject<>("403","用户不在",null);
        }
        if (!oldster.getCommunityId().equals(Admin.getCommunityId())){
            return new RestObject<>("401","没有权限发送",null);
        }
        //发送数据
        int i = wordsWrittenMapper.InsertWordsWritten(wordsWritten);
        if (i==0){
            return new RestObject<>("403","写入失败",i);
        }
        return new RestObject<>("200","写入成功",i);
    }

    //长辈向社区的管理人员发送消息
    @Override
    public RestObject OldsterInsertWordsWrittenAdmin(WordsWritten wordsWritten) {
        //权限认证
        String OldsterId = wordsWritten.getSender();
        String AdminId = wordsWritten.getRecipient();
        Oldster oldster = oldsterMapper.GetOldster(OldsterId);
        CommunityAdmin communityAdmin = communityAdminMapper.SelectedCommunityAdminById(AdminId);
        if (oldster==null||communityAdmin==null){
            return new RestObject<>("403","用户不在",null);
        }
        if (!oldster.getCommunityId().equals(communityAdmin.getCommunityId())){
            return new RestObject<>("401","没有权限发送",null);
        }
        //发送数据
        int i = wordsWrittenMapper.InsertWordsWritten(wordsWritten);
        if (i==0){
            return new RestObject<>("403","写入失败",i);
        }
        return new RestObject<>("200","写入成功",i);
    }




}
