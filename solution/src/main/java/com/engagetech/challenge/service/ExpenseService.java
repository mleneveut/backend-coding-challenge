package com.engagetech.challenge.service;

import com.engagetech.challenge.entity.Expense;
import com.engagetech.challenge.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public Expense getById(long id) {
        return expenseRepository.findOne(id);
    }

    @Transactional
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }
}
