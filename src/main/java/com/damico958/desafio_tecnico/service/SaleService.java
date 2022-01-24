package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.builder.SaleBuilder;
import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.model.Sale;
import com.damico958.desafio_tecnico.model.Salesman;
import com.damico958.desafio_tecnico.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CurrentFileDataService currentFileDataService;

    public boolean save(Long id, List<Item> itemList, Salesman salesman) {
        if(saleRepository.existsById(id)) {
            throw new InvalidBehaviorException("Fail! Could not create new Sale object as this ID is already registered!");
        }
        Sale sale = new SaleBuilder()
                .withId(id)
                .withItemsList(itemList)
                .withSalesman(salesman)
                .build();
        saleRepository.save(sale);
        salesman.addToSalesTotal(sale.getSaleTotalCost());
        currentFileDataService.saveSale(sale);
        return true;
    }

    public Sale findSaleById(Long id) {
        Sale foundSale = saleRepository.findSaleById(id);
        if(foundSale == null) {
            throw new InvalidBehaviorException("Fail! Could not find a Sale with this ID!");
        }
        return foundSale;
    }

    public Sale remove(Long id) {
        Sale foundSale = saleRepository.remove(id);
        if(foundSale == null) {
            throw new InvalidBehaviorException("Fail! Could not remove a Sale with this ID as it doesn't exist!");
        }
        return foundSale;
    }
}
