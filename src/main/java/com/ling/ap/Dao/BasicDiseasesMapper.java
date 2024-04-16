package com.ling.ap.Dao;

import com.ling.ap.Pojo.Entity.Basic_Diseases;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BasicDiseasesMapper {

    //长辈添加长辈基础疾病
    int OldsterInsertBasicDisease(@Param("basicDiseases") Basic_Diseases basicDiseases);

    //长辈删除长辈基础疾病
    int OldsterDeleteBasicDisease(@Param("UserId") String UserId,@Param("DiseaseId") String DiseaseId);

    //长辈修改长辈基础疾病
    int OldsterUpdateBasicDisease(@Param("basicDiseases") Basic_Diseases basicDiseases);

    //长辈查询长辈基础疾病
    List<Basic_Diseases> OldsterSelectBasicDisease(@Param("UserId") String UserId);


    //晚辈查询长辈基础疾病
    List<Basic_Diseases> YoungerSelectBasicDisease(@Param("UserId") String UserId,@Param("Oldster") String Oldster);

    //根据疾病Id查询疾病
    Basic_Diseases SelectBasicDiseaseById(@Param("Oldster") String Oldster,@Param("DiseasesId") String DiseasesId);
    //根据个人ID和疾病的名称来查询疾病
    Basic_Diseases SelectBasicDiseaseByIdIsName(@Param("Oldster") String Oldster,@Param("DiseasesName") String DiseasesName);
}
