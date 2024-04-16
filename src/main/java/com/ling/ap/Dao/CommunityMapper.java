package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {

    //创建社区
    int CreateCommunity(@Param("community")Community community);
    //删除社区
    int DeleteCommunity(@Param("communityId")String community ,@Param("adminId")String adminId);

    //修改社区长辈人数
    int UpdateCommunityOldster(@Param("Num") int Num,@Param("communityId")String community);

    //修改社区人员人数
    int UpdateCommunityYounger(@Param("Num") int Num,@Param("communityId")String community);

    //查询社区长辈人数
    int SelectCommunityOldsterNum(@Param("communityId")String community);

    //查询社区人员人数
    int SelectCommunityUserNum(@Param("communityId")String community);

    //查询社区信息
    Community SelectCommunity(@Param("communityId")String community);

    //查询社区列表
    List<Community> SelectCommunityList();

}
