package org.example.controller;

import org.example.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "invoices";
    }

    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoiceById(id);
        return "redirect:/invoices"; // Silme işleminden sonra liste sayfasına yönlendirir
    }
}
