package com.example.ERP_Project.controllers;

import com.example.ERP_Project.entities.Expense;
import com.example.ERP_Project.entities.ExpenseCategory;
import com.example.ERP_Project.entities.ExpenseStatus;
import com.example.ERP_Project.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        try {
            List<Expense> expenses = expenseService.getAllExpenses();
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody Expense expense) {
        try {
            // Zorunlu alanların kontrolü
            if (expense.getCategories() == null || 
                expense.getDescription() == null || 
                expense.getPrice() == null || 
                expense.getDate() == null || 
                expense.getStatus() == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Tüm alanlar zorunludur"));
            }

            Expense savedExpense = expenseService.createExpense(expense);
            return ResponseEntity.ok(savedExpense);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Gider kaydedilemedi: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        try {
            return expenseService.getExpenseById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Gider bulunamadı: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        try {
            Expense updatedExpense = expenseService.updateExpense(id, expense);
            return ResponseEntity.ok(updatedExpense);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Gider güncellenemedi: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
            return ResponseEntity.ok(Map.of("message", "Gider başarıyla silindi"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Gider silinemedi: " + e.getMessage()));
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable ExpenseCategory category) {
        try {
            List<Expense> expenses = expenseService.getExpensesByCategory(category);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Expense>> getExpensesByStatus(@PathVariable ExpenseStatus status) {
        try {
            List<Expense> expenses = expenseService.getExpensesByStatus(status);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Expense>> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<Expense> expenses = expenseService.getExpensesByDateRange(startDate, endDate);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Kategoriye ve duruma göre giderleri getir
    @GetMapping("/filter")
    public List<Expense> getExpensesByCategoryAndStatus(
            @RequestParam ExpenseCategory category,
            @RequestParam ExpenseStatus status) {
        return expenseService.getExpensesByCategoryAndStatus(category, status);
    }
} 