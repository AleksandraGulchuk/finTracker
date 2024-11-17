package org.nure.fintracker.mapper;

import org.nure.fintracker.model.dto.category.CategoryDto;
import org.nure.fintracker.model.entity.ExpenseCategory;
import org.nure.fintracker.model.entity.IncomeCategory;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto expenseCategoryToDto(ExpenseCategory category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public CategoryDto incomeCategoryToDto(IncomeCategory category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
