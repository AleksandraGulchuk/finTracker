package org.nure.fintracker.servise;

import org.nure.fintracker.dto.DashboardDto;
import org.nure.fintracker.dto.TransactionDto;
import org.nure.fintracker.entity.Expense;
import org.nure.fintracker.entity.Income;
import org.nure.fintracker.exception.EntityNotFoundException;
import org.nure.fintracker.mapper.TransactionMapper;
import org.nure.fintracker.repository.ExpenseRepository;
import org.nure.fintracker.repository.IncomeRepository;
import org.nure.fintracker.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class DashboardService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private TransactionMapper transactionMapper;


    public DashboardDto getDashboard(UUID id) {
        checkUser(id);
        List<Expense> expenses = expenseRepository.findAllByUserAccountIdOrderByDateDesc(id);
        List<Income> incomes = incomeRepository.findAllByUserAccountIdOrderByDateDesc(id);

        BigDecimal expenseAmount = getExpenseAmount(expenses);
        BigDecimal incomeAmount = getIncomeAmount(incomes);
        BigDecimal balance = incomeAmount.subtract(expenseAmount);

        List<TransactionDto> expenseDtos = expenses.stream()
                .map(e -> transactionMapper.expenseToDto(e))
                .toList();
        List<TransactionDto> incomeDtos = incomes.stream()
                .map(i -> transactionMapper.incomeToDto(i))
                .toList();
        List<TransactionDto> transactions = Stream
                .concat(expenseDtos.stream(), incomeDtos.stream())
                .sorted(Comparator.comparing(TransactionDto::getDate).reversed())
                .toList();

        return DashboardDto.builder()
                .expenseAmount(expenseAmount)
                .incomeAmount(incomeAmount)
                .balance(balance)
                .transactions(transactions)
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
