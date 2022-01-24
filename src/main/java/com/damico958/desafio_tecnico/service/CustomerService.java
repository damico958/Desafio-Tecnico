package com.damico958.desafio_tecnico.service;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.damico958.desafio_tecnico.builder.CustomerBuilder;
import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Customer;
import com.damico958.desafio_tecnico.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentFileDataService currentFileDataService;

    private final CNPJValidator cnpjValidator;

    @Autowired
    public CustomerService(CNPJValidator cnpjValidator) {
        this.cnpjValidator = cnpjValidator;
    }

    public boolean save(String cnpj, String name, String businessArea) {
        try {
            cnpjValidator.assertValid(cnpj);
        } catch (InvalidStateException exception) {
            throw new IllegalArgumentException("Fail! Could not accept this invalid CNPJ! Cause of exception from the validator API: " + exception.getInvalidMessages());
        }
        if(customerRepository.existsByCnpj(cnpj)) {
            throw new InvalidBehaviorException("Fail! Could not create new Customer as this CNPJ is already registered!");
        }
        Customer customer = new CustomerBuilder()
                .withCnpj(cnpj)
                .withName(name)
                .withBusinessArea(businessArea)
                .build();
        customerRepository.save(customer);
        currentFileDataService.saveCustomer(customer);
        return true;
    }

    public Customer findCustomerByCnpj(String cnpj) {
        Customer foundCustomer = customerRepository.findCustomerByCnpj(cnpj);
        if(foundCustomer == null) {
            throw new InvalidBehaviorException("Fail! Could not find a Customer with this CNPJ!");
        }
        return foundCustomer;
    }

    public Customer remove(String cnpj) {
        Customer foundCustomer = customerRepository.remove(cnpj);
        if(foundCustomer == null) {
            throw new InvalidBehaviorException("Fail! Could not remove a Customer with this CNPJ as it doesn't exist!");
        }
        return foundCustomer;
    }
}
