package com.example.ERP_Project.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Customer saveCustomer(Customer customer) {
        if (customer.getEmail() != null && !customer.getEmail().isEmpty() && 
            customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Bu email adresi zaten kullanılıyor!");
        }
        return customerRepository.save(customer);
    }
    
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!"));
            
        existingCustomer.setName(customer.getName());
        existingCustomer.setSurname(customer.getSurname());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setCity(customer.getCity());
        existingCustomer.setDistrict(customer.getDistrict());
        
        return customerRepository.save(existingCustomer);
    }
    
    public List<Customer> searchCustomers(String query) {
        return customerRepository.findByNameContainingOrSurnameContaining(query, query);
    }
} 