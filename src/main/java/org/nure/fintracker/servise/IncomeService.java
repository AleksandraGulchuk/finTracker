package org.nure.fintracker.servise;

import lombok.extern.slf4j.Slf4j;
import org.nure.fintracker.mapper.CategoryMapper;
import org.nure.fintracker.mapper.TransactionMapper;
import org.nure.fintracker.model.dto.category.CategoryDto;
import org.nure.fintracker.model.dto.transaction.SummaryDto;
import org.nure.fintracker.model.dto.transaction.TransactionDto;
import org.nure.fintracker.model.dto.transaction.TransactionSaveDto;
import org.nure.fintracker.model.entity.Income;
import org.nure.fintracker.repository.IncomeCategoryRepository;
import org.nure.fintracker.repository.IncomeRepository;
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
public class IncomeService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;
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
        Income income = transactionMapper.dtoToIncome(dto);
        return incomeRepository.save(income).getId();
    }

    public void deleteIncome(UUID incomeId) {
        incomeRepository.deleteById(incomeId);
    }

    private List<CategoryDto> getCategories() {
        return incomeCategoryRepository
                .findAll()
                .stream()
                .map(c -> categoryMapper.incomeCategoryToDto(c))
                .toList();
    }

    private List<TransactionDto> getTransactions(UUID id) {
        return incomeRepository
                .findAllByUserAccountIdOrderByDateDesc(id)
                .stream()
                .map(i -> transactionMapper.incomeToDto(i))
                .toList();
    }

}
