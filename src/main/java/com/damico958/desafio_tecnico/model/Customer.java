package com.damico958.desafio_tecnico.model;

import br.com.caelum.stella.validation.CNPJValidator;

import java.util.regex.Pattern;

public class Customer {
    static final String CONTAINS_DIGIT_REGEX_RULE = ".*[0-9].*";
    private static final String CONTAINS_LETTER_REGEX_RULE = ".*[a-zA-Z].*";
    private static final int CPF_LENGTH_DELIMITER = 14;

    private String cnpj;
    private String name;
    private String businessArea;

    public Customer() {

    }

    public String getCustomerCnpj() {
        return cnpj;
    }

    public void setCustomerCnpj(String cnpj) {
        if(cnpj == null) {
            throw new IllegalArgumentException("Fail! Could not build a customer object with null CNPJ!");
        }
        if(cnpj.isEmpty()) {
            throw new IllegalArgumentException("Fail! Could not build a customer object with empty CNPJ!");
        }
        if(Pattern.matches(CONTAINS_LETTER_REGEX_RULE, cnpj)) {
            throw new IllegalArgumentException("Fail! Could not build a customer object with letters inside CNPJ!");
        }
        if(cnpj.length() != CPF_LENGTH_DELIMITER) {
            throw new IllegalArgumentException("Fail! Could not build a customer object with wrong CNPJ size!");
        }
        this.cnpj = cnpj;
    }

    public String getCustomerName() {
        return name;
    }

    public void setCustomerName(String name) {
        validate(name);
        this.name = name;
    }

    public String getCustomerBusinessArea() {
        return businessArea;
    }

    public void setCustomerBusinessArea(String businessArea) {
        validate(businessArea);
        this.businessArea = businessArea;
    }

    private boolean validate(String inspectedString) {
        if(inspectedString == null) {
            throw new IllegalArgumentException("Fail! Could not build a customer object with null parameter!");
        }
        if(inspectedString.isEmpty()) {
            throw new IllegalArgumentException("Fail! Could not build a customer object with empty parameter!");
        }
        if(Pattern.matches(CONTAINS_DIGIT_REGEX_RULE, inspectedString)) {
            throw new IllegalArgumentException("Fail! Could not build a customer object with digits inside field!");
        }
        return true;
    }

    @Override
    public String toString() {
        return "Customer CNPJ: " + cnpj +
                "\nName: '" + name + "'" +
                "\nBusiness Area: '" + businessArea + "'\n";
    }
}
