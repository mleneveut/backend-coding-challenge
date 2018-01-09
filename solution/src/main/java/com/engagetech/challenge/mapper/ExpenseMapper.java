package com.engagetech.challenge.mapper;

import com.engagetech.challenge.dto.ExpenseDto;
import com.engagetech.challenge.entity.Expense;
import org.mapstruct.Mapper;
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
    ExpenseDto toExpenseDto(Expense source);
}