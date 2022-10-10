package com.example.controllers;

import com.example.entities.User;
import com.example.props.JWTLogin;
import com.example.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    protected final UserService userService;


    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody JWTLogin jwtLogin) {
        return userService.auth(jwtLogin);
    }

    @GetMapping("/getUsers")
    public ResponseEntity getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/getUserById/{uid}")
    public ResponseEntity getUserById(@PathVariable int uid) {
        return userService.getUserById(uid);
    }
}
