package org.nure.fintracker.model.dto.transaction;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class IncomeDto {

    private UUID userId;
    private UUID categoryId;
    private LocalDate date;
    private BigDecimal amount;
    private String description;

}
