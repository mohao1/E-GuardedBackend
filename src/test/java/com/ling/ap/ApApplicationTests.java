package com.ling.ap;

import com.ling.ap.Dao.OldsterMapper;
import com.ling.ap.Service.ServiceInterface.ActivityRecordService;
import com.ling.ap.Service.ServiceInterface.HealthScoreService;
import com.ling.ap.Utils.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.Date;


//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApApplicationTests {

    @Resource
    Character_recognition character_recognition;


//    @Test
//    void contextLoads() throws IOException {
//        System.out.println(character_recognition.GetPlayUrl("你好", "小王"));
//
//    }

//    @Resource
//    MyJwt myJwt;
//
//    @Test
//    void JwtTest(){
//        Map<String,Object> map=new HashMap();
//
//        map.put("id","1234567");
//        map.put("name","小王");
//        map.put("age","20");
//        map.put("sex",1);
//        String Token=myJwt.GetJwt(map);
//        System.out.println(Token);
//
//        System.out.println(myJwt.VerifyJwt(Token));
//
//        System.out.println(myJwt.GetJwtValue(Token));
//
//
//    }
//
//    @Test
//    void splice(){
//        String authorization="Bearer helloWorld";
//        System.out.println(authorization.split(" ")[1]);
//    }
//
//    @Resource
//    UserLoginMapper userLoginMapper;
//
//    @Test
//    void SetUserLogin(){
//        userLoginMapper.SetUserLogin(
//                new UserLogin(
//                        "10","123456","18575842930","2418514837@qq.com",null,"某某小区")
//        );
////        System.out.println(userLoginMapper.PhoneGetUserLogin("1857584293"));
//    }
//
//    @Resource
//    RegistrationService userLoginService;
//
//
//    @Test
//    void UserRegistrationTest(){
//        System.out.println(userLoginService.OldsterRegistration(
//                new Oldster(
//                        "15698",
//                        null,
//                        null,
//                        "老王",
//                        66,
//                        0
//                ),
//                        new UserLogin(
//                                "15698",
//                                "123456",
//                                "18575842939",
//                                "2418514837@qq.com",
//                                null,
//                                "某某小区"
//                        )
//                )
//        );
//    }
//
//
//
//    @Test
//    void LoginPhoneTest(){
//        System.out.println(userLoginMapper.PhoneGetInformation("18575842933"));
//        System.out.println(userLoginMapper.EmailGetInformation("2689645613@qq.com"));
//
//    }
//    @Resource
//    YoungerMapper youngerMapper;
//
//    @Resource
//    OldsterMapper oldsterMapper;
//    @Test
//    void HomeTest(){
////        System.out.println(youngerMapper.GetOldsterList("10"));
//        System.out.println(youngerMapper.GetHome("10"));
//    }
//
//    @Test
//    void Emergency(){
////        System.out.println(oldsterMapper.GetEmergencyContactsList("0b23161013a24548afaab06d2af26774"));
//        System.out.println(oldsterMapper.GetMaxEmergencyContactsList("0b23161013a24548afaab06d2af26774"));
//    }
//
//    @Resource
//    AlibabaSendSms alibabaSendSms;
//    @Test
//    void Message() throws Exception {
////        Map<String,String> map=new HashMap();
////        map.put("signName","阿里云短信测试");
////        map.put("phone","15012916690");
////        map.put("templateCode","SMS_154950909");
////        map.put("templateParam","{\"code\":\"1234\"}");
////        alibabaSendSms.SendSms(map);
//
//        Map<String,String> map=new HashMap();
//        map.put("signName","[\"lingmohao\",\"lingmohao\"]");
//        map.put("phone","[\"18575842930\",\"15012916690\"]");
//        map.put("templateCode","SMS_273735375");
//        map.put("templateParam","[{\"code\":\"123456\"},{\"code\":\"123456\"}]");
//        System.out.println(alibabaSendSms.BatchSms(map));
//    }
//    @Resource
//    CodeUtil codeUtil;
//    @Test
//    void Code(){
//        System.out.println(codeUtil.GetCode());
//    }
//
//    @Resource
//    HomeMapper homeMapper;
//    @Test
//    void HomeNum(){
//        //改变人数
////        System.out.println(homeMapper.UpdateNum(2, "12356"));
////        //查询人数
////        System.out.println(homeMapper.SelectNum("12356"));
//
//        System.out.println(oldsterMapper.GetHome("5689"));
//    }
//
//
//    @Test
//    void HomeId(){
//        homeMapper.DeleteYoungerBindHome("555","");
//    }
//
//    @Resource
//    EmergencySosService emergencySosService;
//    @Test
//    void EmergencySos(){
////        System.out.println(emergencySosService.YoungerList("0b23161013a24548afaab06d2af26774"));
//
//        emergencySosService.MessageForHelp("1");
//    }
//
//
//
//    @Resource
//    MemorandumService memorandumService;
//
//    @Test
//    void MemorandumTest(){
//        //插入
////        memorandumService.AddTask(
////                new Memorandum(
////                        "2",
////                        "1",
////                        "测试",
////                        "测试",
////                        new Date(),
////                        new Date(),
////                        new Date(),
////                        0)
////        );
//        //查询全部
////        System.out.println(memorandumService.SelectTaskList("1"));
//        //确认s完成
////        System.out.println(memorandumService.ConfirmTask("1", "1"));
//        //长辈取消确认完成任务
////        System.out.println(memorandumService.UnConfirmTask("1", "1"));
//
//        //长辈查询没有完成任务
////        System.out.println(memorandumService.SelectUnfinishedTaskList("1"));
//        //长辈查询已经完成了的任务
////        System.out.println(memorandumService.SelectUnfinishedTaskList("1"));
//        //长辈删除自己的备忘录任务(虚拟删除)
////        memorandumService.DeleteTake("1","1");
//        //长辈恢复自己的备忘录任务(虚拟恢复)
////        System.out.println(memorandumService.RecoveryTake("1", "1"));
//        //查询回收站的任务列表
////        System.out.println(memorandumService.SelectDeleteTaskList("1"));
//        //删除回收站的任务
////        System.out.println(memorandumService.ClearTask("1", "1"));
//        //长辈修改任务内容
////        System.out.println(memorandumService.ModifyTask(
////                new Memorandum(
////                        "2",
////                        "1",
////                        "测试2",
////                        "测试2",
////                        new Date(),
////                        new Date(),
////                        new Date(),
////                        0)
////        ));
//    }
//
//    @Resource
//    RegistrationService registrationService;
//    @Test
//    void rTest(){
//        //System.out.println(registrationService.AcquisitionVerificationCode("18575842930"));
//         TimedCache<String, String> RegistrationCodeCache = CacheUtil.newTimedCache(300000);
//        System.out.println(RegistrationCodeCache.get("123456"));
//    }
//
//    @Resource
//    HomeMemberListServiceImpl homeMemberListService;
//    @Test
//    void homemetest(){
//        System.out.println(homeMemberListService.QueryOldster("0fcc84e579e94a5093947cb57d1aa451"));
////        System.out.println(homeMemberListService.QueryYounger("c003adb673d64436a9b4a0900b099282"));
//    }
//
//    @Resource
//    CalendarUtil calendarUtil;
//    @Test
//    void dateTest(){
//        calendarUtil.calendar();
//    }
//
//    @Test
//    void TestOY(){
//        System.out.println(youngerMapper.SelectYoungerBindOldster(
//                "0fcc84e579e94a5093947cb57d1aa451",
//                "c003adb673d64436a9b4a0900b099282"
//        ));
//    }
//    @Resource
//    HealthMapper healthMapper;
//    @Resource
//    RedisUtil redisUtil;
//
//    @Test
//    void Health(){
////        healthMapper.InsertHealth(
////                new Health(
////                        "1",
////                        "2",
////                        new Date(),
////                        5000,
////                        "20",
////                        "20"
////                )
////        );
////        redisUtil.set("测试","测试数据");
//
////        Date date = new Date();
////
////        Calendar instance = Calendar.getInstance();
////        instance.setTime(date);
////        int year = instance.get(Calendar.YEAR);//获取年份
////        int month = instance.get(Calendar.MONTH);//获取月份
////        int dates = instance.get(Calendar.DATE);//获取日
////        System.out.println(year+","+month+","+dates);
//
//        Date date=new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_MONTH, -11);
//        int year = calendar.get(Calendar.YEAR);//获取年份
//        int month = calendar.get(Calendar.MONTH)+1;//获取月份
//        int dates = calendar.get(Calendar.DATE);//获取日
//        String str=year+"-"+month+"-"+dates;
//
//    }
//
//    @Test
//    void TimeTest(){
//        Time time = new Time(23,50,30);
//        System.out.println(time);
//    }
//
//    @Resource
//    DrugService drugService;
//    @Test
//    void drugTest(){
////        System.out.println(drugService.OldsterInsertDrug(
////                new Drug(
////                        "1",
////                        "c003adb673d64436a9b4a0900b099282",
////                        "123456",
////                        "测试tittle",
////                        "测试content",
////                        new Date(),
////                        new Time(24, 10, 20),
////                        0,
////                        ""
////                ),
////                new DrugNum(
////                        "c003adb673d64436a9b4a0900b099282",
////                        "123456",
////                        "阿莫西林",
////                        50,
////                        "使用方法测试"
////                )
////        ));
//
////        System.out.println(drugService.OldsterSelectDrug("c003adb673d64436a9b4a0900b099282"));
////        System.out.println(drugService.OldsterConfirmDrug(
////                "c003adb673d64436a9b4a0900b099282",
////                "1",
////                2,
////                "123456"
////        ));
////        drugService.OldsterDeleteDrug("1");
////        drugService.OldsterUpdateDrug(new Drug(
////                        "1",
////                        "c003adb673d64436a9b4a0900b099282",
////                        "123456",
////                        "测试tittle5",
////                        "测试content5",
////                        new Date(),
////                        new Time(10, 10, 20),
////                        0,
////                        ""
////        ));
////        drugService.OldsterUpdateDrugNum(new DrugNum(
////                        "c003adb673d64436a9b4a0900b099282",
////                        "123456",
////                        "阿莫西林",
////                        50,
////                        "使用方法测试"
////        ),"1");
//
////        System.out.println(drugService.YoungerSelectDrug("c003adb673d64436a9b4a0900b099282", "0fcc84e579e94a5093947cb57d1aa451"));
//    }
//
//    @Resource
//    TencentCOSUtil tencentCOSUtil;
//    @Test
//    void Centir(){
////        File localFile = new File("C:\\Users\\mohao\\Desktop\\1111.jpg");
//        String fileName = "HeadSculpture/olds.png";
//        String path = ClassUtils.getDefaultClassLoader().getResource("head_sculpture/oldster-0.png").getPath();
//        File file = new File(path);
//        tencentCOSUtil.UpLoadFile(file,fileName);
////        tencentCOSUtil.GetFile("HeadSculpture/123456789.jpg");
//
////        File file = new File("/head_sculpture/oldster-0");
////        System.out.println(file.getName());
//    }
//
//    @Resource
//    HeadSculptureService headSculptureService;
//    @Test
//    void Cent(){
////        String fileName="123456.jpg";
////        String substring = fileName.substring(fileName.lastIndexOf("."));
////        System.out.println(substring);
//
//        headSculptureService.OldsterInitUserHeadSculpture("123456",0);
//    }
//
//
//    @Resource
//    GreetingsService greetingsService;
//    @Test
//    void Greetings(){
//        greetingsService.GetGreetings();
//    }

