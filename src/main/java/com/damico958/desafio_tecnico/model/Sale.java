package com.damico958.desafio_tecnico.model;

import java.math.BigDecimal;
import java.util.List;

public class Sale {
    private Long id;
    private List<Item> itemsList;
    private Salesman salesman;
    private BigDecimal totalCost;

    public Sale() {
        totalCost = BigDecimal.valueOf(0);
    }

    public Long getSaleId() {
        return id;
    }

    public void setSaleId(Long id) {
        if(id == null || id <= 0L) {
            throw new IllegalArgumentException("Fail! Could not build a sale object with null or negative ID!");
        }
        this.id = id;
    }

    public List<Item> getSaleItemsList() {
        return itemsList;
    }

    public void setSaleItemsList(List<Item> itemsList) {
        if (itemsList == null || itemsList.isEmpty()) {
            throw new IllegalArgumentException("Fail! Could not build a sale object with a null or empty list!");
        }
        this.itemsList = itemsList;
    }

    public Salesman getSaleSalesman() {
        return salesman;
    }

    public void setSaleSalesman(Salesman salesman) {
        if (salesman == null) {
            throw new IllegalArgumentException("Fail! Could not build a sale object with a null Salesman!");
        }
        this.salesman = salesman;
    }

    public BigDecimal getSaleTotalCost() {
        return totalCost;
    }

    public void setSaleTotalCost() {
        if(itemsList.isEmpty())
        {
            throw new IllegalArgumentException("Fail! Could not calculate the sale's total cost without items!");
        }
        for (Item item : itemsList) {
            if(item.getTotalCost().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Fail! Could not calculate the sale's total cost as there are item's costs not calculated yet!");
            }
            totalCost = totalCost.add(item.getTotalCost());
        }
    }

    @Override
    public String toString() {
        return "Sale #ID: " + id +
                "\n\nItems: \n\n" + itemsList +
                "\n\nSalesman's name: '" + salesman.getSalesmanName() + "'" +
                "\nTotal cost: " + totalCost +  "\n";
    }
}
