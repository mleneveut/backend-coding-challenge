package com.engagetech.challenge.web;

import com.engagetech.challenge.config.ChallengeProperties;
import com.engagetech.challenge.dto.Rate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(
        value = "/app/conversion-rates",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Api(
        value = "Conversion rate API",
        description = "API to handle the conversion rates between currencies",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class ConversionRateController {

    private final ChallengeProperties properties;
    private RestTemplate restTemplate = new RestTemplate();

    @ApiOperation(value = "Get the euro - pound conversion rate")
    @GetMapping("/euro")
    @Cacheable("euroConversionRate")
    public ResponseEntity<Double> getEuroRate() {
        Rate rate = restTemplate.getForObject(properties.getConversionUrl(), Rate.class);
        return ResponseEntity.ok(Double.valueOf(rate.getRates().get("GBP")));
    }
}
