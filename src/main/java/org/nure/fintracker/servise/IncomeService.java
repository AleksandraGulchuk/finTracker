package org.nure.fintracker.servise;

import lombok.extern.slf4j.Slf4j;
import org.nure.fintracker.model.dto.transaction.TransactionDto;
import org.nure.fintracker.model.dto.category.CategoryDto;
import org.nure.fintracker.model.dto.transaction.IncomeDto;
import org.nure.fintracker.model.dto.transaction.SummaryDto;
import org.nure.fintracker.model.entity.Income;
import org.nure.fintracker.mapper.CategoryMapper;
import org.nure.fintracker.mapper.TransactionMapper;
import org.nure.fintracker.repository.IncomeCategoryRepository;
import org.nure.fintracker.repository.IncomeRepository;
import org.nure.fintracker.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
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
        return SummaryDto.builder()
                .transactions(transactions)
                .categories(getCategories())
                .summary(getSummary(transactions))
                .build();
    }

    public UUID createIncome(IncomeDto dto) {
        Income income = transactionMapper.dtoToIncome(dto);
        return incomeRepository.save(income).getId();
    }

    public void deleteIncome(UUID incomeId){
        incomeRepository.deleteById(incomeId);
    }

    private Map<String, BigDecimal> getSummary(List<TransactionDto> transactions) {
        LocalDate currentDate = LocalDate.now();
        Month current = currentDate.getMonth(); //NOV
        Month past = current.minus(6);//June
        //TODO: DELETE AFTER TEST
//        LocalDate date = LocalDate.of(currentDate.getYear(), past, 1);
//        LocalDate date1 = LocalDate.of(currentDate.getYear(), past, 1);
//        log.info(" ++++++++++++++++++++++++++++++++++++++++++ ");
//        log.info("Month current" + current);
//        log.info("MONTH " + past);
//        log.info("DATE " + date);
//        log.info("isAfter" + currentDate.isAfter(date));
//        log.info("equals" + currentDate.equals(date1));

        List<TransactionDto> transactionDtos = transactions.stream()
                .filter(t -> t.getDate().getMonth().getValue() > past.getValue())
                .toList();

        return bindMap(transactionDtos);
    }

    private Map<String, BigDecimal> bindMap(List<TransactionDto> transactions) {
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
