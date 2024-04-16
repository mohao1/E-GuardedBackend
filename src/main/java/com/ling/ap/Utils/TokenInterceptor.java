package com.ling.ap.Utils;

import cn.hutool.json.JSONUtil;
import com.ling.ap.Pojo.RestObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//Token的拦截器

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    MyJwt myJwt;

    //进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //放行OPTIONS请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            //放行OPTIONS请求
            return true;
        }
        String authorization = request.getHeader("Authorization");
        if (authorization==null){
            //设置跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            //设置响应标头
            response.setHeader("Cache-Control", "no-cache");
            //设置响应编码
            response.setCharacterEncoding("UTF-8");
            //设置响应数据类型
            response.setContentType("application/json");
            //返回数据（202码令牌错误）
            RestObject<Object> object = new RestObject<>("202", "Token错误", "Token error");
            //返回数据
            response.getWriter().print(JSONUtil.parse(object));
            //拦截请求
            return false;
        }
        String[] List = authorization.split(" ");
        String Token = List[List.length-1];

        if (myJwt.VerifyJwt(Token)){
            //解析Token的数据
            Object userId = myJwt.GetJwtValue(Token).get("UserId");
            //放行请求
            request.setAttribute("JWT",userId);
            return true;
        }else {
            //设置跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            //设置响应标头
            response.setHeader("Cache-Control", "no-cache");
            //设置响应编码
            response.setCharacterEncoding("UTF-8");
            //设置响应数据类型
            response.setContentType("application/json");
            //返回数据（202码令牌错误）
            RestObject<Object> object = new RestObject<>("202", "Token错误", "Token error");
            //返回数据
            response.getWriter().print(JSONUtil.parse(object));
            //拦截请求
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
