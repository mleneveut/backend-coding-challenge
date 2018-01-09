package com.engagetech.challenge.mapper;

import com.engagetech.challenge.dto.ExpenseDto;
import com.engagetech.challenge.entity.Expense;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseMapperTest {

    @Test
    public void test_mapping_dto_dao() {
        ExpenseDto dto = ExpenseDto.builder()
                .id(1)
                .amount(12.34)
                .date(LocalDate.of(2017, 10, 25))
                .reason("train tickets")
                .build();

        Expense dao = ExpenseMapper.instance().toExpense(dto);

        assertThat(dao).isNotNull();
        assertThat(dao.getId()).isEqualTo(dto.getId());
        assertThat(dao.getDate()).isEqualTo(dto.getDate());
        assertThat(dao.getAmount()).isEqualTo(dto.getAmount());
        assertThat(dao.getReason()).isEqualTo(dto.getReason());
    }

    @Test
    public void test_mapping_dao_dto() {
        Expense dao = Expense.builder()
                .id(1)
                .amount(12.34)
                .date(LocalDate.of(2017, 10, 25))
                .reason("train tickets")
                .build();

        ExpenseDto dto = ExpenseMapper.instance().toExpenseDto(dao);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(dao.getId());
        assertThat(dto.getDate()).isEqualTo(dao.getDate());
        assertThat(dto.getAmount()).isEqualTo(dao.getAmount());
        assertThat(dto.getReason()).isEqualTo(dao.getReason());
    }
}
