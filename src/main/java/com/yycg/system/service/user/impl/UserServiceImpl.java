package com.yycg.system.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yycg.system.mapper.UserInfoMapper;
import com.yycg.system.pojo.po.UserInfo;
import com.yycg.system.process.result.DataGridResultInfo;
import com.yycg.system.process.result.ExceptionResultInfo;
import com.yycg.system.process.result.ResultInfo;
import com.yycg.system.service.user.IUserService;
import com.yycg.system.util.ResultUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 15:01 on 12/03/2018.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserInfoMapper userMapper;

    @Override
    public DataGridResultInfo selectUserPageList(Integer page, Integer rows) throws  Exception{
        DataGridResultInfo resultInfo=null;
        PageHelper.startPage(page,rows);

        List<UserInfo> userInfos = this.userMapper.selectUserPageList();

        if (CollectionUtils.isNotEmpty(userInfos)){
            resultInfo=new DataGridResultInfo();
            PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfos);
            resultInfo.setRows(pageInfo.getList());
            resultInfo.setTotal(pageInfo.getTotal());
        }


        return resultInfo;
    }

    @Override
    public void saveUser(UserInfo user) throws  Exception{
        String userId = user.getUserId();
        if (StringUtils.isEmpty(userId)){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            resultInfo.setMessage("用户Id 为空");
            resultInfo.setMessageCode(4000);
            throw new ExceptionResultInfo(resultInfo);
        }
        //校验账号是否重复
       UserInfo userInfo= this.userMapper.selectUserByUserId(userId);
        if(userInfo!=null){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            resultInfo.setMessage("userId 已存在");
            resultInfo.setMessageCode(4001);
            throw new ExceptionResultInfo(resultInfo);
        }
//       int m= 1/0;
        Date date = new Date();
        user.setCreateTime(date);
        user.setLastUpdate(date);
        this.userMapper.insert(user);
    }

    @Override
    public UserInfo queryUserByUserId(String userId)throws  Exception {
        if (StringUtils.isEmpty(userId)){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            resultInfo.setMessage("userId is Null");
            resultInfo.setMessageCode(4002);
            throw new ExceptionResultInfo(resultInfo);
        }

        UserInfo userInfo = this.userMapper.selectUserByUserId(userId);
        if (userInfo==null){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            resultInfo.setMessage("the user is not exsist!");
            resultInfo.setMessageCode(4003);
            throw new ExceptionResultInfo(resultInfo);
        }
        return userInfo;
    }

    @Override
    public UserInfo queryUserByNameAndPasswd(String userId, String password) throws Exception{
        UserInfo userInfo=this.userMapper.selectUserByUserNameAndPasswd(userId,password);
        if (userInfo==null){
            ResultUtil.throwExcepion(ResultUtil.createFail("用户不存在",101,null));
        }
        return userInfo;
    }
}
