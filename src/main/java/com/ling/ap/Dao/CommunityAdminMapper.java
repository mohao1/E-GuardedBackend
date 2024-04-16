package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Community;
import com.ling.ap.Pojo.Entity.CommunityAdmin;
import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.Oldster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityAdminMapper {

    //查询自身信息
    CommunityAdmin SelectedCommunityAdminById(@Param("AdminId") String AdminId);

    //查询管理社区中的社区人员信息列表
    List<Oldster> SelectedOldsterList(@Param("AdminId") String AdminId);

    //查询管理社区中的老人信息列表
    List<CommunityUser> SelectedCommunityUserList(@Param("AdminId") String AdminId);

    //根据登录名称获取Id和密码
    Map<String,String> SelectedLogin(@Param("adminName") String adminName);

    //查询社区管理人员列表
    List<CommunityAdmin> SelectCommunityAdminList();

    //查询社区信息
    Community SelectedCommunity(@Param("AdminId") String AdminId);
}
