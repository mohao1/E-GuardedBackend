package com.ling.ap.Stoker;

import com.alibaba.fastjson2.JSONObject;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Pojo.ToDo.GPSInformation;
import com.ling.ap.Service.ServiceInterface.GPSService;
import com.ling.ap.Utils.MyJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//设置连接地址
@ServerEndpoint(value = "/GPSService")
@Component
public class WebStoker {


    private static final ConcurrentHashMap<String,Session> webSocketMap=new ConcurrentHashMap<>();//用户列表Map
    private static final ConcurrentHashMap<String,List<String>> Y_List=new ConcurrentHashMap<>();//长辈映射晚辈
    private static final ConcurrentHashMap<String,List<String>> O_List=new ConcurrentHashMap<>();//晚辈映射长辈
    private static final ConcurrentHashMap<String,String> Session_Id_List=new ConcurrentHashMap<>();//映射Map


    private static MyJwt myJwt;

    private static GPSService gpsService;


    // 注入的时候，给类的 service 注入
    @Autowired
    public void setMyJwt(MyJwt myJwt) {
        WebStoker.myJwt=myJwt;
    }
    @Autowired
    public void setGpsService(GPSService gpsService) {
        WebStoker.gpsService=gpsService;
    }



    //建立链接
    @OnOpen
    public synchronized void onOpen (Session session) {
        try {
            //获取信息
            Map<String, List<String>> map = session.getRequestParameterMap();
            String JWT = map.get("UserId").get(0);
            //分隔字符
            String[] List = JWT.split(" ");
            //获取字符
            String Token = List[List.length-1];
            boolean flag=UserIdToken(Token);//判断UserId是否合法
            String UserId=this.UserIdGet(Token);//解析UserId
            //判断UserId是否合法
            if (flag) {//合法处理方法
                //判断用户是否已经连接
                if(UserBindSession(UserId)){
                    session.getBasicRemote().sendText("用户已经连接");
                    session.close();
                    return;
                }
                String type = map.get("type").get(0);
                //晚辈连接操作
                if (type != null && type.equals("Younger")) {
                    //存储用户的Session
                    webSocketMap.put(UserId, session);
                    //存储用户的sessionId映射
                    this.UserIdIsSessionId(session.getId(), UserId);
                    //存储晚辈长辈列表
                    List<String> list = gpsService.YoungerGetOldsterIdList(UserId);
                    this.O_List.put(UserId,list);
                    this.YoungerGetGPSSend(session);
                    return;
                }else if (type != null && type.equals("Oldster")){
                    //存储用户的Session
                    webSocketMap.put(UserId, session);
                    //存储用户的sessionId映射
                    this.UserIdIsSessionId(session.getId(), UserId);
                    //存储长辈晚辈列表
                    List<String> list = gpsService.OldsterGetYoungerIdList(UserId);
                    this.Y_List.put(UserId,list);
                    return;
                }else {
                    //关闭连接
                    session.close();
                    return;
                }

            }else {//不合法的处理方法
                //关闭连接
                session.close();
                return;
            }
        }catch (Exception e){//错误捕获
            e.printStackTrace();
            return;
        }
    }

