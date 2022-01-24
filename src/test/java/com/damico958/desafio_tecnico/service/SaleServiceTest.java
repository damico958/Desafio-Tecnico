package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.builder.ItemBuilder;
import com.damico958.desafio_tecnico.builder.SalesmanBuilder;
import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.model.Sale;
import com.damico958.desafio_tecnico.model.Salesman;
import com.damico958.desafio_tecnico.repository.CurrentFileDataRepository;
import com.damico958.desafio_tecnico.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SaleService.class, SaleRepository.class, CurrentFileDataService.class, CurrentFileDataRepository.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SaleServiceTest {
    @Autowired
    private SaleService saleService;

    private Long id = 1L;
    private final Long itemId = 1L;
    private final Long quantity = 1L;
    private final BigDecimal unitPrice = new BigDecimal("3.00");
    private final Item item = new ItemBuilder().builder()
            .withId(itemId)
            .withQuantity(quantity)
            .withUnitPrice(unitPrice)
            .build();
    private List<Item> itemsList = new ArrayList<Item>();
    private final String cpf = "36421185002";
    private final String name = "Felipe";
    private final BigDecimal salary = new BigDecimal("1500.00");
    private final Salesman salesman = new SalesmanBuilder().builder()
            .withCpf(cpf)
            .withName(name)
            .withSalary(salary)
            .build();

    @Test
    public void shouldAcceptValidId() {
        itemsList.add(item);
        assertTrue(saleService.save(id, itemsList, salesman));
    }

    @Test
    public void shouldNotAcceptInvalidId() {
        id = 0L;
        String invalidIdExceptionMessage = "Fail! Could not build a sale object with null or negative ID!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> saleService.save(id, itemsList, salesman));
        id = -1L;
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> saleService.save(id, itemsList, salesman));
        assertEquals(invalidIdExceptionMessage, exceptionOne.getMessage());
        assertEquals(invalidIdExceptionMessage, exceptionTwo.getMessage());
    }

    @Test
    public void shouldFindSaleById() {
        itemsList.add(item);
        saleService.save(id, itemsList, salesman);
        Sale sale = saleService.findSaleById(1L);
        assertEquals(id, sale.getSaleId());
        assertEquals(itemsList, sale.getSaleItemsList());
        assertEquals(salesman, sale.getSaleSalesman());
        assertEquals(itemId, sale.getSaleItemsList().get(0).getItemId());
        assertEquals(quantity, sale.getSaleItemsList().get(0).getItemQuantity());
        assertEquals(unitPrice, sale.getSaleItemsList().get(0).getItemUnitPrice());
        assertEquals(cpf, sale.getSaleSalesman().getSalesmanCpf());
        assertEquals(name, sale.getSaleSalesman().getSalesmanName());
        assertEquals(salary, sale.getSaleSalesman().getSalesmanSalary());
    }

    @Test
    public void shouldNotFindSaleById() {
        String saleNotFoundExceptionMessage = "Fail! Could not find a Sale with this ID!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> saleService.findSaleById(1L));
        assertEquals(saleNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldRemoveSaleById() {
        itemsList.add(item);
        saleService.save(id, itemsList, salesman);
        Sale sale = saleService.findSaleById(1L);
        assertEquals(id, sale.getSaleId());
        assertEquals(itemsList, sale.getSaleItemsList());
        assertEquals(salesman, sale.getSaleSalesman());
        saleService.remove(id);
        String saleNotFoundExceptionMessage = "Fail! Could not find a Sale with this ID!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> saleService.findSaleById(1L));
        assertEquals(saleNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotRemoveSaleNotFoundById() {
        String saleNotRemovedExceptionMessage = "Fail! Could not remove a Sale with this ID as it doesn't exist!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> saleService.remove(1L));
        assertEquals(saleNotRemovedExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotSaveIdAlreadyRegistered() {
        itemsList.add(item);
        saleService.save(id, itemsList, salesman);
        String duplicateSaleExceptionMessage = "Fail! Could not create new Sale object as this ID is already registered!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> saleService.save(id, itemsList, salesman));
        assertEquals(duplicateSaleExceptionMessage, exceptionOne.getMessage());
    }
}
