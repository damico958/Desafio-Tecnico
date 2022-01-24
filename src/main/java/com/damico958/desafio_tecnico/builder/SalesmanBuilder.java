package com.damico958.desafio_tecnico.builder;

import com.damico958.desafio_tecnico.model.Salesman;

import java.math.BigDecimal;

public class SalesmanBuilder {
    private final Salesman salesman;

    public SalesmanBuilder() {
        this.salesman = new Salesman();
    }

    public static SalesmanBuilder builder() {
        return new SalesmanBuilder();
    }

    public SalesmanBuilder withCpf(String cpf) {
        this.salesman.setSalesmanCpf(cpf);
        return this;
    }

    public SalesmanBuilder withName(String name) {
        this.salesman.setSalesmanName(name);
        return this;
    }

    public SalesmanBuilder withSalary(BigDecimal salary) {
        this.salesman.setSalesmanSalary(salary);
        return this;
    }

    public Salesman build() {
        if(salesman.getSalesmanCpf() == null) {
            throw new IllegalArgumentException("Fail! Can't build a Salesman object without providing a CPF!");
        }
        if(salesman.getSalesmanName() == null) {
            throw new IllegalArgumentException("Fail! Can't build a Salesman object without providing a name!");
        }
        if(salesman.getSalesmanSalary() == null) {
            throw new IllegalArgumentException("Fail! Can't build a Salesman object without providing a salary!");
        }
        return this.salesman;
    }
}
