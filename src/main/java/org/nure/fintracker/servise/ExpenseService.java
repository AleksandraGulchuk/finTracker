package org.nure.fintracker.servise;

import lombok.extern.slf4j.Slf4j;
import org.nure.fintracker.mapper.CategoryMapper;
import org.nure.fintracker.mapper.TransactionMapper;
import org.nure.fintracker.model.dto.category.CategoryDto;
import org.nure.fintracker.model.dto.transaction.SummaryDto;
import org.nure.fintracker.model.dto.transaction.TransactionDto;
import org.nure.fintracker.model.dto.transaction.TransactionSaveDto;
import org.nure.fintracker.model.entity.Expense;
import org.nure.fintracker.repository.ExpenseCategoryRepository;
import org.nure.fintracker.repository.ExpenseRepository;
import org.nure.fintracker.repository.UserAccountRepository;
import org.nure.fintracker.util.SummaryCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ExpenseService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    CategoryMapper categoryMapper;


    public SummaryDto getSummary(UUID id) {
        List<TransactionDto> transactions = getTransactions(id);
        Map<String, BigDecimal> summary = SummaryCalculator.getTransactionsSummary(transactions);
        return SummaryDto.builder()
                .transactions(transactions)
                .categories(getCategories())
                .summary(summary)
                .build();
    }

    public UUID createIncome(TransactionSaveDto dto) {
        Expense expense = transactionMapper.dtoToExpense(dto);
        return expenseRepository.save(expense).getId();
    }

    public void deleteIncome(UUID incomeId) {
        expenseRepository.deleteById(incomeId);
    }

    private List<CategoryDto> getCategories() {
        return expenseCategoryRepository
                .findAll()
                .stream()
                .map(c -> categoryMapper.expenseCategoryToDto(c))
                .toList();
    }

    private List<TransactionDto> getTransactions(UUID id) {
        return expenseRepository
                .findAllByUserAccountIdOrderByDateDesc(id)
                .stream()
                .map(i -> transactionMapper.expenseToDto(i))
                .toList();
    }

}
