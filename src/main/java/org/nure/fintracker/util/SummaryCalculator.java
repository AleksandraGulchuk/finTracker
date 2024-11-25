package org.nure.fintracker.util;

import org.nure.fintracker.model.dto.transaction.TransactionDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryCalculator {

    private static final int PERIOD = 6;

    public static Map<String, BigDecimal> getBalanceHistory(List<TransactionDto> incomes, List<TransactionDto> expenses) {
        Map<String, BigDecimal> incomesSummary = getTransactionsSummary(incomes);
        Map<String, BigDecimal> expensesSummary = getTransactionsSummary(expenses);
        return calculateBalances(incomesSummary, expensesSummary);
    }

    public static Map<String, BigDecimal> getTransactionsSummary(List<TransactionDto> transactions) {
        LocalDate currentDate = LocalDate.now();
        Month current = currentDate.getMonth();
        Month past = current.minus(PERIOD);
        List<TransactionDto> transactionDtos = transactions.stream()
                .filter(t -> t.getDate().getMonth().getValue() > past.getValue())
                .toList();

        return bindMap(transactionDtos);
    }

    private static Map<String, BigDecimal> bindMap(List<TransactionDto> transactions) {
        Map<String, BigDecimal> bindMap = new HashMap<>();
        for (TransactionDto transaction : transactions) {
            String month = transaction.getDate().getMonth().toString();
            if (bindMap.containsKey(month)) {
                BigDecimal total = bindMap.get(month).add(transaction.getAmount());
                bindMap.put(month, total);
            } else {
                bindMap.put(month, transaction.getAmount());
            }
        }
        return bindMap;
    }

    private static Map<String, BigDecimal> calculateBalances(Map<String, BigDecimal> incomesSummary, Map<String, BigDecimal> expenseSummary) {
        Map<String, BigDecimal> balanceHistory = new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        Month current = currentDate.getMonth();
        Month past = current.minus(PERIOD);
        for (int i = 1; i < 7; i++) {
            String month = past.plus(i).toString();
            if (incomesSummary.containsKey(month)) {
                if (expenseSummary.containsKey(month)) {
                    balanceHistory.put(month, incomesSummary.get(month).subtract(expenseSummary.get(month)));
                } else {
                    balanceHistory.put(month, incomesSummary.get(month));
                }
            } else {
                if (expenseSummary.containsKey(month)) {
                    balanceHistory.put(month, new BigDecimal("0.00").subtract(expenseSummary.get(month)));
                } else {
                    balanceHistory.put(month, new BigDecimal("0.00"));
                }
            }
        }
        return balanceHistory;
    }

}
