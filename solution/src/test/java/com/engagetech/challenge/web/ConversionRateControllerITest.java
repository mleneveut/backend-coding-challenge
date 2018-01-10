package com.engagetech.challenge.web;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ConversionRateControllerITest extends ResourceBase {

    @Test
    public void should_get_conversion_rate() throws Exception {
        mockMvc.perform(
                get("/app/conversion-rates/euro"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists());
    }
}
