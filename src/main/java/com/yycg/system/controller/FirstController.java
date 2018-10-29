package com.yycg.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 14:18 on 12/03/2018.
 */
@Controller
public class FirstController {
    @RequestMapping("/first")
    public String gotoFirst() throws  Exception{
        return  "/base/first";
    }

    @RequestMapping("welcome")
    public String gotoWelcome() throws  Exception{
        return "/base/welcome";
    }

}
