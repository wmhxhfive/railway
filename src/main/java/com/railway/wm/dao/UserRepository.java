package com.railway.wm.dao;


import com.railway.wm.domain.UserInfoDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description:
 * date: 2017/3/15 16:28
 */
public interface UserRepository extends JpaRepository<UserInfoDao, Long> {

    UserInfoDao findByNameAndPassword(String userName,String passWord);

    UserInfoDao findByNameEquals(String userName);

}