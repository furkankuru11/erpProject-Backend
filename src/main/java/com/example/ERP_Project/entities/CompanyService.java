package com.example.ERP_Project.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
    
    public Company saveCompany(Company company) {
        if (company.getTaxNumber() != null && !company.getTaxNumber().isEmpty() && 
            companyRepository.findByTaxNumber(company.getTaxNumber()).isPresent()) {
            throw new RuntimeException("Bu vergi numarası zaten kullanılıyor!");
        }
        return companyRepository.save(company);
    }
    
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }
    
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
    
    public Company updateCompany(Long id, Company company) {
        Company existingCompany = companyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Firma bulunamadı!"));
            
        existingCompany.setName(company.getName());
        existingCompany.setTaxNumber(company.getTaxNumber());
        existingCompany.setTaxOffice(company.getTaxOffice());
        existingCompany.setPhone(company.getPhone());
        existingCompany.setEmail(company.getEmail());
        existingCompany.setAddress(company.getAddress());
        existingCompany.setCity(company.getCity());
        existingCompany.setDistrict(company.getDistrict());
        existingCompany.setCreditLimit(company.getCreditLimit());
        existingCompany.setCurrentBalance(company.getCurrentBalance());
        existingCompany.setRiskStatus(company.getRiskStatus());
        
        return companyRepository.save(existingCompany);
    }
    
    public List<Company> searchCompanies(String query) {
        return companyRepository.findByNameContaining(query);
    }
    
    public List<Company> getCompaniesByRiskStatus(RiskStatus riskStatus) {
        return companyRepository.findByRiskStatus(riskStatus);
    }
} 