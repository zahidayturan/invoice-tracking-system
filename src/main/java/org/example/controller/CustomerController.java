package org.example.controller;

import org.example.models.Invoice;
import org.example.models.User;
import org.example.services.InvoiceService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class CustomerController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @GetMapping("/customer")
    public String customerDashboard(Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Long userId = getUserIdByUsername(username);

        model.addAttribute("invoices", invoiceService.getInvoicesByUserId(userId));
        return "customer";
    }

    @PostMapping("/customer/invoices")
    public String createInvoice(@RequestParam String description, @RequestParam double amount) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Long userId = getUserIdByUsername(username);

        Invoice invoice = new Invoice();
        invoice.setDescription(description);
        invoice.setAmount(amount);
        invoice.setDate(LocalDateTime.now());
        invoice.setUser(userService.getUserById(userId));

        invoiceService.createInvoice(invoice);
        return "redirect:/customer";
    }

    private Long getUserIdByUsername(String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return user.getId();
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
