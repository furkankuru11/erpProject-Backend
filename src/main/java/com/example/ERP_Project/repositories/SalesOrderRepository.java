package com.example.ERP_Project.repositories;

import com.example.ERP_Project.entities.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    List<SalesOrder> findByCompanyId(Long companyId);
    List<SalesOrder> findByCustomerId(Long customerId);
    boolean existsByOrderNumber(String orderNumber);
} 