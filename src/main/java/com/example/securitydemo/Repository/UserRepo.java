package com.example.securitydemo.Repository;

import com.example.securitydemo.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByUsername(String username);
}
