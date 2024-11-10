package org.nure.fintracker.mapper;

import org.nure.fintracker.dto.TransactionDto;
import org.nure.fintracker.entity.Expense;
import org.nure.fintracker.entity.Income;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDto expenseToDto(Expense expense) {
        return TransactionDto.builder()
                .type("expense")
                .category(expense.getExpenseCategory().getName())
                .date(expense.getDate())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .build();
    }

    public TransactionDto incomeToDto(Income income) {
        return TransactionDto.builder()
                .type("income")
                .category(income.getIncomeCategory().getName())
                .date(income.getDate())
                .amount(income.getAmount())
                .description(income.getDescription())
                .build();
    }

}
