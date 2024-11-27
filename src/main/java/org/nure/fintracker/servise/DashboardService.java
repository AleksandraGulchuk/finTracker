package org.nure.fintracker.servise;

import org.nure.fintracker.mapper.TransactionMapper;
import org.nure.fintracker.model.dto.dashboard.DashboardDto;
import org.nure.fintracker.model.dto.transaction.TransactionDto;
import org.nure.fintracker.repository.ExpenseRepository;
import org.nure.fintracker.repository.IncomeRepository;
import org.nure.fintracker.util.SummaryCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
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
        Month pastMonth = LocalDate.now().getMonth().minus(1);
        Month startPeriodMonth = LocalDate.now().getMonth().minus(6);

        List<TransactionDto> expenses = transactionMapper
                .expensesToDto(expenseRepository.findAllByUserAccountIdOrderByDateDesc(id));
        List<TransactionDto> expensesForHalfYear = SummaryCalculator
                .getTransactionsForPeriod(expenses, startPeriodMonth);
        List<TransactionDto> expensesForMonth = SummaryCalculator
                .getTransactionsForPeriod(expenses, pastMonth);

        List<TransactionDto> incomes = transactionMapper
                .incomesToDto(incomeRepository.findAllByUserAccountIdOrderByDateDesc(id));
        List<TransactionDto> incomesForHalfYear = SummaryCalculator
                .getTransactionsForPeriod(incomes, startPeriodMonth);
        List<TransactionDto> incomesForMonth = SummaryCalculator
                .getTransactionsForPeriod(incomes, pastMonth);

        List<TransactionDto> transactions = Stream
                .concat(expensesForMonth.stream(), incomesForMonth.stream())
                .sorted(Comparator.comparing(TransactionDto::getDate).reversed())
                .toList();

        BigDecimal expenseAmount = getTransactionAmount(expensesForMonth);
        BigDecimal incomeAmount = getTransactionAmount(incomesForMonth);
        BigDecimal balance = incomeAmount.subtract(expenseAmount);

        return DashboardDto.builder()
                .expenseAmount(expenseAmount)
                .incomeAmount(incomeAmount)
                .balance(balance)
                .transactions(transactions)
                .balanceHistory(SummaryCalculator.getBalanceHistory(incomesForHalfYear, expensesForHalfYear))
                .build();
    }

    private static BigDecimal getTransactionAmount(List<TransactionDto> transactions) {
        return transactions.stream()
                .map(TransactionDto::getAmount)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

}
