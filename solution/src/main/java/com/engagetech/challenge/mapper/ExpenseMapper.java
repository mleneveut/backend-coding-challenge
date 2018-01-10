package com.engagetech.challenge.mapper;

import com.engagetech.challenge.dto.ExpenseDto;
import com.engagetech.challenge.entity.Expense;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseMapper {

    static ExpenseMapper instance() {
        return Mappers.getMapper(ExpenseMapper.class);
    }

    /*
     * ExpenseDto -> Expense
     */
    Expense toExpense(ExpenseDto source);

    /*
     * Expense -> NewsModelDto
     */
    @Mappings({
            @Mapping(target = "vat", ignore = true)
    })
    ExpenseDto toExpenseDto(Expense source, double vat);

    @AfterMapping
    default void fillVat(Expense source, @MappingTarget ExpenseDto result, double vat) {
        double ht = result.getAmount().doubleValue() / (1 + vat);
        result.setVat(ht * vat);
    }
}