package org.nure.fintracker.controller;

import lombok.AllArgsConstructor;
import org.nure.fintracker.dto.user.UserDto;
import org.nure.fintracker.dto.user.UserLoginDto;
import org.nure.fintracker.servise.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/signup")
    public UUID register(@RequestBody UserDto user) {
        return service.create(user);
    }

    @PostMapping("/login")
    public UUID login(@RequestBody UserLoginDto user) {
        return service.login(user);
    }


}
