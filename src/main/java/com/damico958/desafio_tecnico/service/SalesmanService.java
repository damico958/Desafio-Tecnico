package com.damico958.desafio_tecnico.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.damico958.desafio_tecnico.builder.SalesmanBuilder;
import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Salesman;
import com.damico958.desafio_tecnico.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SalesmanService {
    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private CurrentFileDataService currentFileDataService;

    private final CPFValidator cpfValidator;

    @Autowired
    public SalesmanService(CPFValidator cpfValidator) {
        this.cpfValidator = cpfValidator;
    }

    public boolean save(String cpf, String name, BigDecimal salary) {
        try {
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException exception) {
            throw new IllegalArgumentException("Fail! Could not accept this invalid CPF! Cause of exception from the validator API: " + exception.getInvalidMessages());
        }
        if (salesmanRepository.existsByCpf(cpf)) {
            throw new InvalidBehaviorException("Fail! Could not create new Salesman as this CPF is already registered!");
        }
        Salesman salesman = new SalesmanBuilder()
                .withCpf(cpf)
                .withName(name)
                .withSalary(salary)
                .build();
        salesmanRepository.save(salesman);
        currentFileDataService.saveSalesman(salesman);
        return true;
    }

    public Salesman findSalesmanByCpf(String cpf) {
        Salesman foundSalesman = salesmanRepository.findSalesmanByCpf(cpf);
        if(foundSalesman == null) {
            throw new InvalidBehaviorException("Fail! Could not find a Salesman with this CPF!");
        }
        return foundSalesman;
    }

    public Salesman findSalesmanByName(String name) {
        Salesman foundSalesman = salesmanRepository.findSalesmanByName(name);
        if(foundSalesman == null) {
            throw new InvalidBehaviorException("Fail! Could not find a Salesman with this name!");
        }
        return foundSalesman;
    }

    public Salesman remove(String cpf) {
        Salesman foundSalesman = salesmanRepository.remove(cpf);
        if(foundSalesman == null) {
            throw new InvalidBehaviorException("Fail! Could not remove a Salesman with this CPF as it doesn't exist!");
        }
        return foundSalesman;
    }
}
