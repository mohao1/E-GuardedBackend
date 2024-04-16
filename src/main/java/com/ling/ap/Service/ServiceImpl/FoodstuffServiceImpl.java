package com.ling.ap.Service.ServiceImpl;

import com.ling.ap.Dao.FoodstuffMapper;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Foodstuff;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.FoodstuffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class FoodstuffServiceImpl implements FoodstuffService {

    @Resource
    FoodstuffMapper foodstuffMapper;

    @Resource
    YoungerMapper youngerMapper;
    @Resource
    OldsterMapper oldsterMapper;


    //长辈设置食品记录
    @Override
    public synchronized RestObject OldsterInsertFoodstuff(Foodstuff foodstuff) {
        int i = foodstuffMapper.InsertFoodstuff(foodstuff);
        if (i==0){
            return new RestObject("403","Insert failed","插入失败");
        }
        return new RestObject("200","插入成功",i);
    }

    //长辈查询食品记录
    @Override
    public RestObject OldsterSelectFoodstuff(String oldster) {
        List<Foodstuff> list = foodstuffMapper.OldsterSelectFoodstuff(oldster);
        if (list.isEmpty()){
            new RestObject("403","查询失败",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //长辈删除食品记录
    @Override
    public synchronized RestObject OldsterDeleteFoodstuff(String oldster, String takeId) {
        int i = foodstuffMapper.DeleteResumeFoodstuff(oldster,takeId,1);
        if (i==0){
            return new RestObject("403","Delete failed","删除失败");
        }
        return new RestObject("200","删除成功",1);
    }

    //长辈恢复食品记录
    @Override
    public synchronized RestObject OldsterResumeFoodstuff(String oldster, String takeId) {
        int i = foodstuffMapper.DeleteResumeFoodstuff(oldster,takeId,0);
        if (i==0){
            return new RestObject("403","Resume failed","恢复失败");
        }
        return new RestObject("200","恢复成功",1);
    }

    //长辈修改食品记录
    @Override
    public synchronized RestObject OldsterUpdateFoodstuff(Foodstuff foodstuff) {
        int i = foodstuffMapper.UpdateFoodstuff(foodstuff);
        if (i==0){
            return new RestObject("403","Update failed","更新失败");
        }
        return new RestObject("200","更新成功",i);
    }

    //长辈查询回收站的记录
    @Override
    public RestObject OldsterSelectRetrieveFoodstuff(String oldster) {
        List<Foodstuff> list = foodstuffMapper.OldsterSelectRetrieveFoodstuff(oldster);
        if (list.isEmpty()){
            new RestObject("403","查询失败",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //长辈清除回收站的记录
    @Override
    public synchronized RestObject OldsterCleanFoodstuff(String oldster, String takeId) {
        int i = foodstuffMapper.CleanFoodstuff(oldster,takeId);
        if (i==0){
            return new RestObject("403","Clean failed","清除失败");
        }
        return new RestObject("200","清除成功",i);
    }

    //长辈清除回收站的所有数据
    @Override
    public synchronized RestObject OldsterCleanAllFoodstuff(String oldster) {
        int i = foodstuffMapper.CleanAllFoodstuff(oldster);
        if (i==0){
            return new RestObject("403","Clean failed","清除失败");
        }
        return new RestObject("200","清除成功",i);
    }





    //晚辈查询食品记录
    @Override
    public RestObject YoungerSelectFoodstuff(String oldster, String younger) {
        List<Foodstuff> list = foodstuffMapper.YoungerSelectFoodstuff(oldster,younger);
        if (list.isEmpty()){
            new RestObject("403","查询失败",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //晚辈查询回收站的记录
    @Override
    public RestObject YoungerSelectRetrieveFoodstuff(String oldster, String younger) {
        List<Foodstuff> list = foodstuffMapper.YoungerSelectRetrieveFoodstuff(oldster,younger);
        if (list.isEmpty()){
            new RestObject("403","查询失败",list);
        }
        return new RestObject<>("200","查询成功",list);
    }

    //晚辈清除回收站的记录
    @Override
    public synchronized RestObject YoungerCleanFoodstuff(String oldster, String takeId, String younger) {
        Oldster oldster1 = oldsterMapper.GetOldster(oldster);
        Younger younger1 = youngerMapper.GetYounger(younger);
        if (oldster1==null||younger1==null){
            return new RestObject<>("401","没有权限访问","error");
        }
        if (!younger1.getHomeId().equals(oldster1.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }

        int i = foodstuffMapper.CleanFoodstuff(oldster,takeId);
        if (i==0){
            return new RestObject("403","Clean failed","清除失败");
        }
        return new RestObject("200","清除成功",i);
    }

    //晚辈清除回收站的所有数据
    @Override
    public synchronized RestObject YoungerCleanAllFoodstuff(String oldster, String younger) {
        Oldster oldster1 = oldsterMapper.GetOldster(oldster);
        Younger younger1 = youngerMapper.GetYounger(younger);
        if (oldster1==null||younger1==null){
            return new RestObject<>("401","没有权限访问","error");
        }
        if (!younger1.getHomeId().equals(oldster1.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }

        int i = foodstuffMapper.CleanAllFoodstuff(oldster);
        if (i==0){
            return new RestObject("403","Clean failed","清除失败");
        }
        return new RestObject("200","清除成功",i);
    }

    //晚辈删除食品记录
    @Override
    public synchronized RestObject YoungerDeleteFoodstuff(String oldster, String takeId, String younger) {
        Oldster oldster1 = oldsterMapper.GetOldster(oldster);
        Younger younger1 = youngerMapper.GetYounger(younger);
        if (oldster1==null||younger1==null){
            return new RestObject<>("401","没有权限访问","error");
        }
        if (!younger1.getHomeId().equals(oldster1.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }

        int i = foodstuffMapper.DeleteResumeFoodstuff(oldster,takeId,1);
        if (i==0){
            return new RestObject("403","Delete failed","删除失败");
        }
        return new RestObject("200","删除成功",1);
    }

    //晚辈恢复食品记录
    @Override
    public synchronized RestObject YoungerResumeFoodstuff(String oldster, String takeId, String younger) {
        Oldster oldster1 = oldsterMapper.GetOldster(oldster);
        Younger younger1 = youngerMapper.GetYounger(younger);
        if (oldster1==null||younger1==null){
            return new RestObject<>("401","没有权限访问","error");
        }
        if (!younger1.getHomeId().equals(oldster1.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }

        int i = foodstuffMapper.DeleteResumeFoodstuff(oldster,takeId,0);
        if (i==0){
            return new RestObject("403","Resume failed","恢复失败");
        }
        return new RestObject("200","恢复成功",1);
    }

    //晚辈设置食品记录
    @Override
    public synchronized RestObject YoungerInsertFoodstuff(Foodstuff foodstuff, String younger) {
        Oldster oldster1 = oldsterMapper.GetOldster(foodstuff.getUserId());
        Younger younger1 = youngerMapper.GetYounger(younger);
        if (oldster1==null||younger1==null){
            return new RestObject<>("401","没有权限访问","error");
        }
        if (!younger1.getHomeId().equals(oldster1.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }

        int i = foodstuffMapper.InsertFoodstuff(foodstuff);
        if (i==0){
            return new RestObject("403","Insert failed","插入失败");
        }
        return new RestObject("200","插入成功",i);
    }

    //修改食品记录
    @Override
    public synchronized RestObject YoungerUpdateFoodstuff(Foodstuff foodstuff, String younger) {
        Oldster oldster1 = oldsterMapper.GetOldster(foodstuff.getUserId());
        Younger younger1 = youngerMapper.GetYounger(younger);
        if (oldster1==null||younger1==null){
            return new RestObject<>("401","没有权限访问","error");
        }
        if (!younger1.getHomeId().equals(oldster1.getHomeId())){
            return new RestObject<>("401","没有权限访问","error");
        }

        int i = foodstuffMapper.UpdateFoodstuff(foodstuff);
        if (i==0){
            return new RestObject("403","Update failed","更新失败");
        }
        return new RestObject("200","更新成功",i);
    }
}
