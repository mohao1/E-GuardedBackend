package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Community;
import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.ToDo.Emergency_Contact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface OldsterMapper {
    //插入一个长辈信息
    int SetOldster(@Param("Oldster")Oldster oldster);
    //查询长辈信息
    Oldster GetOldster(@Param("id")String id);

    //查询前辈绑定晚辈
    ArrayList<Younger> GetYoungerList(@Param("id") String id);

    //查询长辈家庭信息
    Map<String,String> GetHome(@Param("id") String id);

    //长辈绑定家庭或者退出家庭（修改家庭）
    int OldsterBindHome(@Param("HomeId") String homeId,@Param("UserId") String UserId);

    //查询各个晚辈手机名字和优先级
    ArrayList<Emergency_Contact> GetEmergencyContactsList(@Param("id") String id);

    //查询优先级为3的晚辈集合
    ArrayList<Emergency_Contact> GetMaxEmergencyContactsList(@Param("id") String id);

    //获取晚辈列表
    List<String> SelectOldsterIdList();

    //长辈绑定社区或者退出社区（修改社区）
    int OldsterBindCommunity(@Param("communityId") String communityId,@Param("UserId") String UserId);

    //长辈查询社区信息
    Community SelectCommunity(@Param("OldsterId") String OldsterId);

    //查询长辈社区社区人员列表
    List<CommunityUser> SelectCommunityUserList(@Param("OldsterId") String OldsterId);

    //查询长辈社区社区人员信息
    CommunityUser SelectCommunityUser(@Param("OldsterId") String OldsterId,@Param("CommunityUserId") String CommunityUserId);

    //修改晚辈个人信息
    int UpdateOldster(@Param("Oldster")Oldster oldster);

    //查询长辈列表
    List<Oldster> SelectOldsterList();

    //长辈查询晚辈Id列表
    List<String> SelectYoungerIdListByOldster(@Param("OldsterId")String oldsterId);



}
