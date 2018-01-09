package com.engagetech.challenge.web;

import com.engagetech.challenge.dto.ExpenseDto;
import com.engagetech.challenge.exceptions.ResourceNotFoundException;
import com.engagetech.challenge.exceptions.UnprocessableEntityException;
import com.engagetech.challenge.mapper.ExpenseMapper;
import com.engagetech.challenge.service.ExpenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * Expenses controller.
 */
@RestController
@RequestMapping(
        value = "/app/expenses",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Api(
        value = "Expenses API",
        description = "API to handle the expenses",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class ExpensesController {

    private final ExpenseService expenseService;

    @ApiOperation(value = "Get all the expenses")
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAll() {
        List<ExpenseDto> dtos = expenseService.findAll()
                .stream()
                .map(e -> ExpenseMapper.instance().toExpenseDto(e))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @ApiOperation(value = "Get an expense by id")
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getById(@PathVariable long id) {
        return ofNullable(expenseService.getById(id))
                .map(e -> ExpenseMapper.instance().toExpenseDto(e))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The expense %d can not be found", id)));
    }

    @ApiOperation(value = "Create an expense")
    @PostMapping
    public ResponseEntity<ExpenseDto> create(
            @ApiParam(value = "The expense's data to create", required = true)
            @Valid @RequestBody ExpenseDto dto) {

        return ofNullable(ExpenseMapper.instance().toExpense(dto))
                .map(expenseService::save)
                .map(savedDao -> ExpenseMapper.instance().toExpenseDto(savedDao))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UnprocessableEntityException(String.format("The expense can not be saved : ", dto)));
    }

}