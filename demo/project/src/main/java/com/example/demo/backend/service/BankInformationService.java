package com.example.backend.service;

import com.example.backend.entity.BankInformation;
import com.example.backend.repository.BankInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class BankInformationService {
    private final BankInformationRepository bankInformationRepository;
    private final SecretKey secretKey; // This should be injected from environment variables or a properties file
    private final IvParameterSpec ivSpec; // Initialization Vector for AES

    @Autowired
    public BankInformationService(BankInformationRepository bankInformationRepository) {
        this.bankInformationRepository = bankInformationRepository;

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // AES with 256-bit key
            this.secretKey = keyGen.generateKey();
            this.ivSpec = new IvParameterSpec(new byte[16]); // Initialization vector for AES
        } catch (Exception e) {
            throw new RuntimeException("Error initializing AES encryption", e);
        }
    }

    public BankInformation createBankInformation(UUID userId, BankInformation bankInformation) {
        bankInformation.setUserId(userId);
        bankInformation.setBankAccountNumber(encrypt(bankInformation.getBankAccountNumber()));
        return bankInformationRepository.save(bankInformation);
    }

    public BankInformation getBankInformation(UUID userId) {
        BankInformation bankInformation = bankInformationRepository.findByUserId(userId);
        bankInformation.setBankAccountNumber(decrypt(bankInformation.getBankAccountNumber()));
        return bankInformation;
    }

    public BankInformation updateBankInformation(UUID userId, BankInformation updatedBankInformation) {
        BankInformation bankInformation = bankInformationRepository.findByUserId(userId);
        bankInformation.setBankAccountNumber(encrypt(updatedBankInformation.getBankAccountNumber()));
        bankInformation.setBankName(updatedBankInformation.getBankName());
        bankInformation.setAccountType(updatedBankInformation.getAccountType());
        return bankInformationRepository.save(bankInformation);
    }

    public void deleteBankInformation(UUID userId) {
        BankInformation bankInformation = bankInformationRepository.findByUserId(userId);
        bankInformationRepository.delete(bankInformation);
    }

    private String encrypt(String value) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    private String decrypt(String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decoded = Base64.getDecoder().decode(encryptedValue);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}