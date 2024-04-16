package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface YoungerMapper {
    //查询晚辈信息
    Younger GetYounger(@Param("id") String id);
    //添加一个晚辈信息
    int SetYounger(@Param("Younger") Younger younger);
    //查询晚辈绑定老人
    ArrayList<Oldster> GetOldsterList(@Param("id") String id);

    //查询晚辈家庭信息
    Map<String,String> GetHome(@Param("id") String id);

    //晚辈绑定家庭或者退出家庭（修改家庭）
    int YoungerBindHome(@Param("HomeId") String homeId,@Param("UserId") String UserId,@Param("Priority") int Priority);



    //查询晚辈是否绑定长辈
    String SelectYoungerBindOldster(@Param("youngerId")String youngerId,@Param("OldsterId") String OldsterId);



    //修改晚辈个人信息
    int UpdateYounger(@Param("Younger") Younger younger);

    //查询晚辈列表
    List<Younger> SelectYoungerList();

    //晚辈查询长辈Id列表
    List<String> SelectOldsterIdListByYounger(@Param("YoungerId")String youngerId);



}
