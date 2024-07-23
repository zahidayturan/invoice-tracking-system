package org.example.controller;

import org.example.model.Invoice;
import org.example.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public String listCustomers(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();

        Map<String, Long> customerInvoiceCount = invoices.stream()
                .collect(Collectors.groupingBy(Invoice::getCustomerName, Collectors.counting()));

        model.addAttribute("customerInvoiceCount", customerInvoiceCount);
        return "customers";
    }

    @GetMapping("/{customerName}")
    public String customerDetails(@PathVariable("customerName") String customerName, Model model) {
        List<Invoice> customerInvoices = invoiceService.getInvoicesByCustomerName(customerName);
        model.addAttribute("customerName", customerName);
        model.addAttribute("invoices", customerInvoices);
        return "customer-details";
    }
}
