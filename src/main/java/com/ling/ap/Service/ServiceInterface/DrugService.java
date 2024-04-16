package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.Drug;
import com.ling.ap.Pojo.Entity.DrugNum;
import com.ling.ap.Pojo.RestObject;

public interface DrugService {
    //长辈设置药品提醒
    RestObject OldsterInsertDrug(Drug drug, DrugNum drugNum);

    //长辈确认药品提醒
    RestObject OldsterConfirmDrug(String oldsterId, String takeId,int DeleteNum,String drugId);

    //长辈查询药品提醒
    RestObject OldsterSelectDrug(String oldsterId);

    //长辈修改药品提醒
    RestObject OldsterUpdateDrug(Drug drug);

    //长辈删除药品提醒
    RestObject OldsterDeleteDrug(String takeId,String oldsterId);

    //长辈修改药品数量
    RestObject OldsterUpdateDrugNum(DrugNum drugNum);


    //晚辈设置药品提醒
    RestObject YoungerInsertDrug(Drug drug, DrugNum drugNum,String YoungerId);

    //晚辈查询药品提醒
    RestObject YoungerSelectDrug(String oldsterId, String youngerId);

    //晚辈修改药品提醒
    RestObject YoungerUpdateDrug(Drug drug,String YoungerId);

    //晚辈删除药品提醒
    RestObject YoungerDeleteDrug(String takeId,String youngerId,String OldsterId);

    //晚辈修改药品数量
    RestObject YoungerUpdateDrugNum(DrugNum drugNum,String youngerId);

}
