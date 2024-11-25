package org.nure.fintracker.model.dto.dashboard;

import lombok.Builder;
import lombok.Data;
import org.nure.fintracker.model.dto.transaction.TransactionDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardDto {

    private BigDecimal expenseAmount;
    private BigDecimal incomeAmount;
    private BigDecimal balance;
    private List<TransactionDto> transactions;
    private Map<String, BigDecimal> balanceHistory;

}
