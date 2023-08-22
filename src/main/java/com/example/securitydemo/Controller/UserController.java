package com.example.securitydemo.Controller;


import com.example.securitydemo.POJO.User;
import com.example.securitydemo.Repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController{

    UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("")
    @ResponseBody
    public List<User> getCustomers(){
        return userRepo.findAll();
    }
}
