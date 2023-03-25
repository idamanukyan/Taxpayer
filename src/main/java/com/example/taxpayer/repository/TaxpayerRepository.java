package com.example.taxpayer.repository;

import com.example.taxpayer.entity.Taxpayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxpayerRepository extends JpaRepository<Taxpayer, Integer> {

    boolean existsByEmail(String email);

}
