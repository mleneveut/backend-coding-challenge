package com.engagetech.challenge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "coding-challenge")
@Data
public class ChallengeProperties {

    private double vat;

    private String conversionUrl;
}
