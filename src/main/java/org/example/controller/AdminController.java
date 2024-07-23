package org.example.controller;

import org.example.model.Invoice;
import org.example.model.User;
import org.example.service.InvoiceService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/customers")
    public List<User> getAllCustomers() {
        return userService.findAll();
    }

    @PostMapping("/customer/{customerId}/invoice")
    public Invoice addInvoice(@PathVariable Long customerId, @RequestBody Invoice invoice) {
        User customer = userService.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        invoice.setUser(customer);
        return invoiceService.saveInvoice(invoice);
    }

    @GetMapping("/customer/{customerId}/invoices")
    public List<Invoice> getCustomerInvoices(@PathVariable Long customerId) {
        return invoiceService.findByUserId(customerId);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        userService.deleteUserById(customerId);
        return ResponseEntity.ok("Customer deleted successfully!");
    }
}
