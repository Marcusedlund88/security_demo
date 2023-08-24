package com.example.securitydemo.Service;

import com.example.securitydemo.POJO.User;
import com.example.securitydemo.Repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void createUser(String username, String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String saltedPass = bCryptPasswordEncoder.encode(password);

        userRepo.save(new User(username, saltedPass));
    }

}
