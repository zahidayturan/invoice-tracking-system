package org.example.controller;

import org.example.model.Invoice;
import org.example.service.ExcelExportService;
import org.example.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ExcelExportService excelExportService;

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

    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        ByteArrayInputStream bis = excelExportService.exportInvoicesToExcel(invoices);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=invoices.xlsx");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(bis));
    }
}
