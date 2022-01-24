package com.damico958.desafio_tecnico.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

import static com.damico958.desafio_tecnico.model.Customer.CONTAINS_DIGIT_REGEX_RULE;
import static com.damico958.desafio_tecnico.model.Item.BIG_DECIMAL_SCALE;

public class Salesman {
    private static final String CONTAINS_LETTER_REGEX_RULE = ".*[a-zA-Z].*";
    private static final int CPF_LENGTH_DELIMITER = 11;

    private String cpf;
    private String name;
    private BigDecimal salary;
    private BigDecimal salesTotal;

    public Salesman() {
        salesTotal = BigDecimal.valueOf(0);
    }

    public String getSalesmanCpf() {
        return cpf;
    }

    public void setSalesmanCpf(String cpf) {
        if(cpf == null) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with null CPF!");
        }
        if(cpf.isEmpty()) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with empty CPF!");
        }
        if(Pattern.matches(CONTAINS_LETTER_REGEX_RULE, cpf)) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with letters inside CPF!");
        }
        if(cpf.length() != CPF_LENGTH_DELIMITER) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with wrong CPF size!");
        }
        this.cpf = cpf;
    }

    public String getSalesmanName() {
        return name;
    }

    public void setSalesmanName(String name) {
        validate(name);
        this.name = name;
    }

    public BigDecimal getSalesmanSalary() {
        return salary;
    }

    public void setSalesmanSalary(BigDecimal salary) {
        if(salary == null || (salary.compareTo(BigDecimal.ZERO) < 0)) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with negative or null salary!");
        }
        if (salary.toString().trim().length() == 0) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with empty salary!");
        }
        this.salary = salary.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getSalesTotal() {
        return salesTotal;
    }

    public void addToSalesTotal(BigDecimal newSale) {
        this.salesTotal = salesTotal.add(newSale);
    }

    private boolean validate(String inspectedString) {
        if(inspectedString == null) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with null parameter!");
        }
        if(inspectedString.isEmpty()) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with empty parameter!");
        }
        if(Pattern.matches(CONTAINS_DIGIT_REGEX_RULE, inspectedString)) {
            throw new IllegalArgumentException("Fail! Could not build a salesman object with digits inside field!");
        }
        return true;
    }

    @Override
    public String toString() {
        return "Salesman CPF: " + cpf +
                "\nName: '" + name + "'" +
                "\nSalary: " + salary +
                "\nSales Total: " + salesTotal + "\n";
    }
}
