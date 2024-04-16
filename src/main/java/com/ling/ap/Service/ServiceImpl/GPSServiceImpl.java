package com.ling.ap.Service.ServiceImpl;

import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Dao.YoungerMapper;
import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.Younger;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Pojo.ToDo.GPSInformation;
import com.ling.ap.Service.ServiceInterface.GPSService;
import com.ling.ap.Utils.RedisUtil_1;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GPSServiceImpl implements GPSService {
    @Resource
    OldsterMapper oldsterMapper;
    @Resource
    YoungerMapper youngerMapper;
    @Resource
    RedisUtil_1 redisUtil_1;
    //长辈获取晚辈列表
    @Override
    public List<String> OldsterGetYoungerIdList(String oldsterId) {
        return oldsterMapper.SelectYoungerIdListByOldster(oldsterId);
    }


    //晚辈获取长辈列表
    @Override
    public List<String> YoungerGetOldsterIdList(String youngerId) {
        return youngerMapper.SelectOldsterIdListByYounger(youngerId);
    }


    //晚辈获取长辈位置信息
    @Override
    public RestObject YoungerGetOldsterGPS(List<String> list,String UserId) {
        Younger younger = youngerMapper.GetYounger(UserId);
        if (younger==null){
            return new RestObject<>("401","用户信息错误",null);
        }
        ArrayList<GPSInformation> gpsInformationArrayList = new ArrayList<>();
        for (String oldsterId: list) {
            GPSInformation gpsInformation = (GPSInformation) redisUtil_1.get(oldsterId);
            if (gpsInformation==null){
                gpsInformationArrayList.add(new GPSInformation(oldsterId,"0","0"));
                continue;
            }
            gpsInformationArrayList.add(gpsInformation);
        }
        return new RestObject<List<GPSInformation>>("200","长辈信息获取成功",gpsInformationArrayList);
    }


    //长辈写入自己的位置信息
    @Override
    public boolean OldsterSetGPS(String GPS, String oldsterId) {
        try {
            //判断是否是个长辈
            if (!redisUtil_1.hasKey(oldsterId)){
                Oldster oldster = oldsterMapper.GetOldster(oldsterId);
                if (oldster==null){
                    return false;
                }
            }
            //获取位置数据
            JSONObject parse = JSONObject.parse(GPS);
//        Longitude;//经度
//        Latitude;//纬度
            String longitude = parse.getString("longitude");
            String latitude = parse.getString("latitude");
            if (latitude==null||longitude==null){
                return false;
            }
            GPSInformation rObj = (GPSInformation) redisUtil_1.get(oldsterId);
            //判断是否存在
            if (rObj!=null &&rObj.getLatitude()!=null && rObj.getLongitude()!=null && (longitude.equals(rObj.getLongitude()) && latitude.equals(rObj.getLatitude()))){
                return true;
            }
            //创建数据对象
            GPSInformation gpsInformation = new GPSInformation(oldsterId, longitude, latitude);
            //缓存存储
            boolean SetBoolean = redisUtil_1.set(oldsterId,gpsInformation);
            System.out.println(SetBoolean);
            return SetBoolean;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //长辈获取自身位置信息
    @Override
    public RestObject OldsterGetMyGPS(String oldsterId) {
        Oldster oldster = oldsterMapper.GetOldster(oldsterId);
        if (oldster==null){
            return new RestObject<>("401","用户信息错误",null);
        }
        GPSInformation gpsInformation = (GPSInformation) redisUtil_1.get(oldsterId);
        if (gpsInformation==null){
            return new RestObject<GPSInformation>("200","长辈信息获取成功",new GPSInformation(oldsterId,"0","0"));
        }
        return new RestObject<GPSInformation>("200","长辈信息获取成功",gpsInformation);
    }

}
