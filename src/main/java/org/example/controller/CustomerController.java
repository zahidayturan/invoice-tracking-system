package org.example.controller;

import org.example.model.Invoice;
import org.example.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
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

        invoices.sort(Comparator.comparing(Invoice::getId));

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
        model.addAttribute("invoice", new Invoice());
        return "customer-details";
    }

    @PostMapping
    public String addInvoice(Invoice invoice) {
        invoiceService.saveInvoice(invoice);
        return "redirect:/customers/" + invoice.getCustomerName();
    }

    @PostMapping("/{customerName}/invoices/{id}/delete")
    public String deleteInvoice(@PathVariable("customerName") String customerName, @PathVariable("id") Long id) {
        invoiceService.deleteInvoiceById(id);
        return "redirect:/customers/" + customerName;
    }

    @PostMapping("/{customerName}/invoices/{id}/update")
    public String updateInvoice(@PathVariable("customerName") String customerName, @PathVariable("id") Long id, Invoice updatedInvoice) {
        Invoice existingInvoice = invoiceService.getInvoiceById(id);
        if (existingInvoice != null) {
            existingInvoice.setAmount(updatedInvoice.getAmount());
            existingInvoice.setDescription(updatedInvoice.getDescription());
            invoiceService.saveInvoice(existingInvoice);
        }
        return "redirect:/customers/" + customerName;
    }

    @PostMapping("/{customerName}/delete")
    public String deleteInvoicesByCustomer(@PathVariable("customerName") String customerName) {
        List<Invoice> customerInvoices = invoiceService.getInvoicesByCustomerName(customerName);
        for (Invoice invoice : customerInvoices) {
            invoiceService.deleteInvoiceById(invoice.getId());
        }
        return "redirect:/customers";
    }
}
