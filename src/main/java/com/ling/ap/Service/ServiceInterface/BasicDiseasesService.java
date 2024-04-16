package com.ling.ap.Service.ServiceInterface;

import com.ling.ap.Pojo.Entity.Basic_Diseases;
import com.ling.ap.Pojo.RestObject;

public interface BasicDiseasesService {

    //长辈添加长辈基础疾病
    RestObject OldsterInsertBasicDisease(Basic_Diseases basicDiseases);

    //长辈删除长辈基础疾病
    RestObject OldsterDeleteBasicDisease(String UserId,String DiseaseId);

    //长辈修改长辈基础疾病
    RestObject OldsterUpdateBasicDisease(Basic_Diseases basicDiseases);

    //长辈查询长辈基础疾病
    RestObject OldsterSelectBasicDisease(String UserId);


    //晚辈查询长辈基础疾病
    RestObject YoungerSelectBasicDisease(String UserId,String Oldster);

    //晚辈添加长辈基础疾病
    RestObject YoungerInsertBasicDisease(String UserId,Basic_Diseases basicDiseases);

    //晚辈删除长辈基础疾病
    RestObject YoungerDeleteBasicDisease(String UserId,String DiseaseId,String oldster);

    //晚辈修改长辈基础疾病
    RestObject YoungerUpdateBasicDisease(String UserId,Basic_Diseases basicDiseases);

//    //长辈根据疾病Id查询疾病
//    RestObject SelectBasicDiseaseById(String Oldster,String DiseasesId);
//
//    //根据个人ID和疾病的名称来查询疾病
//    RestObject SelectBasicDiseaseByIdIsName(String Oldster,String DiseasesName);
}
