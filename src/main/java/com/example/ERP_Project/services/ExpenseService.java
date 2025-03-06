package com.example.ERP_Project.services;

import com.example.ERP_Project.entities.Expense;
import com.example.ERP_Project.entities.ExpenseCategory;
import com.example.ERP_Project.entities.ExpenseStatus;
import com.example.ERP_Project.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    // Tüm giderleri getir
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    
    // ID'ye göre gider getir
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
    
    // Yeni gider ekle
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    
    // Gider güncelle
    public Expense updateExpense(Long id, Expense expense) {
        if (expenseRepository.existsById(id)) {
            expense.setId(id);
            return expenseRepository.save(expense);
        }
        throw new RuntimeException("Gider bulunamadı: " + id);
    }
    
    // Gider sil
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
    
    // Kategoriye göre giderleri getir
    public List<Expense> getExpensesByCategory(ExpenseCategory category) {
        return expenseRepository.findByCategories(category);
    }
    
    // Duruma göre giderleri getir
    public List<Expense> getExpensesByStatus(ExpenseStatus status) {
        return expenseRepository.findByStatus(status);
    }
    
    // Tarih aralığına göre giderleri getir
    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetween(startDate, endDate);
    }
    
    // Kategoriye ve duruma göre giderleri getir
    public List<Expense> getExpensesByCategoryAndStatus(ExpenseCategory category, ExpenseStatus status) {
        return expenseRepository.findByCategoriesAndStatus(category, status);
    }
} 