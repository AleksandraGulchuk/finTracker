package org.nure.fintracker.controller;

import org.nure.fintracker.model.dto.user.UserLoginDto;
import org.nure.fintracker.model.dto.user.UserSetupDto;
import org.nure.fintracker.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
@CrossOrigin
@Validated
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/signup")
    public UUID register(@RequestBody UserSetupDto user) {
        return service.create(user);
    }

    @PostMapping("/login")
    public UUID login(@RequestBody UserLoginDto user) {
        return service.login(user);
    }


}
