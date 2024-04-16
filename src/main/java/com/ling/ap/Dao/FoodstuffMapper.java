package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Foodstuff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodstuffMapper {

    //设置食品记录
    int InsertFoodstuff(@Param("foodstuff") Foodstuff foodstuff);

    //长辈查询食品记录
    List<Foodstuff> OldsterSelectFoodstuff(@Param("oldster") String oldster);

    //删除或者恢复食品记录
    int DeleteResumeFoodstuff(@Param("oldster") String oldster,@Param("takeId") String takeId,@Param("flag") int flag);

    //修改食品记录
    int UpdateFoodstuff(@Param("foodstuff") Foodstuff foodstuff);

    //长辈查询回收站的记录
    List<Foodstuff> OldsterSelectRetrieveFoodstuff(@Param("oldster") String oldster);

    //清除回收站的记录
    int CleanFoodstuff(@Param("oldster") String oldster,@Param("takeId") String takeId);

    //清除回收站的所有数据
    int CleanAllFoodstuff(@Param("oldster") String oldster);

    //晚辈查询食品记录
    List<Foodstuff> YoungerSelectFoodstuff(@Param("oldster") String oldster,@Param("younger") String younger);

    //晚辈查询回收站的记录
    List<Foodstuff> YoungerSelectRetrieveFoodstuff(@Param("oldster") String oldster,@Param("younger") String younger);

}
