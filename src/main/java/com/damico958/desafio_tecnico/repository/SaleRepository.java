package com.damico958.desafio_tecnico.repository;

import com.damico958.desafio_tecnico.model.Sale;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SaleRepository {
    private final Map<Long, Sale> saleRepository;

    public SaleRepository() {
        this.saleRepository = new HashMap<Long, Sale>();
    }

    public void save(Sale sale) {
        saleRepository.put(sale.getSaleId(), sale);
    }

    public Sale findSaleById(Long id) {
        return saleRepository.get(id);
    }

    public boolean existsById(Long id) {
        return saleRepository.containsKey(id);
    }

    public Sale remove(Long id) {
        Sale foundSale = saleRepository.get(id);
        this.saleRepository.remove(id);
        return foundSale;
    }
}
