package com.engagetech.challenge.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel
public class Rate {
    @ApiModelProperty(value = "The base currency")
    private String base;

    @ApiModelProperty(value = "The date of the rate")
    private LocalDate date;

    @ApiModelProperty(value = "The rates by target currency")
    private Map<String, String> rates;
}
