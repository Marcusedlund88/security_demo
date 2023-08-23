package com.example.securitydemo.Controller;


import com.example.securitydemo.POJO.User;
import com.example.securitydemo.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController{

    private UserRepo userRepo;

    private DataSource dataSource;

    private boolean isLoggedIn = false;

    @Autowired
    public UserController(UserRepo userRepo, DataSource dataSource) {
        this.userRepo = userRepo;
        this.dataSource = dataSource;
    }


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("")
    @ResponseBody
    public List<User> getUsers(){
        if(isLoggedIn) {
            return userRepo.findAll();
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable long id) {
        userRepo.deleteById(id);
    }

    @GetMapping("/getById")
    public void login(@RequestParam String id){
        String sql = "select username, password from users where id = " + id;
    }

    @GetMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password){
        userRepo.findAll();
        log.info(username + password);
        String sql = "SELECT username, password FROM user WHERE username = '"+username+"' AND password = '"+password+"'";
        log.info(sql);
        if(isValid(sql)){
            log.info("logged in");
            isLoggedIn = true;
        }
        else{
            isLoggedIn = false;
            log.info("Wrong credentials");
        }

    }

    private boolean isValid(String sql){
        try{

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(sql);

            if(queryResult.size()>0) {
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
