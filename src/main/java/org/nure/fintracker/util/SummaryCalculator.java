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

}
