package com.micro.controller;

import com.micro.config.JwtProvider;
import com.micro.model.User;
import com.micro.repo.UserRepo;
import com.micro.request.LoginRequest;
import com.micro.response.AuthResponse;
import com.micro.service.CustomerUserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomerUserServiceImpl customerUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private JwtProvider jwtProvider;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        String email = user.getEmail();
        String password = user.getPassword();
        String role = user.getRole();
        String fullName = user.getFullName();

        User isEmailExisted = userRepo.findByEmail(email);
        if(isEmailExisted!=null){
            throw new Exception("Email is already existed Please try another email");
        }
//        create new User
        User newUser = new User();
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        User savedUser = userRepo.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        AuthResponse auth = new AuthResponse();
        auth.setJwt(token);
        auth.setMessage("Register success");
        auth.setStatus(true);
        return new ResponseEntity<>(auth, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("login Sucess");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse,HttpStatus.OK);

    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customerUserService.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username or Password");

        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
