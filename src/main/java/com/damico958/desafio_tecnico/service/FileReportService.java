package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.model.FileReport;
import com.damico958.desafio_tecnico.model.Sale;
import com.damico958.desafio_tecnico.model.Salesman;
import com.damico958.desafio_tecnico.repository.CurrentFileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileReportService {
    private static final long MAX_SORTED_SALES_TO_SHOW = 1;
    private static final long MAX_SORTED_SALESMEN_TO_SHOW = 1;
    @Autowired
    CurrentFileDataRepository currentFileDataRepository;

    private final FileReport fileReport;

    public FileReportService() {
        this.fileReport = new FileReport();
    }

    public FileReport retrieveReportData() {
        this.fileReport.setSalesmenAmount(currentFileDataRepository.getSalesmenList().size());
        this.fileReport.setCustomersAmount(currentFileDataRepository.getCustomersList().size());
        this.fileReport.setMostExpensiveSale(sortMostExpensiveSales().get(0));
        this.fileReport.setWorstSalesmanEver(sortWorstSalesmenEver().get(0));
        return this.fileReport;
    }

    public List<Sale> sortMostExpensiveSales() {
        return currentFileDataRepository.getSalesList().stream().sorted(Comparator.comparing(Sale::getSaleTotalCost).reversed())
                .limit(MAX_SORTED_SALES_TO_SHOW)
                .collect(Collectors.toList());
    }

    public List<Salesman> sortWorstSalesmenEver() {
        return currentFileDataRepository.getSalesmenList().stream().sorted(Comparator.comparing(Salesman::getSalesTotal))
                .limit(MAX_SORTED_SALESMEN_TO_SHOW)
                .collect(Collectors.toList());
    }
}
