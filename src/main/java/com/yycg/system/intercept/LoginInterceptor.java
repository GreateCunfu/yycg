package com.yycg.system.intercept;

import com.yycg.system.process.context.Config;
import com.yycg.system.util.ResultUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 18:24 on 14/03/2018.
 */
public class LoginInterceptor implements HandlerInterceptor {
      private  static  final  String[] urlArr={"/login","/loginsubmit"} ;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //检验是否公开地址
        String requestURI = request.getRequestURI();
        System.out.println("requestURL ==> "+requestURI);
        List<String> list = Arrays.asList(urlArr);
        if (list.contains(requestURI)){
            return true;
        }
        //校验用户是否登录
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user!=null){
            return  true;
        }
       // request.getRequestDispatcher("/WEB-INF/jsp/base/login.jsp").forward(request,response);
        //防止session 过期,返回json 提交操作 处理,抛出异常交给异常处理器处理
        ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,106,null));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
