package com.banking.simplebanking.service;

import com.banking.simplebanking.model.BankAccount;
import com.banking.simplebanking.model.Transaction;
import com.banking.simplebanking.repository.BankAccountRepository;
import com.banking.simplebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankAccountRepository accountRepo;

    @Autowired
    private TransactionRepository txnRepo;

    public BankAccount createAccount(String name, Double initialBalance) {
        BankAccount account = new BankAccount();
        account.setAccountHolderName(name);
        account.setBalance(initialBalance);
        return accountRepo.save(account);
    }

    public BankAccount deposit(Long accountId, Double amount) {
        BankAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);
        txnRepo.save(new Transaction(null, "Deposit", amount, LocalDateTime.now(), account, "Deposited"));
        return account;
    }

    public BankAccount withdraw(Long accountId, Double amount) {
        BankAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);
        txnRepo.save(new Transaction(null, "Withdraw", amount, LocalDateTime.now(), account, "Withdrawn"));
        return account;
    }

    public void transfer(Long fromId, Long toId, Double amount) {
        BankAccount from = accountRepo.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        BankAccount to = accountRepo.findById(toId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        if (from.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        accountRepo.save(from);
        accountRepo.save(to);

        txnRepo.save(new Transaction(null, "Transfer", amount, LocalDateTime.now(), from, "Transferred to account " + toId));
        txnRepo.save(new Transaction(null, "Transfer", amount, LocalDateTime.now(), to, "Received from account " + fromId));
    }

    public List<Transaction> getTransactionHistory(Long accountId) {
        return txnRepo.findByAccountId(accountId);
    }

    public BankAccount updateAccountName(Long accountId, String newName) {
        BankAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountHolderName(newName);
        return accountRepo.save(account);
    }

    public BankAccount getAccountById(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<BankAccount> getAllAccounts() {
        return accountRepo.findAll();
    }
    public void deleteAccount(Long accountId) {
        BankAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepo.delete(account);
        // Optionally: Also delete transactions related to this account
        txnRepo.deleteAll(txnRepo.findByAccountId(accountId));
    }


}
