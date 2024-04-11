package com.micro.service;

import com.micro.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE",url = "http://localhost:8080/")
public interface UserService {


    @GetMapping("/api/user/profile")
    public UserDto getUser(@RequestHeader("Authorization") String jwt);
}
