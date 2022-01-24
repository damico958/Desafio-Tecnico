package com.damico958.desafio_tecnico.repository;

import com.damico958.desafio_tecnico.model.Salesman;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SalesmanRepository {
    private final Map<String, Salesman> salesmanRepository;
    private final Map<String, String> namesCache;

    public SalesmanRepository() {
        this.salesmanRepository = new HashMap<String, Salesman>();
        this.namesCache = new HashMap<String, String>();
    }

    public void save(Salesman salesman) {
        salesmanRepository.put(salesman.getSalesmanCpf(), salesman);
        namesCache.put(salesman.getSalesmanName(), salesman.getSalesmanCpf());
    }

    public Salesman findSalesmanByCpf(String cpf) {
        return salesmanRepository.get(cpf);
    }

    public Salesman findSalesmanByName(String name) {
        return salesmanRepository.get(namesCache.get(name));
    }

    public boolean existsByCpf(String cpf) {
        return salesmanRepository.containsKey(cpf);
    }

    public Salesman remove(String cpf) {
        Salesman foundSalesman = salesmanRepository.get(cpf);
        this.salesmanRepository.remove(cpf);
        return foundSalesman;
    }
}
