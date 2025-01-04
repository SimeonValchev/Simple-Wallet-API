package com.example.wallet;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wallets")
public class WalletController {

	private Map<String, Wallet> wallets = new HashMap<>(); // Store wallets by ID

	// Create a new wallet
	@PostMapping("/create")
	public String createWallet(@RequestBody String walletId) {
		if (walletId == null || walletId.trim().isEmpty()) {
			return "Error: Wallet ID cannot be empty.";
		}
		if (wallets.containsKey(walletId)) {
			return "Error: Wallet ID '" + walletId + "' already exists.";
		}
		wallets.put(walletId, new Wallet());
		return "Wallet '" + walletId + "' created successfully.";
	}

	// View the balance of a specific wallet
	@GetMapping("/{walletId}/balance")
	public String getBalance(@PathVariable String walletId) {
		Wallet wallet = wallets.get(walletId);
		if (wallet == null) {
			return "Error: Wallet ID '" + walletId + "' not found.";
		}
		return "The balance of wallet '" + walletId + "' is " + wallet.getBalance() + ".";
	}

	// Deposit money into a specific wallet
	@PutMapping("/{walletId}/deposit")
	public String depositFunds(@PathVariable String walletId, @RequestBody double amount) {
		Wallet wallet = wallets.get(walletId);
		if (wallet == null) {
			return "Error: Wallet ID '" + walletId + "' not found.";
		}
		if (amount <= 0) {
			return "Error: Deposit amount must be greater than 0.";
		}
		wallet.addFunds(amount);
		return "Successfully deposited " + amount + " to wallet '" + walletId + "'. New balance: " + wallet.getBalance() + ".";
	}

	// Withdraw money from a specific wallet
	@PutMapping("/{walletId}/withdraw")
	public String withdrawFunds(@PathVariable String walletId, @RequestBody double amount) {
		Wallet wallet = wallets.get(walletId);
		if (wallet == null) {
			return "Error: Wallet ID '" + walletId + "' not found.";
		}
		if (amount <= 0) {
			return "Error: Withdrawal amount must be greater than 0.";
		}
		if (amount > wallet.getBalance()) {
			return "Error: Insufficient balance in wallet '" + walletId + "'. Current balance: " + wallet.getBalance() + ".";
		}
		wallet.withdrawFunds(amount);
		return "Successfully withdrew " + amount + " from wallet '" + walletId + "'. New balance: " + wallet.getBalance() + ".";
	}
}
