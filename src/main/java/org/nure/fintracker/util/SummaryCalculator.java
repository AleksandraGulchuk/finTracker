package org.nure.fintracker.util;

import org.nure.fintracker.model.dto.transaction.TransactionDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        Month startPeriodMonth = getStartPeriodMonth();
        List<TransactionDto> transactionDtos = transactions.stream()
                .filter(t -> t.getDate().getMonth().getValue() > startPeriodMonth.getValue())
                .toList();
        return bindTransactionsSummaryMap(transactionDtos);
    }

    private static Map<String, BigDecimal> bindTransactionsSummaryMap(List<TransactionDto> transactions) {
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
        return fillEmptyMonths(bindMap);
    }

    private static Map<String, BigDecimal> fillEmptyMonths(Map<String, BigDecimal> transactionsSummary) {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        Month startPeriodMonth = getStartPeriodMonth();
        for (int i = 1; i < 7; i++) {
            String month = startPeriodMonth.plus(i).toString();
            if (transactionsSummary.containsKey(month)) {
                map.put(month, transactionsSummary.get(month));
            } else {
                map.put(month, new BigDecimal("0.00"));
            }
        }
        return map;
    }

    private static Map<String, BigDecimal> calculateBalances(Map<String, BigDecimal> incomesSummary, Map<String, BigDecimal> expenseSummary) {
        Map<String, BigDecimal> balanceHistory = new LinkedHashMap<>();
        Month startPeriodMonth = getStartPeriodMonth();
        for (int i = 1; i < 7; i++) {
            String month = startPeriodMonth.plus(i).toString();
            if (incomesSummary.containsKey(month) && expenseSummary.containsKey(month)) {
                balanceHistory.put(month, incomesSummary.get(month).subtract(expenseSummary.get(month)));
            } else if (incomesSummary.containsKey(month) && !expenseSummary.containsKey(month)) {
                balanceHistory.put(month, incomesSummary.get(month));
            } else if (!incomesSummary.containsKey(month) && expenseSummary.containsKey(month)) {
                balanceHistory.put(month, new BigDecimal("0.00").subtract(expenseSummary.get(month)));
            } else {
                balanceHistory.put(month, new BigDecimal("0.00"));
            }
        }
        return balanceHistory;
    }

    private static Month getStartPeriodMonth() {
        LocalDate currentDate = LocalDate.now();
        Month current = currentDate.getMonth();
        return current.minus(PERIOD);
    }

}
