package com.example.backend.controller;

import com.example.backend.entity.BankInformation;
import com.example.backend.service.BankInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/bank-information")
public class BankInformationController {
    private final BankInformationService bankInformationService;

    @Autowired
    public BankInformationController(BankInformationService bankInformationService) {
        this.bankInformationService = bankInformationService;
    }

    @PostMapping
    public ResponseEntity<BankInformation> createBankInformation(@PathVariable UUID userId,
                                                                 @RequestBody BankInformation bankInformation) {
        return new ResponseEntity<>(bankInformationService.createBankInformation(userId, bankInformation), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BankInformation> getBankInformation(@PathVariable UUID userId) {
        return ResponseEntity.ok(bankInformationService.getBankInformation(userId));
    }

    @PutMapping
    public ResponseEntity<BankInformation> updateBankInformation(@PathVariable UUID userId,
                                                                 @RequestBody BankInformation bankInformation) {
        return ResponseEntity.ok(bankInformationService.updateBankInformation(userId, bankInformation));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBankInformation(@PathVariable UUID userId) {
        bankInformationService.deleteBankInformation(userId);
        return ResponseEntity.noContent().build();
    }
}