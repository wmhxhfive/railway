package com.railway.wm.controller;

import com.railway.wm.domain.UserInfoDao;
import com.railway.wm.request.UserRequest;
import com.railway.wm.response.BaseResponse;
import com.railway.wm.service.UserInfoService;
import com.railway.wm.util.CurrentUserUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/sys")
public class EmployeeController {
    @Resource
    UserInfoService userInfoService;

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/register")
    public BaseResponse register(@RequestBody UserRequest userRequest){
        String username=userRequest.getName();
        String password=userRequest.getPassword();
        BaseResponse baseResponse=new BaseResponse();
        if(userInfoService.findUserInfoByName(username)){
            baseResponse.setRet("1");
            baseResponse.setMessage("用户名重复");
            baseResponse.setCode("001");
            return baseResponse;
        }
        UserInfoDao result=userInfoService.saveUserInfo(username,password);
        CurrentUserUtils.getInstance().setUser(result);
        baseResponse.setRet("0");
        baseResponse.setMessage("注册成功");
        return baseResponse;
    }

    /**
     * 用户名校验
     * @param username
     * @return
     */
    @RequestMapping("/check")
    public BaseResponse loginVerify(@NotBlank(message = "用户名不能为空")
                                        @RequestParam(name = "username") String username){
        BaseResponse baseResponse=new BaseResponse();
        if(userInfoService.findUserInfoByName(username)){
            baseResponse.setRet("1");
            baseResponse.setMessage("用户名重复");
            baseResponse.setCode("001");
        }else {
            baseResponse.setRet("0");
            baseResponse.setMessage("用户可以注册");
        }
        return baseResponse;
    }


    /**
     * 用户名校验
     * @param
     * @return
     */
    @RequestMapping("/login")
    public BaseResponse loginVerify(@RequestBody UserRequest userRequest){
        BaseResponse baseResponse=new BaseResponse();
        UserInfoDao userInfoDao=userInfoService.findUserInfoByNameAndPassword(userRequest.getName(),userRequest.getPassword());
        if(userInfoDao!=null){
            baseResponse.setRet("0");
            baseResponse.setMessage("登陆成功");
            CurrentUserUtils.getInstance().setUser(userInfoDao);
        }else {
            baseResponse.setRet("1");
            baseResponse.setMessage("用户名密码错误");
            baseResponse.setCode("002");
        }
        return baseResponse;
    }


}
