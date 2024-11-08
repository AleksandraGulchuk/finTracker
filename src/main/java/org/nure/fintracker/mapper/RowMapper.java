package org.nure.fintracker.mapper;

import org.nure.fintracker.dto.RowDto;
import org.nure.fintracker.entity.Expense;
import org.nure.fintracker.entity.Income;
import org.springframework.stereotype.Component;

@Component
public class RowMapper {

    public RowDto expenseToDto(Expense expense) {
        return RowDto.builder()
                .date(expense.getDate())
                .category(expense.getExpenseCategory().getName())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .build();
    }

    public RowDto incomeToDto(Income income) {
        return RowDto.builder()
                .date(income.getDate())
                .category(income.getIncomeCategory().getName())
                .amount(income.getAmount())
                .description(income.getDescription())
                .build();
    }

}
