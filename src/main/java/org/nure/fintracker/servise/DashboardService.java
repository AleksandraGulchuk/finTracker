package org.nure.fintracker.servise;

import org.nure.fintracker.mapper.TransactionMapper;
import org.nure.fintracker.model.dto.dashboard.DashboardDto;
import org.nure.fintracker.model.dto.transaction.TransactionDto;
import org.nure.fintracker.model.entity.Expense;
import org.nure.fintracker.model.entity.Income;
import org.nure.fintracker.repository.ExpenseRepository;
import org.nure.fintracker.repository.IncomeRepository;
import org.nure.fintracker.util.SummaryCalculator;
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
    private ExpenseRepository expenseRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private TransactionMapper transactionMapper;


    public DashboardDto getDashboard(UUID id) {
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
                .balanceHistory(SummaryCalculator.getBalanceHistory(incomeDtos, expenseDtos))
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


}
