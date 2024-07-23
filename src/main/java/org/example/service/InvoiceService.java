package org.example.service;

import org.example.model.Invoice;
import org.example.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> findByUserId(Long userId) {
        return invoiceRepository.findByUserId(userId);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}