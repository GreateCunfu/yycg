package com.yycg.system.service.user;

import com.yycg.system.pojo.po.UserInfo;
import com.yycg.system.process.result.DataGridResultInfo;
import org.springframework.http.ResponseEntity;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 15:01 on 12/03/2018.
 */
public interface IUserService {


    public DataGridResultInfo selectUserPageList(Integer page, Integer rows) throws  Exception ;

    void saveUser(UserInfo user) throws  Exception;


    UserInfo queryUserByUserId(String userId) throws  Exception;

    UserInfo queryUserByNameAndPasswd(String userId, String password)throws  Exception;
}
