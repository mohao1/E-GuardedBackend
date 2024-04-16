package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Basic_Diseases;
import com.ling.ap.Pojo.Entity.Health;
import com.ling.ap.Pojo.Entity.Oldster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityManageMapper {

    //查询管理长辈信息列表
    List<Oldster> GetOldsterList(@Param("CommunityUserId") String CommunityUserId);

    //查询管理社区某个长辈身体状况十天
    List<Health> GetOldsterHealth(@Param("CommunityUserId") String CommunityUserId, @Param("OldsterId") String OldsterId);

    //查询管理社区某个长辈身体基础疾病列表
    List<Basic_Diseases> GetOldsterBasicDiseases(@Param("CommunityUserId") String CommunityUserId, @Param("OldsterId") String OldsterId);
}
