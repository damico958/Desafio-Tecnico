package com.damico958.desafio_tecnico.builder;

import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.model.Sale;
import com.damico958.desafio_tecnico.model.Salesman;

import java.util.List;

public class SaleBuilder {
    private final Sale sale;

    public SaleBuilder() {
        this.sale = new Sale();
    }

    public static SaleBuilder builder() {
        return new SaleBuilder();
    }

    public SaleBuilder withId(Long id) {
        this.sale.setSaleId(id);
        return this;
    }

    public SaleBuilder withItemsList(List<Item> itemsList) {
        this.sale.setSaleItemsList(itemsList);
        return this;
    }

    public SaleBuilder withSalesman(Salesman salesman) {
        this.sale.setSaleSalesman(salesman);
        return this;
    }

    public Sale build() {
        if(sale.getSaleId() == null) {
            throw new IllegalArgumentException("Fail! Can't build a Sale object without providing an ID!");
        }
        if(sale.getSaleItemsList() == null || sale.getSaleItemsList().isEmpty()) {
            throw new IllegalArgumentException("Fail! Can't build a Sale object without providing an items list!");
        }
        if(sale.getSaleSalesman() == null) {
            throw new IllegalArgumentException("Fail! Can't build a Sale object without providing a Salesman!");
        }
        this.sale.setSaleTotalCost();
        return this.sale;
    }
}
