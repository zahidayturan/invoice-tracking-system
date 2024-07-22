package org.example.controller;

import org.example.services.InvoiceService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "admin";
    }
}
