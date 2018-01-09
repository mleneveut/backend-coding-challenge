package com.engagetech.challenge.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.SQLException;

/**
 * Mother class for all component tests, using MockMVC.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Slf4j
@ActiveProfiles("test")
public class ResourceBase {

    private static final String SCHEMA = "codingchallenge";

    @Autowired(required = false)
    protected WebApplicationContext context;
    protected MockMvc mockMvc;
    protected ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void init() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mapper.registerModule(new JavaTimeModule());
    }

    // Clean database after each resource test
    @After
    public void truncateAll() throws SQLException {
        jdbcTemplate.queryForList("SELECT table_name FROM information_schema.tables where table_schema='" + SCHEMA + "'").stream()
                .map(e -> e.get("table_name"))
                .filter(tableName -> !"schema_version".equals(tableName)) // "schema_version" is a Flyway table !
                .peek(tableName -> log.info("Truncate table " + tableName))
                .forEach(tableName -> jdbcTemplate.execute("TRUNCATE TABLE " + tableName));
    }

    @Test
    public void should_start_application_context() {
    }
}