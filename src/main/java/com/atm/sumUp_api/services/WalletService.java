package com.atm.sumUp_api.services;

import com.atm.sumUp_api.models.Wallet;
import com.atm.sumUp_api.repos.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(String username) {
        Wallet wallet = new Wallet();
        wallet.setUsername(username);
        return walletRepository.save(wallet);
    }

    public Optional<Wallet> getWallet(Long id) {
        return walletRepository.findById(id);
    }

    public Wallet deposit(Long id, Double amount) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setBalance(wallet.getBalance() + amount);
        return walletRepository.save(wallet);
    }

    public Wallet withdraw(Long id, Double amount) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        return walletRepository.save(wallet);
    }
}
