package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.Foodstuff;
import com.ling.ap.Pojo.RestObject;

public interface FoodstuffService {

    //长辈设置食品记录
    RestObject OldsterInsertFoodstuff(Foodstuff foodstuff);

    //长辈查询食品记录
    RestObject OldsterSelectFoodstuff(String oldster);

    //删除食品记录
    RestObject OldsterDeleteFoodstuff(String oldster,String takeId);

    //恢复食品记录
    RestObject OldsterResumeFoodstuff(String oldster,String takeId);

    //修改食品记录
    RestObject OldsterUpdateFoodstuff(Foodstuff foodstuff);

    //长辈查询回收站的记录
    RestObject  OldsterSelectRetrieveFoodstuff(String oldster);

    //长辈清除回收站的记录
    RestObject  OldsterCleanFoodstuff(String oldster,String takeId);

    //长辈清除回收站的所有数据
    RestObject  OldsterCleanAllFoodstuff(String oldster);





    //晚辈查询食品记录
    RestObject  YoungerSelectFoodstuff(String oldster,String younger);

    //晚辈查询回收站的记录
    RestObject  YoungerSelectRetrieveFoodstuff(String oldster,String younger);


    //晚辈清除回收站的记录
    RestObject  YoungerCleanFoodstuff(String oldster,String takeId,String younger);

    //晚辈清除回收站的所有数据
    RestObject  YoungerCleanAllFoodstuff(String oldster,String younger);


    //晚辈删除食品记录
    RestObject YoungerDeleteFoodstuff(String oldster,String takeId,String younger);

    //晚辈恢复食品记录
    RestObject YoungerResumeFoodstuff(String oldster,String takeId,String younger);

    //晚辈设置食品记录
    RestObject YoungerInsertFoodstuff(Foodstuff foodstuff,String younger);

    //修改食品记录
    RestObject YoungerUpdateFoodstuff(Foodstuff foodstuff,String younger);
}
