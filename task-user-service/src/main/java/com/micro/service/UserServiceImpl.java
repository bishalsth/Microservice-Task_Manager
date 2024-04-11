package com.micro.service;

import com.micro.config.JwtProvider;
import com.micro.model.User;
import com.micro.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;
    @Override
    public User getUserProfile(String jwt) {
        String email = JwtProvider.getEmailFrowJwtToken(jwt);

        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getAllUser() {
       return userRepo.findAll();
    }
}