    //错误处理
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error occurred for client " + session.getId() + ": " + throwable.getMessage());
    }

    //关闭链接
    @OnClose
    public synchronized void onClose (Session session){
        String UserId = Session_Id_List.get(session.getId());
        if (UserId==null){
            return;
        }
        webSocketMap.remove(UserId);//移除链接
        O_List.remove(UserId);//移除长辈列表
        Y_List.remove(UserId);//移除晚辈列表
        Session_Id_List.remove(session.getId());//移除sessionId映射
    }

    //接收数据
    /**
     * {
     *     "OldsterSetGPS":100
     *     "YoungerGetGPS":200,
     *     "OldsterGetGPS":300,
     * }
     * */

    @OnMessage
    public void  onMessage (String msg,Session session) {
        //数据处理
        JSONObject JS = (JSONObject) JSONObject.parse(msg);
        String type = JS.getObject("type", String.class);
        if (type ==null){
            this.TypeErrorSend(session);
        }
//        System.out.println(type);
        switch (type){
            case "100":{
                this.OldsterSetGPSSend(session,JS.getString("gps"));
                break;
            }
            case "200":{
                this.YoungerGetGPSSend(session);
                break;
            }
            case "300":{
                this.OldsterGetGPSSend(session);
                break;
            }
            default:{
                this.TypeErrorSend(session);
                break;
            }
        }
    }


    //晚辈获取长辈信息
    public void YoungerGetGPSSend(Session session){
        try {
            String UserId = Session_Id_List.get(session.getId());
            List<String> list = O_List.get(UserId);
            if (list==null||list.isEmpty()){
                RestObject<String> object = new RestObject<>("200", "用户没有绑定老人信息",null);
                String json = JSONObject.toJSONString(object);
                session.getBasicRemote().sendText(json);
            }else {
                RestObject restObject = gpsService.YoungerGetOldsterGPS(list,UserId);
                String json = JSONObject.toJSONString(restObject);
                session.getBasicRemote().sendText(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //长辈获取信息
    public synchronized void OldsterGetGPSSend(Session session){
        try{
            String UserId = Session_Id_List.get(session.getId());
            if (UserId==null){
                RestObject<String> object = new RestObject<>("401", "用户信息错误",null);
                String json = JSONObject.toJSONString(object);
                session.getBasicRemote().sendText(json);
            }else {
                //查询长辈GPS的信息
                RestObject restObject = gpsService.OldsterGetMyGPS(UserId);
                String json = JSONObject.toJSONString(restObject);
                session.getBasicRemote().sendText(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //长辈存储信息
    public void OldsterSetGPSSend(Session session,String msg){
        try{
            String UserId = Session_Id_List.get(session.getId());
            //写入GPS的数据进入缓存
            boolean b = gpsService.OldsterSetGPS(msg, UserId);
            if (!b){
                RestObject<String> object = new RestObject<>("401", "位置数据缓存失败",null);
                String json = JSONObject.toJSONString(object);
                session.getBasicRemote().sendText(json);
                return;
            }
            List<String> list = Y_List.get(UserId);
            if (list==null||list.isEmpty()){
                RestObject<String> object = new RestObject<>("200", "用户没有绑定子女信息",null);
                String json = JSONObject.toJSONString(object);
                session.getBasicRemote().sendText(json);
            }else {
                JSONObject parse = JSONObject.parse(msg);
                String longitude = parse.getString("longitude");
                String latitude = parse.getString("latitude");
                GPSInformation gpsInformation = new GPSInformation(UserId,longitude,latitude);
                String jsonObject = JSONObject.toJSONString(gpsInformation);
                for (String Y_UserId:list) {
                        Session Y_Session = webSocketMap.get(Y_UserId);
                        //发送坐标信息
                    if (Y_Session!=null){
                        //发送数据
                        Y_Session.getBasicRemote().sendText(jsonObject);
                    }
                }
                RestObject<String> object = new RestObject<>("200", "用户定位信息更新成功",null);
                String json = JSONObject.toJSONString(object);
                session.getBasicRemote().sendText(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Type错误Send
    public void TypeErrorSend (Session session) {
        try {
            RestObject<String> object = new RestObject<>("401", "Type数据错误",null);
            String json = JSONObject.toJSONString(object);
            session.getBasicRemote().sendText(json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //设置用户session映射操作
    public void UserIdIsSessionId(String SessionId,String UserId){
        Session_Id_List.put(SessionId,UserId);
    }

    //判断Id数据的正确性
    public boolean UserIdToken(String JWT){
        return myJwt.VerifyJwt(JWT);
    }
    //解析Id数据
    public String UserIdGet(String JWT){
        cn.hutool.json.JSONObject jsonObject = myJwt.GetJwtValue(JWT);
        String userId = jsonObject.getStr("UserId");
        return userId;
    }

    //判断用户是否已经连接
    public boolean UserBindSession(String UserId){
        return webSocketMap.get(UserId) != null;
    }

    //设置心跳检测函数
    public synchronized void SendPing(){
        if (webSocketMap.size()<=0){
            return;
        }
        webSocketMap.forEach((String id, Session session)->{
                    try{
                        if (webSocketMap.containsKey(id)){
//                            System.out.println("ping");
                            //ping操作
                            session.getBasicRemote().sendPing(StandardCharsets.UTF_8.encode("Ping").asReadOnlyBuffer());
                        }
                    }catch (Exception e){
                        //移除数据
                        String UserId = Session_Id_List.get(session.getId());
                        if (UserId==null){
                            return;
                        }
                        webSocketMap.remove(UserId);//移除链接
                        O_List.remove(UserId);//移除长辈列表
                        Y_List.remove(UserId);//移除晚辈列表
                        Session_Id_List.remove(session.getId());//移除sessionId映射
                    }
                }
        );
    }
}

