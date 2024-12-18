package org.nure.fintracker.controller;

import org.nure.fintracker.model.dto.dashboard.DashboardDto;
import org.nure.fintracker.servise.DashboardService;
import org.nure.fintracker.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public DashboardDto read(@PathVariable(name = "id") UUID id) {
        userService.checkUser(id);
        return dashboardService.getDashboard(id);
    }


}
