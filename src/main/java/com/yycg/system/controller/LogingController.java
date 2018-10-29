package com.yycg.system.controller;

import com.yycg.system.pojo.po.UserInfo;
import com.yycg.system.process.result.SubmitResultInfo;
import com.yycg.system.service.user.IUserService;
import com.yycg.system.util.ResultUtil;
import com.yycg.system.util.ViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 22:08 on 13/03/2018.
 */
@Controller
public class LogingController {

    @Autowired
    private IUserService userService;

    @RequestMapping("login")
    public String login(){
        return "base/login";
    }


    @RequestMapping("loginsubmit")
    public @ResponseBody  SubmitResultInfo loginSubmit(String userId, String password, HttpSession session) throws  Exception{

        UserInfo user=this.userService.queryUserByNameAndPasswd(userId,password);
        session.setAttribute("user",user);

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess("登录成功",107,new Object[]{user.getUserName()}));
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return ViewUtil.redirect("first");
    }

}
