package com.banking.simplebanking.controller;

import com.banking.simplebanking.model.BankAccount;
import com.banking.simplebanking.model.Transaction;
import com.banking.simplebanking.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService service;

    @PostMapping("/create")
    public BankAccount createAccount(@RequestParam String name, @RequestParam Double initialBalance) {
        return service.createAccount(name, initialBalance);
    }

    @PostMapping("/deposit")
    public BankAccount deposit(@RequestParam Long accountId, @RequestParam Double amount) {
        return service.deposit(accountId, amount);
    }

    @PostMapping("/withdraw")
    public BankAccount withdraw(@RequestParam Long accountId, @RequestParam Double amount) {
        return service.withdraw(accountId, amount);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromId, @RequestParam Long toId, @RequestParam Double amount) {
        service.transfer(fromId, toId, amount);
        return "Transfer Successful";
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam Long accountId) {
        return service.getTransactionHistory(accountId);
    }

    @PutMapping("/updateAccountName")
    public BankAccount updateAccountName(@RequestParam Long accountId, @RequestParam String newName) {
        return service.updateAccountName(accountId, newName);
    }

    @GetMapping("/account")
    public BankAccount getAccountById(@RequestParam Long accountId) {
        return service.getAccountById(accountId);
    }

    @GetMapping("/allAccounts")
    public List<BankAccount> getAllAccounts() {
        return service.getAllAccounts();
    }
    @DeleteMapping("/deleteAccount")
    public String deleteAccount(@RequestParam Long accountId) {
        service.deleteAccount(accountId);
        return "Account deleted successfully.";
    }


}
