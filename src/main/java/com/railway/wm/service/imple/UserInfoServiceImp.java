package com.railway.wm.service.imple;

import com.railway.wm.dao.UserRepository;
import com.railway.wm.domain.UserInfoDao;
import com.railway.wm.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userInfoService")
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    UserRepository userRepository;
    @Override
    public boolean findUserInfoByName(String name) {
        UserInfoDao byNameEquals = userRepository.findByNameEquals(name);
        if(null==byNameEquals) return false;
        return true;
    }

    @Override
    public UserInfoDao findUserInfoByNameAndPassword(String name, String password) {
        UserInfoDao byNameAAndPassword = userRepository.findByNameAndPassword(name, password);

        return byNameAAndPassword;
    }

    @Override
    public UserInfoDao saveUserInfo(String name, String password) {
        UserInfoDao userInfoDao=new UserInfoDao();
        userInfoDao.setName(name);
        userInfoDao.setPassword(password);
        UserInfoDao save = userRepository.save(userInfoDao);
        if(save!=null && save.getId()>0){
            return save;
        }else{
            return save;
        }
    }
}