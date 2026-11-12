package com.bank.loan.config;

import com.bank.loan.model.Admin;
import com.bank.loan.model.Customer;
import com.bank.loan.repository.AdminRepository;
import com.bank.loan.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UnifiedUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String loginType = request.getParameter("loginType");
        
        // Admin login - only check admin repository
        if ("admin".equals(loginType)) {
            Optional<Admin> admin = adminRepository.findByUsername(username);
            if (admin.isPresent()) {
                return User.builder()
                        .username(admin.get().getUsername())
                        .password(admin.get().getPassword())
                        .roles("ADMIN")
                        .build();
            }
            throw new UsernameNotFoundException("Admin not found: " + username);
        }
        
        // Customer login - only check customer repository
        if ("customer".equals(loginType)) {
            Optional<Customer> customer = customerRepository.findByEmail(username);
            if (customer.isPresent()) {
                Customer c = customer.get();
                if (c.getPassword() != null && !c.getPassword().isEmpty()) {
                    return User.builder()
                            .username(c.getEmail())
                            .password(c.getPassword())
                            .roles("CUSTOMER")
                            .build();
                }
            }
            throw new UsernameNotFoundException("Customer not found: " + username);
        }
        
        throw new UsernameNotFoundException("Invalid login type");
    }
}
