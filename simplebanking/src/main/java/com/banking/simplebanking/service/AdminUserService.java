package com.banking.simplebanking.service;

import com.banking.simplebanking.model.AdminUser;
import com.banking.simplebanking.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminRepo;

    // ✅ Register new admin
    public AdminUser register(AdminUser user) {
        // You can hash password here before saving for security (optional for now)
        return adminRepo.save(user);
    }

    // ✅ Login check
    public boolean login(String username, String password) {
        AdminUser user = adminRepo.findByUsername(username).orElse(null);
        return user != null && user.getPassword().equals(password);
    }
}
