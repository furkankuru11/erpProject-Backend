package com.example.ERP_Project.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product saveProduct(Product product) {
        if (productRepository.findByCode(product.getCode()).isPresent()) {
            throw new RuntimeException("Bu ürün kodu zaten kullanılıyor!");
        }
        return productRepository.save(product);
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));
            
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setUnit(product.getUnit());
        existingProduct.setPurchasePrice(product.getPurchasePrice());
        existingProduct.setSalePrice(product.getSalePrice());
        existingProduct.setTaxRate(product.getTaxRate());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setMinStockLevel(product.getMinStockLevel());
        existingProduct.setStatus(product.getStatus());
        
        return productRepository.save(existingProduct);
    }
} 