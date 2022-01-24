package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.model.Customer;
import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.model.Sale;
import com.damico958.desafio_tecnico.model.Salesman;
import com.damico958.desafio_tecnico.repository.CurrentFileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentFileDataService {
    @Autowired
    private CurrentFileDataRepository currentFileDataRepository;

    public void saveSalesman(Salesman salesman) {
        currentFileDataRepository.saveSalesman(salesman);
    }

    public void saveCustomer(Customer customer) {
        currentFileDataRepository.saveCustomer(customer);
    }

    public void saveSale(Sale sale) {
        currentFileDataRepository.saveSale(sale);
    }

    public void saveItem(Item item) {
        currentFileDataRepository.saveItem(item);
    }

    public List<Salesman> getSalesmenList() {
        return currentFileDataRepository.getSalesmenList();
    }

    public List<Customer> getCustomersList() {
        return currentFileDataRepository.getCustomersList();
    }

    public List<Sale> getSalesList() {
        return currentFileDataRepository.getSalesList();
    }

    public List<Item> getItemsList() {
        return currentFileDataRepository.getItemsList();
    }

    public void clear() {
        currentFileDataRepository.clear();
    }

}
