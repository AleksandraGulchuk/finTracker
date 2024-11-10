package org.nure.fintracker.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionDto {

    private String type;
    private String category;
    private LocalDate date;
    private BigDecimal amount;
    private String description;

}
