package com.engagetech.challenge.web;


import com.engagetech.challenge.dto.ExpenseDto;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExpensesControllerITest extends ResourceBase {

    @Test
    @Sql
    public void should_get_all_expenses() throws Exception {
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, ExpenseDto.class);

        MvcResult mvcResult = mockMvc.perform(
                get("/app/expenses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        List<ExpenseDto> list = mapper.readValue(mvcResult.getResponse().getContentAsString(), type);
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getId()).isEqualTo(1);
        assertThat(list.get(0).getAmount()).isEqualTo(12.34);
        assertThat(list.get(0).getDate()).isEqualTo(LocalDate.of(2018, 1, 1));
        assertThat(list.get(0).getReason()).isEqualTo("my reasons");
        assertThat(list.get(1).getId()).isEqualTo(2);
        assertThat(list.get(1).getAmount()).isEqualTo(17);
        assertThat(list.get(1).getDate()).isEqualTo(LocalDate.of(2017, 10, 24));
        assertThat(list.get(1).getReason()).isEqualTo("plane tickets");
    }

    @Test
    public void should_create() throws Exception {
        ExpenseDto dto = ExpenseDto.builder()
                .amount(100.34)
                .date(LocalDate.of(2017, 11, 24))
                .reason("train tickets")
                .build();

        MvcResult mvcResult = mockMvc.perform(
                post("/app/expenses")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.amount").value("100.34"))
                .andExpect(jsonPath("$.date").value("24/11/2017"))
                .andExpect(jsonPath("$.reason").value("train tickets"))
                .andReturn();

        ExpenseDto createdDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), ExpenseDto.class);

        mockMvc.perform(
                get("/app/expenses/" + createdDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(String.valueOf(createdDto.getId())))
                .andExpect(jsonPath("$.amount").value("100.34"))
                .andExpect(jsonPath("$.date").value("24/11/2017"))
                .andExpect(jsonPath("$.reason").value("train tickets"));
    }

    @Test
    public void should_fail_get_wrong_id() throws Exception {
        mockMvc.perform(
                get("/app/expenses/1234"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("description").value("The expense 1234 can not be found"));
    }
}
