package org.nure.fintracker.model.dto.transaction;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class TransactionDto {

    private UUID id;
    private String type;
    private String category;
    private LocalDate date;
    private BigDecimal amount;
    private String description;

}
