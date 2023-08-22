package com.example.securitydemo.Repository;

import com.example.securitydemo.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
