package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.SosRecord;
import com.ling.ap.Pojo.RestObject;

public interface EmergencySosService {

    //给全部的晚辈发送信息
    RestObject MessageForHelp(String id);

    //返回最高优先级的人员信息
    RestObject YoungerList(String id);

    //求救记录
    void SosRecord(SosRecord sosRecord);
}
