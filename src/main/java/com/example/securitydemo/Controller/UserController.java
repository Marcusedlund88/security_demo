package com.example.securitydemo.Controller;


import com.example.securitydemo.POJO.User;
import com.example.securitydemo.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController{

    private final UserRepo userRepo;

    private final DataSource dataSource;

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


    @GetMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password){

        String outputMessage = "";
        String sql = "SELECT username, password FROM user WHERE username = '"+username+"' AND password = '"+ password+"'";
        log.info(sql);
        if(isValid(sql)){
            log.info("logged in");
            isLoggedIn = true;
            outputMessage = "You are logged in, happy exploring";
        }
        else{
            isLoggedIn = false;
            log.info("Wrong credentials");
            outputMessage = "Wrong credentials, please try again";
        }
        return outputMessage;
    }

    /*
        Functional login endpoint, not best practice,
        uses encryption and are safe from SQL injections
    */
    @GetMapping("/loginFixed")
    @ResponseBody
    public String loginFixed(@RequestParam String username, @RequestParam String password) {

        String outputMessage = "";
        try {
            if (isValidFixed(username, password)) {
                log.info("logged in");
                isLoggedIn = true;
                outputMessage = "You are logged in, happy exploring";
            } else {
                isLoggedIn = false;
                log.info("Invalid username or password");
                outputMessage = "Invalid username or password";
            }
        }
        catch (Exception e){
            log.error("An error occurred during login", e);
            return "An error occurred during login";
        }
        return outputMessage;
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
            log.error("Unexpected error occurred during authorization");
        }
        return false;
    }

    public boolean isValidFixed(String username, String password){

        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            List<User> users = userRepo.findByUsername(username);

            for (User user : users) {
                if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                    return true;
                }
                ;
            }
        }
        catch(Exception e){
            log.error("An error occurred during Authorization");
        }
        return false;
    }
}
