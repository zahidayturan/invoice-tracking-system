package org.example.controller;

import org.example.model.Invoice;
import org.example.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        model.addAttribute("invoice", new Invoice());
        return "invoices";
    }

    @PostMapping
    public String addInvoice(Invoice invoice) {
        invoiceService.saveInvoice(invoice);
        return "redirect:/invoices";
    }

    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoiceById(id);
        return "redirect:/invoices";
    }
}
