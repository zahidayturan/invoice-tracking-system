package org.example.service;

import org.example.model.Admin;
import org.example.model.Customer;
import org.example.repository.AdminRepository;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return new User(admin.getUsername(), admin.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return new User(customer.getUsername(), customer.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        }

        throw new UsernameNotFoundException("User not found");
    }
}