//    @Resource
//    HeadSculptureService headSculptureService;
//    @Test
//    void jpeg(){
//        headSculptureService.OldsterInitUserHeadSculpture("12345688ss",0);
//        headSculptureService.YoungerInitUserHeadSculpture("55223365",1);
//
//    }

//    @Resource
//    Mp3Util mp3Util;
//    @Test
//    void mp3() throws IOException {
//       File pcm = File.createTempFile("pcm", ".wav");
//        System.out.println(pcm.getAbsolutePath());
//        mp3Util.convertMP32Pcm(new File("C:\\Users\\mohao\\Desktop\\15mp.mp3"),pcm.getAbsolutePath());
//        System.out.println(pcm.length());
//        System.out.println(character_recognition.SpeechToText(pcm.getAbsolutePath(), "123456"));
//    }

    @Test
    void mp3(){
        Time time = new Time(22, 50, 30);
        Time time1 = new Time(53430000);
        Date date = new Date();
        System.out.println(date.getTime());
    }

    @Resource
    HealthScoreService healthScoreService;
    @Resource
    UIDS uids;
    @Resource
    ActivityRecordService activityRecordService;
    @Resource
    TencentSendSms tencentSendSms;
    @Test
    void Take() throws Exception {
//        healthScoreService.UpdateHealthScore();
//        System.out.println(new Date().toString());

//        String p = uids.GetUIDS("p");
//
//        System.out.println(p);

//        activityRecordService.TakeActivityRecord();
        String[] phoneList= new String[1];
        String[] templateParamList={};
        phoneList[0]="13059374306";

        tencentSendSms.SendSms(phoneList, templateParamList,"123456", Const.T_SIGN_NAME,"1800034",Const.APP_KEY_ID);


    }
    @Resource
    RedisUtil redisUtil;
    @Resource
    RedisUtil_1 redisUtil_1;
    @Resource
    OldsterMapper oldsterMapper;
    @Test
    void  redisTest (){
        redisUtil_1.set("测试1","测试1");
        redisUtil.set("测试2","测试2");
//        System.out.println(oldsterMapper.SelectYoungerIdListByOldster("9d0ba7fa2cb74d358c980f2fdd07c9a7"));
    }

    @Test
    void Enum(){
    }

}
