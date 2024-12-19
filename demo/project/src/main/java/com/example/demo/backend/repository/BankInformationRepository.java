package com.example.backend.repository;

import com.example.backend.entity.BankInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BankInformationRepository extends JpaRepository<BankInformation, UUID> {
    BankInformation findByUserId(UUID userId);
}