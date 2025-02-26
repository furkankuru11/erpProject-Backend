package com.example.ERP_Project.controller;

import com.example.ERP_Project.entities.*;
import com.example.ERP_Project.payload.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-orders")
@CrossOrigin(origins = "http://localhost:3000")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;
    
    @GetMapping
    public List<SalesOrder> getAllOrders() {
        return salesOrderService.getAllOrders();
    }
    
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody SalesOrder order) {
        try {
            SalesOrder savedOrder = salesOrderService.saveOrder(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Sipariş oluşturulamadı: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        return salesOrderService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody SalesOrder order) {
        try {
            SalesOrder updatedOrder = salesOrderService.updateOrder(id, order);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Sipariş güncellenemedi: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            salesOrderService.deleteOrder(id);
            return ResponseEntity.ok(new MessageResponse("Sipariş başarıyla silindi"));
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Sipariş silinemedi: " + e.getMessage()));
        }
    }
    
    @GetMapping("/company/{companyId}")
    public List<SalesOrder> getOrdersByCompany(@PathVariable Long companyId) {
        return salesOrderService.getOrdersByCompany(companyId);
    }
    
    @GetMapping("/customer/{customerId}")
    public List<SalesOrder> getOrdersByCustomer(@PathVariable Long customerId) {
        return salesOrderService.getOrdersByCustomer(customerId);
    }
    
    @GetMapping("/status/{status}")
    public List<SalesOrder> getOrdersByStatus(@PathVariable OrderStatus status) {
        return salesOrderService.getOrdersByStatus(status);
    }
} 