package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.BasicDiseasesMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Basic_Diseases;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.BasicDiseasesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BasicDiseasesServiceImpl implements BasicDiseasesService {

    @Resource
    BasicDiseasesMapper basicDiseasesMapper;

    @Resource
    YoungerMapper youngerMapper;

    @Resource
    OldsterMapper oldsterMapper;


    //长辈添加长辈基础疾病
    @Override
    public synchronized RestObject OldsterInsertBasicDisease(Basic_Diseases basicDiseases) {
        int i = basicDiseasesMapper.OldsterInsertBasicDisease(basicDiseases);
        if (i==0){
            return new RestObject("403","error","插入失败");
        }
        return new RestObject("200","Insert succeeded","插入成功");
    }

    //长辈删除长辈基础疾病
    @Override
    public synchronized RestObject OldsterDeleteBasicDisease(String UserId, String DiseaseId) {
        int i = basicDiseasesMapper.OldsterDeleteBasicDisease(UserId, DiseaseId);
        if (i==0){
            return new RestObject("403","error","删除失败");
        }
        return new RestObject("200","Delete succeeded","删除成功");
    }

    //长辈修改长辈基础疾病
    @Override
    public synchronized RestObject OldsterUpdateBasicDisease(Basic_Diseases basicDiseases) {
        int i = basicDiseasesMapper.OldsterUpdateBasicDisease(basicDiseases);
        if (i==0){
            return new RestObject("403","error","修改失败");
        }
        return new RestObject("200","Modified successfully","修改成功");
    }

    //长辈查询长辈基础疾病
    @Override
    public RestObject OldsterSelectBasicDisease(String UserId) {
        List<Basic_Diseases> list = basicDiseasesMapper.OldsterSelectBasicDisease(UserId);
        if (list.isEmpty()){
            return new RestObject("200","查询为空",list);
        }
        return new RestObject("200","查询成功",list);
    }

    //晚辈查询长辈基础疾病
    @Override
    public RestObject YoungerSelectBasicDisease(String UserId, String Oldster) {
        List<Basic_Diseases> list = basicDiseasesMapper.YoungerSelectBasicDisease(UserId, Oldster);

        if (list.isEmpty()){
            return new RestObject("200","查询为空",list);
        }
        return new RestObject("200","查询成功",list);


    }

    //晚辈添加长辈基础疾病
    @Override
    public synchronized RestObject YoungerInsertBasicDisease(String UserId, Basic_Diseases basicDiseases) {
        Oldster oldster = oldsterMapper.GetOldster(basicDiseases.getUserId());
        Younger younger = youngerMapper.GetYounger(UserId);
        if (younger==null||oldster==null){
            return new RestObject<>("403","信息错误","error");
        }
        if (!younger.getHomeId().equals(oldster.getHomeId())){
            return new RestObject<>("403","信息错误","error");
        }

        int i = basicDiseasesMapper.OldsterInsertBasicDisease(basicDiseases);
        if (i==0){
            return new RestObject("403","error","插入失败");
        }
        return new RestObject("200","Insert succeeded","插入成功");
    }

    //晚辈删除长辈基础疾病
    @Override
    public synchronized RestObject YoungerDeleteBasicDisease(String UserId, String DiseaseId ,String oldsterId) {
        Oldster oldster = oldsterMapper.GetOldster(oldsterId);
        Younger younger = youngerMapper.GetYounger(UserId);
        if (younger==null||oldster==null){
            return new RestObject<>("403","信息错误","error");
        }
        if (!younger.getHomeId().equals(oldster.getHomeId())){
            return new RestObject<>("403","信息错误","error");
        }

        int i = basicDiseasesMapper.OldsterDeleteBasicDisease(oldsterId, DiseaseId);
        if (i==0){
            return new RestObject("403","error","删除失败");
        }
        return new RestObject("200","Delete succeeded","删除成功");
    }

    //晚辈修改长辈基础疾病
    @Override
    public synchronized RestObject YoungerUpdateBasicDisease(String UserId, Basic_Diseases basicDiseases) {
        Oldster oldster = oldsterMapper.GetOldster(basicDiseases.getUserId());
        Younger younger = youngerMapper.GetYounger(UserId);
        if (younger==null||oldster==null){
            return new RestObject<>("403","信息错误","error");
        }
        if (!younger.getHomeId().equals(oldster.getHomeId())){
            return new RestObject<>("403","信息错误","error");
        }

        int i = basicDiseasesMapper.OldsterUpdateBasicDisease(basicDiseases);
        if (i==0){
            return new RestObject("403","error","修改失败");
        }
        return new RestObject("200","Modified successfully","修改成功");
    }
}
