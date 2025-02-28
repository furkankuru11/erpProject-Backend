package com.example.ERP_Project.services;

import com.example.ERP_Project.entities.SalesOrder;
import com.example.ERP_Project.repositories.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SalesOrderService {
    
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    public List<SalesOrder> findAll() {
        return salesOrderRepository.findAll();
    }

    public Optional<SalesOrder> findById(Long id) {
        return salesOrderRepository.findById(id);
    }

    public SalesOrder save(SalesOrder salesOrder) {
        calculateNetAmount(salesOrder);
        return salesOrderRepository.save(salesOrder);
    }

    private void calculateNetAmount(SalesOrder salesOrder) {
        BigDecimal total = salesOrder.getTotalAmount() != null ? salesOrder.getTotalAmount() : BigDecimal.ZERO;
        BigDecimal tax = salesOrder.getTaxAmount() != null ? salesOrder.getTaxAmount() : BigDecimal.ZERO;
        BigDecimal discount = salesOrder.getDiscountAmount() != null ? salesOrder.getDiscountAmount() : BigDecimal.ZERO;
        
        salesOrder.setNetAmount(total.add(tax).subtract(discount));
    }

    public void deleteById(Long id) {
        salesOrderRepository.deleteById(id);
    }

    public List<SalesOrder> findByCompanyId(Long companyId) {
        return salesOrderRepository.findByCompanyId(companyId);
    }

    public List<SalesOrder> findByCustomerId(Long customerId) {
        return salesOrderRepository.findByCustomerId(customerId);
    }
} 