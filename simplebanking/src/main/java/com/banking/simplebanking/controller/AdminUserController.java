package com.banking.simplebanking.controller;

import com.banking.simplebanking.model.AdminUser;
import com.banking.simplebanking.service.AdminUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService service;

    // ✅ Register a new admin user
    @PostMapping("/register")
    public AdminUser register(@RequestBody AdminUser adminUser) {
        return service.register(adminUser);
    }

    // ✅ Login with session support
    @PostMapping("/login")
    public String loginAdmin(@RequestBody AdminUser adminUser, HttpSession session) {
        boolean isAuthenticated = service.login(adminUser.getUsername(), adminUser.getPassword());
        if (isAuthenticated) {
            session.setAttribute("admin", adminUser.getUsername());
            return "Login successful!";
        } else {
            return "Invalid username or password.";
        }
    }

    // ✅ Logout
    @PostMapping("/logout")
    public String logoutAdmin(HttpSession session) {
        session.invalidate();
        return "Logged out successfully!";
    }
}
