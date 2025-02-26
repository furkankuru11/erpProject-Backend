package com.example.ERP_Project.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByTaxNumber(String taxNumber);
    List<Company> findByNameContaining(String name);
    List<Company> findByRiskStatus(RiskStatus riskStatus);
} 