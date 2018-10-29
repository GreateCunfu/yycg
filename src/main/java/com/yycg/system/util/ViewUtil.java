package com.yycg.system.util;

import com.yycg.system.process.context.Config;
import com.yycg.system.process.result.SubmitResultInfo;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 18:00 on 14/03/2018.
 */
public class ViewUtil {

        /**
         * 通用的成功jsp页面文件名
         */
        public static final String SUCCESS = "success";
        /**
         * 通用的失败jsp页面文件名
         */
        public static final String ERROR = "error";

        /**
         * 返回base模块jsp路径方法，其它模块可照此添加
         *
         * @param page
         *            jsp文件名
         * @return
         */
        public static String toBase(String page) {
            return Config.PAGE_PATH_BASE + page;
        }

        /**
         * 返回业务模块jsp路径方法，其它模块可照此添加
         *
         * @param page
         *            jsp文件名
         * @return
         */
        public static String toBusiness(String page) {
            return Config.PAGE_PATH_BUSINESS + page;
        }

        /**
         * action返回成功页面方法
         *
         * @return
         */
        public static String toSuccess() {
            return Config.PAGE_PATH_BASE + SUCCESS;
        }

        /**
         * action返回操作失败页面方法
         *
         * @return
         */
        public static String toError() {
            return Config.PAGE_PATH_BASE + ERROR;
        }


        /**
         * action以redirect方式重定向到自定义url
         *
         * @param url
         * @return
         */
        public static String redirect(String url) {
            return "redirect:" + url;
        }

        /**
         * action以forward方式定向到自定义页面
         *
         * @param url
         * @return
         */
        public static String forward(String url) {
            return "forward:" + url;
        }

        /**
         * 返回成功提示（转json）
         */
        public static SubmitResultInfo toSuccessJson(){
            return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
        }



}