package com.damico958.desafio_tecnico.repository;

import com.damico958.desafio_tecnico.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerRepository {
    private final Map<String, Customer> customerRepository;

    public CustomerRepository() {
        this.customerRepository = new HashMap<String, Customer>();
    }

    public void save(Customer customer) {
        customerRepository.put(customer.getCustomerCnpj(), customer);
    }

    public Customer findCustomerByCnpj(String cnpj) {
        return customerRepository.get(cnpj);
    }

    public boolean existsByCnpj(String cnpj) {
        return customerRepository.containsKey(cnpj);
    }

    public Customer remove(String cnpj) {
        Customer foundCustomer = customerRepository.get(cnpj);
        this.customerRepository.remove(cnpj);
        return foundCustomer;
    }
}
