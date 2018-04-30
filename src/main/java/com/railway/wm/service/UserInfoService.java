package com.railway.wm.service;

import com.railway.wm.domain.UserInfoDao;

public interface UserInfoService {
    boolean findUserInfoByName(String name);
    UserInfoDao findUserInfoByNameAndPassword(String name,String password);
    UserInfoDao saveUserInfo(String name,String password);

}
