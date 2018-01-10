package com.engagetech.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel
public class ExpenseDto {
    @ApiModelProperty(value = "The expense's id")
    private long id;

    @ApiModelProperty(value = "The expense's date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @ApiModelProperty(value = "The expense's amount")
    private Double amount;

    @ApiModelProperty(value = "The expense's VAT")
    private Double vat;

    @ApiModelProperty(value = "The expense's reason")
    private String reason;

}
