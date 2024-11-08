package org.nure.fintracker.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardDto {

    private BigDecimal expenseAmount;
    private BigDecimal incomeAmount;
    private BigDecimal balance;

    List<RowDto> expenses;
    List<RowDto> incomes;

}
