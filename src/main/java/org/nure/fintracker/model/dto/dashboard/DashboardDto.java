package org.nure.fintracker.model.dto.dashboard;

import lombok.Builder;
import lombok.Data;
import org.nure.fintracker.model.dto.transaction.TransactionDto;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardDto {

    private BigDecimal expenseAmount;
    private BigDecimal incomeAmount;
    private BigDecimal balance;
    private List<TransactionDto> transactions;

}
