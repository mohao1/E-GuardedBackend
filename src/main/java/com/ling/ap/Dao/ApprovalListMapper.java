package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.ApprovalList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApprovalListMapper {

    //根据社区管理员来查询申请列表
    List<ApprovalList> AdminSelectApprovalList(@Param("AdminId")String AdminId);

    //根据社区Id和用户Id删除数据
    int DeleteByCommunityIdAndUserId(@Param("CommunityId")String CommunityId, @Param("UserId")String UserId);

    //添加申请数据
    int InsertApprovalList(@Param("ApprovalList") ApprovalList approvalList);

    //根据用户Id查询和社区Id申请情况信息
    ApprovalList SelectByUserIdAndCommunityId(@Param("UserId") String UserId,@Param("CommunityId")String CommunityId);
}
