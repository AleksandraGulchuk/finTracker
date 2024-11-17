package org.nure.fintracker.mapper;

import org.nure.fintracker.model.dto.transaction.TransactionDto;
import org.nure.fintracker.model.dto.transaction.IncomeDto;
import org.nure.fintracker.model.entity.Expense;
import org.nure.fintracker.model.entity.Income;
import org.nure.fintracker.model.entity.IncomeCategory;
import org.nure.fintracker.model.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDto expenseToDto(Expense expense) {
        return TransactionDto.builder()
                .id(expense.getId())
                .type("expense")
                .category(expense.getExpenseCategory().getName())
                .date(expense.getDate())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .build();
    }

    public TransactionDto incomeToDto(Income income) {
        return TransactionDto.builder()
                .id(income.getId())
                .type("income")
                .category(income.getIncomeCategory().getName())
                .date(income.getDate())
                .amount(income.getAmount())
                .description(income.getDescription())
                .build();
    }

    public Income dtoToIncome(IncomeDto dto) {
        return Income.builder()
                .userAccount(UserAccount.builder().id(dto.getUserId()).build())
                .incomeCategory(IncomeCategory.builder().id(dto.getCategoryId()).build())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .description(dto.getDescription())
                .build();
    }

}
