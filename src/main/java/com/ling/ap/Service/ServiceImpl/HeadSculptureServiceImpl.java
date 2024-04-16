package com.ling.ap.Service.ServiceImpl;

import cn.hutool.core.io.resource.ResourceUtil;
import com.ling.ap.Pojo.RestObject;
import com.ling.ap.Service.ServiceInterface.HeadSculptureService;
import com.ling.ap.Utils.Const;
import com.ling.ap.Utils.TencentCOSUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Service
public class HeadSculptureServiceImpl implements HeadSculptureService {

    @Resource
    TencentCOSUtil tencentCOSUtil;


    //初始化用户的头像
    @Override
    public boolean OldsterInitUserHeadSculpture(String userId,int sex) {
        File pubs=null;
        try {
            String path;
            if (sex==1){
                path ="public/head_sculpture/oldster1.jpg";
            }else {
                path="public/head_sculpture/oldster0.jpg";
            }

            InputStream resourceAsStream = ResourceUtil.getStream(path);
            pubs = File.createTempFile("pubs", ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(pubs);
            byte[] bytes = new byte[1024];
            int length;
            while((length=resourceAsStream.read(bytes)) != -1){
                fileOutputStream.write(bytes,0,length);
            }

            resourceAsStream.close();
            fileOutputStream.close();

            File file = new File(pubs.getAbsolutePath());


            //取出文件格式后缀
            String name = file.getName();
            String substring = name.substring(name.lastIndexOf("."));

            String fileName = Const.COS_PATH +userId+substring;
            tencentCOSUtil.UpLoadFile(file,fileName);
            pubs.delete();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            if (pubs!=null){
                pubs.delete();
            }
            return false;
        }
    }

    @Override
    public boolean YoungerInitUserHeadSculpture(String userId, int sex) {
        File pubs=null;
        try {
            String path;
            if (sex==1){
                path="public/head_sculpture/younger1.jpg";
            }else {
                path="public/head_sculpture/younger0.jpg";
            }
            pubs = File.createTempFile("pubs", ".jpg");
            InputStream resourceAsStream = ResourceUtil.getStream(path);
            FileOutputStream fileOutputStream = new FileOutputStream(pubs);
            byte[] bytes = new byte[1024];
            int length;
            while((length=resourceAsStream.read(bytes)) != -1){
                fileOutputStream.write(bytes,0,length);
            }

            resourceAsStream.close();
            fileOutputStream.close();

            File file = new File(pubs.getAbsolutePath());

            //取出文件格式后缀
            String name = file.getName();
            String substring = name.substring(name.lastIndexOf("."));
            //传输数据
            String fileName = Const.COS_PATH +userId+substring;
            tencentCOSUtil.UpLoadFile(file,fileName);
            pubs.delete();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            if (pubs!=null){
                pubs.delete();
            }
            return false;
        }
    }


    //修改用户的头像
    @Override
    public RestObject UpDataUserHeadSculpture(String userId, MultipartFile file) {
        try {
            //取出文件格式后缀
            String name = file.getOriginalFilename();
            String substring = name.substring(name.lastIndexOf("."));
            //MultipartFile转换成功File
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            file.transferTo(localFile);
            //传输数据
            String fileName= Const.COS_PATH +userId+substring;
            tencentCOSUtil.UpLoadFile(localFile,fileName);
            return new RestObject("200","上传成功",userId);
        }catch (Exception e){
            e.printStackTrace();
            return new RestObject("403","上传过程发生错误",e);
        }
    }


    //根据Id查询用户的头像
    @Override
    public RestObject SelectUserHeadSculpture(String userId) {
        String fileName= Const.COS_PATH +userId+".jpg";
        String url = tencentCOSUtil.GetFile(fileName);
        return new RestObject("200","获取成功",url);
    }
}
