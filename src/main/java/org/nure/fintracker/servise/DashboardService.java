package org.nure.fintracker.servise;

import org.nure.fintracker.dto.DashboardDto;
import org.nure.fintracker.dto.RowDto;
import org.nure.fintracker.entity.Expense;
import org.nure.fintracker.entity.Income;
import org.nure.fintracker.exception.EntityNotFoundException;
import org.nure.fintracker.mapper.RowMapper;
import org.nure.fintracker.repository.ExpenseRepository;
import org.nure.fintracker.repository.IncomeRepository;
import org.nure.fintracker.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private RowMapper rowMapper;


    public DashboardDto getDashboard(UUID id) {
        checkUser(id);
        List<Expense> exceptions = expenseRepository.findAllByUserAccountIdOrderByDateDesc(id);
        List<Income> incomes = incomeRepository.findAllByUserAccountIdOrderByDateDesc(id);

        BigDecimal expenseAmount = getExpenseAmount(exceptions);
        BigDecimal incomeAmount = getIncomeAmount(incomes);
        BigDecimal balance = incomeAmount.subtract(expenseAmount);

        List<RowDto> expenseDtos = exceptions.stream().map(e -> rowMapper.expenseToDto(e)).toList();
        List<RowDto> incomeDtos = incomes.stream().map(i -> rowMapper.incomeToDto(i)).toList();

        return DashboardDto.builder()
                .expenseAmount(expenseAmount)
                .incomeAmount(incomeAmount)
                .balance(balance)
                .expenses(expenseDtos)
                .incomes(incomeDtos)
                .build();
    }

    private static BigDecimal getIncomeAmount(List<Income> incomes) {
        return incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

    private static BigDecimal getExpenseAmount(List<Expense> exceptions) {
        return exceptions.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

    private void checkUser(UUID id) {
        userAccountRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
    }
}
