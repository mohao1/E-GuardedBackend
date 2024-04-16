package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityUserMapper {
    //添加一个用户信息
    int SetCommunityUser(@Param("CommunityUser") CommunityUser communityUser);

    //查询社区人员绑定老人
    ArrayList<Oldster> GetCommunityUserList(@Param("id") String id);

    //查询社区信息
    Map<String,String> GetCommunity(@Param("id") String id);

    //社区人员绑定社区或者退出社区（修改社区）
    int CommunityUserBindCommunity(@Param("CommunityId") String CommunityId,@Param("UserId") String UserId);

    //查询社区人员个人信息
    CommunityUser GetCommunityUser(@Param("id") String id);

    //修改社区人员个人信息
    int UpdateYounger(@Param("CommunityUser") CommunityUser communityUser);

    //查询社区人员列表
    List<CommunityUser> SelectCommunityUserList();
}
