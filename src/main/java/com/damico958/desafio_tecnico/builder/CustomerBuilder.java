package com.damico958.desafio_tecnico.builder;

import com.damico958.desafio_tecnico.model.Customer;

public class CustomerBuilder {
    private final Customer customer;

    public CustomerBuilder() {
        this.customer = new Customer();
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public CustomerBuilder withCnpj(String cnpj) {
        this.customer.setCustomerCnpj(cnpj);
        return this;
    }

    public CustomerBuilder withName(String name) {
        this.customer.setCustomerName(name);
        return this;
    }

    public CustomerBuilder withBusinessArea(String businessArea) {
        this.customer.setCustomerBusinessArea(businessArea);
        return this;
    }

    public Customer build() {
        if(customer.getCustomerCnpj() == null) {
            throw new IllegalArgumentException("Fail! Can't build a Customer object without providing a CNPJ!");
        }
        if(customer.getCustomerName() == null) {
            throw new IllegalArgumentException("Fail! Can't build a Customer object without providing a name!");
        }
        if(customer.getCustomerBusinessArea() == null) {
            throw new IllegalArgumentException("Fail! Can't build a Customer object without providing a business area!");
        }
        return this.customer;
    }
}
