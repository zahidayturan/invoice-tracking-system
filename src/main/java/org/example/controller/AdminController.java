package org.example.controller;

import org.example.model.Invoice;
import org.example.service.CustomerService;
import org.example.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "admin/customers";
    }

    @GetMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customers";
    }

    @PostMapping("/customer/addInvoice")
    public String addInvoice(@RequestParam Long customerId, @RequestParam String description, @RequestParam double amount) {
        Invoice invoice = new Invoice();
        invoice.setCustomer(customerService.getCustomerById(customerId));
        invoice.setDescription(description);
        invoice.setAmount(amount);
        invoice.setDate(new Date());
        invoiceService.saveInvoice(invoice);
        return "redirect:/admin/customers";
    }

    @GetMapping("/customer/invoices/{id}")
    public String viewInvoices(@PathVariable Long id, Model model) {
        model.addAttribute("invoices", invoiceService.getInvoicesByCustomerId(id));
        return "admin/invoices";
    }
}