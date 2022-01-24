package com.damico958.desafio_tecnico.repository;

import com.damico958.desafio_tecnico.model.Customer;
import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.model.Sale;
import com.damico958.desafio_tecnico.model.Salesman;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CurrentFileDataRepository {
    private final List<Salesman> salesmenList;
    private final List<Customer> customersList;
    private final List<Sale> salesList;
    private final List<Item> itemsList;

    public CurrentFileDataRepository() {
        this.salesmenList = new ArrayList<Salesman>();
        this.customersList = new ArrayList<Customer>();
        this.salesList = new ArrayList<Sale>();
        this.itemsList = new ArrayList<Item>();
    }

    public void saveSalesman(Salesman salesman) {
        salesmenList.add(salesman);
    }

    public void saveCustomer(Customer customer) {
        customersList.add(customer);
    }

    public void saveSale(Sale sale) {
        salesList.add(sale);
    }

    public void saveItem(Item item) {
        itemsList.add(item);
    }

    public List<Salesman> getSalesmenList() {
        return salesmenList;
    }

    public List<Customer> getCustomersList() {
        return customersList;
    }

    public List<Sale> getSalesList() {
        return salesList;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void clear() {
        salesmenList.clear();
        customersList.clear();
        salesList.clear();
        itemsList.clear();
    }
}
