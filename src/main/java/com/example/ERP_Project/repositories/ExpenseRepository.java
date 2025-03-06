package com.example.ERP_Project.repositories;

import com.example.ERP_Project.entities.Expense;
import com.example.ERP_Project.entities.ExpenseCategory;
import com.example.ERP_Project.entities.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Kategoriye göre giderleri getir
    List<Expense> findByCategories(ExpenseCategory category);
    
    // Duruma göre giderleri getir
    List<Expense> findByStatus(ExpenseStatus status);
    
    // Tarih aralığına göre giderleri getir
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Kategoriye ve duruma göre giderleri getir
    List<Expense> findByCategoriesAndStatus(ExpenseCategory category, ExpenseStatus status);
} 