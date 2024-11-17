package org.nure.fintracker.controller;

import org.nure.fintracker.model.dto.transaction.IncomeDto;
import org.nure.fintracker.model.dto.transaction.SummaryDto;
import org.nure.fintracker.servise.IncomeService;
import org.nure.fintracker.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/incomes")
@CrossOrigin
@Validated
public class IncomeController {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public SummaryDto getSummary(@PathVariable(name = "id") UUID id) {
        userService.checkUser(id);
        return incomeService.getSummary(id);
    }

    @PostMapping("/create")
    public UUID login(@RequestBody IncomeDto income) {
        userService.checkUser(income.getUserId());
        return incomeService.createIncome(income);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id){
        incomeService.deleteIncome(id);
    }

}
