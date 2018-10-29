package com.yycg.system.process.exception;

import com.yycg.system.process.context.Config;
import com.yycg.system.process.result.ExceptionResultInfo;
import com.yycg.system.process.result.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description: 全局异常处理器,负责统一处理系统抛出的异常
 * @Date Created in 14:50 on 13/03/2018.
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    Logger LOGGER= LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Autowired
    private MappingJackson2HttpMessageConverter jsonMessageConverter;

    /**
     * 复写接口的resolveException
     * @param request
     * @param response
     * @param handler 当前拦截的handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //1. 抛出异常
        LOGGER.info(ExceptionUtils.getRootCauseMessage(ex));

        //2.获取处理handler对应的方法信息
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //判断返回返回值类型是否String,不是就是json 类型或http 协议接结果类型
        Type returnType = method.getGenericReturnType();
        String typeName = returnType.getTypeName();
        System.out.println(typeName);
        ModelAndView returnMv=null;
        //判断是否是string 或ModelAndView
        if (StringUtils.equalsIgnoreCase(Config.STRING_PACKAGE_PATH,typeName)||StringUtils.equalsIgnoreCase(Config.MODELANDVIEW_PACKAGE_PATH,typeName)){
            //进行异常信息解析
            returnMv=new ModelAndView();
            ExceptionResultInfo exceptionResultInfo=this.exceptionResolver(ex);
            int messageCode = exceptionResultInfo.getResultInfo().getMessageCode();
            if(messageCode==106){
                returnMv.setViewName(Config.LOGIN_PAGE);
            }else{

                returnMv.setViewName(Config.ERROR_PAGE);
            }

            returnMv.addObject("excptionInfo",exceptionResultInfo.getResultInfo());

        }else if(StringUtils.contains(typeName,"ResponseEntity")){
            // 返回值类型:
            //org.springframework.http.ResponseEntity<com.yycg.system.pojo.po.UserInfo>
           // typeName >>> org.springframework.http.ResponseEntity<com.yycg.system.pojo.po.UserInfo>
            returnMv=excptionConvertJson(request,response,handler,ex);
        }else {
            returnMv=excptionConvertJson(request,response,handler,ex);
        }


        return returnMv;
    }

    private ExceptionResultInfo exceptionResolver(Exception ex) {
        ResultInfo resultInfo=null;
        if (ex instanceof  ExceptionResultInfo){
            resultInfo=((ExceptionResultInfo)ex).getResultInfo();
        }else{
            resultInfo=new ResultInfo();
            resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            resultInfo.setMessage("未知异常");
            resultInfo.setMessageCode(110);
        }

        return  new ExceptionResultInfo(resultInfo);
    }

    private ModelAndView excptionConvertJson(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView modelAndView = new ModelAndView();
        //异常信息解析
        ExceptionResultInfo exceptionResultInfo = this.exceptionResolver(ex);
        //定义http 消息输出对象
        HttpOutputMessage httpOutputMessage = new ServletServerHttpResponse(response);

        try {
            jsonMessageConverter.write(exceptionResultInfo, MediaType.APPLICATION_JSON_UTF8,httpOutputMessage);
        } catch (IOException e) {
           LOGGER.error("excptionConvertJson >> e ====> {}", ExceptionUtils.getRootCauseMessage(e));
        }


        return modelAndView;
    }
}
