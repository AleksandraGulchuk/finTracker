package org.nure.fintracker.dto.income;

import lombok.Builder;
import lombok.Data;
import org.nure.fintracker.dto.TransactionDto;
import org.nure.fintracker.dto.category.CategoryDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class SummaryDto {

    private List<CategoryDto> categories;
    private List<TransactionDto> transactions;
    private Map<String, BigDecimal> summary;

}
