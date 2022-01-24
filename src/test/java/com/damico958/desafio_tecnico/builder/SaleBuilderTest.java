package com.damico958.desafio_tecnico.builder;

import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.model.Sale;
import com.damico958.desafio_tecnico.model.Salesman;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SaleBuilderTest {
    private final BigDecimal unitPrice = new BigDecimal("3.00");
    private final Item item = ItemBuilder.builder()
            .withId(1L)
            .withQuantity(2L)
            .withUnitPrice(unitPrice)
            .build();
    private List<Item> itemsList = new ArrayList<Item>();
    private final BigDecimal salary = new BigDecimal("1500.00");

    private final Salesman salesman = SalesmanBuilder.builder()
            .withCpf("48397381091")
            .withName("Felipe")
            .withSalary(salary)
            .build();

    @Test
    public void shouldBuildSaleWithCompleteInfo() {
        itemsList.add(item);
        BigDecimal totalCost = new BigDecimal("6.00");

        Sale sale = SaleBuilder.builder()
                .withId(1L)
                .withItemsList(itemsList)
                .withSalesman(salesman)
                .build();

        assertEquals(1L, sale.getSaleId());
        assertEquals(itemsList, sale.getSaleItemsList());
        assertEquals(salesman, sale.getSaleSalesman());
        assertEquals(totalCost, sale.getSaleTotalCost());
        assertEquals(1L, sale.getSaleItemsList().get(0).getItemId());
        assertEquals(2L, sale.getSaleItemsList().get(0).getItemQuantity());
        assertEquals(unitPrice, sale.getSaleItemsList().get(0).getItemUnitPrice());
        assertEquals(totalCost, sale.getSaleItemsList().get(0).getTotalCost());
        assertEquals("48397381091", sale.getSaleSalesman().getSalesmanCpf());
        assertEquals("Felipe", sale.getSaleSalesman().getSalesmanName());
        assertEquals(salary, sale.getSaleSalesman().getSalesmanSalary());
    }

    @Test
    public void shouldNotAcceptNullOrNegativeParameters() {
        itemsList.add(item);
        String nullIdExceptionMessage = "Fail! Could not build a sale object with null or negative ID!";
        String nullItemsListExceptionMessage = "Fail! Could not build a sale object with a null or empty list!";
        String nullSalesmanExceptionMessage = "Fail! Could not build a sale object with a null Salesman!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withId(null)
                .withItemsList(itemsList)
                .withSalesman(salesman)
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withId(1L)
                .withItemsList(null)
                .withSalesman(salesman)
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withId(1L)
                .withItemsList(itemsList)
                .withSalesman(null)
                .build());
        assertEquals(nullIdExceptionMessage, exceptionOne.getMessage());
        assertEquals(nullItemsListExceptionMessage, exceptionTwo.getMessage());
        assertEquals(nullSalesmanExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotAcceptEmptyParameters() {
        String emptyListExceptionMessage = "Fail! Could not build a sale object with a null or empty list!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withId(1L)
                .withItemsList(itemsList)
                .withSalesman(salesman)
                .build());
        assertEquals(emptyListExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotBuildSaleWithoutId() {
        itemsList.add(item);
        String missingIdExceptionMessage = "Fail! Can't build a Sale object without providing an ID!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withItemsList(itemsList)
                .withSalesman(salesman)
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withSalesman(salesman)
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withItemsList(itemsList)
                .build());
        assertEquals(missingIdExceptionMessage, exceptionOne.getMessage());
        assertEquals(missingIdExceptionMessage, exceptionTwo.getMessage());
        assertEquals(missingIdExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotBuildSaleWithoutItemsList() {
        itemsList.add(item);
        String missingIdExceptionMessage = "Fail! Can't build a Sale object without providing an items list!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withId(1L)
                .withSalesman(salesman)
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withId(1L)
                .build());
        assertEquals(missingIdExceptionMessage, exceptionOne.getMessage());
        assertEquals(missingIdExceptionMessage, exceptionTwo.getMessage());
    }

    @Test
    public void shouldNotBuildSaleWithoutSalesman() {
        itemsList.add(item);
        String missingIdExceptionMessage = "Fail! Can't build a Sale object without providing a Salesman!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> SaleBuilder.builder()
                .withId(1L)
                .withItemsList(itemsList)
                .build());
        assertEquals(missingIdExceptionMessage, exceptionOne.getMessage());
    }
}
