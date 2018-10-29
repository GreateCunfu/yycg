package com.yycg.system.annotion;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 18:11 on 12/03/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface Myppers {
    String value() default "";
}
