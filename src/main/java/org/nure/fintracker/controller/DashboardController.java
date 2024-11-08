package org.nure.fintracker.controller;

import lombok.AllArgsConstructor;
import org.nure.fintracker.dto.DashboardDto;
import org.nure.fintracker.servise.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardController {

    @Autowired
    private final DashboardService service;

    @GetMapping("/{id}")
    public DashboardDto read(@PathVariable(name = "id") UUID id) {
        return service.getDashboard(id);
    }



}
