package com.example.ERP_Project.controllers;

import com.example.ERP_Project.entities.SalesOrder;
import com.example.ERP_Project.services.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sales-orders")
@CrossOrigin(origins = "*")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @GetMapping
    public List<SalesOrder> getAllOrders() {
        return salesOrderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrder> getOrderById(@PathVariable Long id) {
        return salesOrderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    public List<SalesOrder> getOrdersByCompany(@PathVariable Long companyId) {
        return salesOrderService.findByCompanyId(companyId);
    }

    @GetMapping("/customer/{customerId}")
    public List<SalesOrder> getOrdersByCustomer(@PathVariable Long customerId) {
        return salesOrderService.findByCustomerId(customerId);
    }

    @PostMapping
    public ResponseEntity<SalesOrder> createOrder(@RequestBody SalesOrder salesOrder) {
        return ResponseEntity.ok(salesOrderService.save(salesOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesOrder> updateOrder(@PathVariable Long id, @RequestBody SalesOrder salesOrder) {
        return salesOrderService.findById(id)
                .map(existingOrder -> {
                    salesOrder.setId(id);
                    return ResponseEntity.ok(salesOrderService.save(salesOrder));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return salesOrderService.findById(id)
                .map(order -> {
                    salesOrderService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 