package org.example.controller;

import org.example.model.Customer;
import org.example.service.CustomerService;
import org.example.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/invoices")
    public String listInvoices(Principal principal, Model model) {
        Optional<Customer> optionalCustomer = customerService.getCustomerByUsername(principal.getName());

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            model.addAttribute("invoices", invoiceService.getInvoicesByCustomerId(customer.getId()));
            return "customer/invoices";
        } else {
            return "error/customer-not-found";
        }
    }
}
