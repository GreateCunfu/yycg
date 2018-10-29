package com.yycg.system.controller;

import com.yycg.system.pojo.po.UserInfo;
import com.yycg.system.process.result.DataGridResultInfo;
import com.yycg.system.process.result.ResultInfo;
import com.yycg.system.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @version 3.0
 * @Author :History.GreatMan.Mao
 * @Description:
 * @Date Created in 14:39 on 12/03/2018.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("queryuser")
    public String queryuser() throws  Exception{
        return "base/user/queryuser";
    }
  @RequestMapping("adduser")
    public String adduser() throws  Exception{
        return "base/user/adduser";
    }

    @RequestMapping("query_list")
    public @ResponseBody DataGridResultInfo query_list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                  @RequestParam(value = "rows",defaultValue = "10") Integer rows){
           DataGridResultInfo resultInfo=new DataGridResultInfo();

        try {
            DataGridResultInfo dataGridResultInfo = this.userService.selectUserPageList(page, rows);
            if (dataGridResultInfo!=null){
                resultInfo=dataGridResultInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       return  resultInfo;
    }

    @RequestMapping(value = "saveUser",method = RequestMethod.POST)
    public @ResponseBody ResultInfo saveUser(UserInfo user) throws  Exception{
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setType(ResultInfo.TYPE_RESULT_SUCCESS);
        resultInfo.setMessageCode(2000);
        resultInfo.setMessage("操作成功");
        this.userService.saveUser(user);

      //  SubmitResultInfo result = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
        return resultInfo;

    }

   @RequestMapping(value = "query_user/{userId}",method = RequestMethod.GET)
    public ResponseEntity<UserInfo> queryUserByUserId(@PathVariable("userId") String userId)throws Exception{
       UserInfo userInfo= this.userService.queryUserByUserId(userId);
       return  ResponseEntity.ok(userInfo);

   }


}
