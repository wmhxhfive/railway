package com.railway.wm.controller;

import com.railway.wm.dao.UserRepository;
import com.railway.wm.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<User> queryAll(){
        List<User> list = new ArrayList<User>();
        list = userRepository.findAll();
        return list;
    }

}
