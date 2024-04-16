package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Drug;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DrugMapper {
    //设置药品提醒
    int InsertDrug(@Param("drug") Drug drug);

    //长辈查询药品提醒
    List<Map<String,Object>> OldsterSelectDrug(@Param("oldsterId") String oldsterId);

    //删除药品提醒
    int DeleteDrug(@Param("takeId") String takeId,@Param("oldsterId") String oldsterId);

    //修改药品提醒
    int UpdateDrug(@Param("drug") Drug drug);

    //晚辈查询药品提醒
    List<Map<String,Object>> YoungerSelectDrug(@Param("oldsterId") String oldsterId,@Param("youngerId") String youngerId);

    //长辈确认药品提醒完成
    int ConfirmDrugs(@Param("oldsterId") String oldsterId,@Param("takeId") String takeId);

    //设置修改查询时间并且重置确认时间
    int SetSelectTime(@Param("oldsterId") String oldsterId,@Param("selectTime") String selectTime);

    //查询查询时间
    List<String> GetSelectTime(@Param("oldsterId") String oldsterId);

}
