package com.damico958.desafio_tecnico.builder;

import com.damico958.desafio_tecnico.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemBuilderTest {

    @Test
    public void shouldBuildItemWithCompleteInfo() {
        BigDecimal unitPrice = new BigDecimal("1.50");
        BigDecimal totalCost = new BigDecimal("3.00");
        Item item = ItemBuilder.builder()
                .withId(1L)
                .withQuantity(2L)
                .withUnitPrice(unitPrice)
                .build();

        assertEquals(1L, item.getItemId());
        assertEquals(2L, item.getItemQuantity());
        assertEquals(unitPrice, item.getItemUnitPrice());
        assertEquals(totalCost, item.getTotalCost());
    }

    @Test
    public void shouldNotAcceptNullOrNegativeParameters() {
        String nullIdExceptionMessage = "Fail! Could not build an item object with null or negative ID!";
        String nullQuantityExceptionMessage = "Fail! Could not build an item object with null or negative quantity!";
        String nullUnitPriceExceptionMessage = "Fail! Could not build an item object with null or negative unit price!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withId(null)
                .withQuantity(1L)
                .withUnitPrice(new BigDecimal("1.50"))
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withId(1L)
                .withQuantity(null)
                .withUnitPrice(new BigDecimal("1.50"))
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withId(1L)
                .withQuantity(1L)
                .withUnitPrice(null)
                .build());
        assertEquals(nullIdExceptionMessage, exceptionOne.getMessage());
        assertEquals(nullQuantityExceptionMessage, exceptionTwo.getMessage());
        assertEquals(nullUnitPriceExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotBuildItemWithoutId() {
        String missingIdExceptionMessage = "Fail! Can't build an Item object without providing an ID!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withQuantity(1L)
                .withUnitPrice(new BigDecimal("1.50"))
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withUnitPrice(new BigDecimal("1.50"))
                .build());
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withQuantity(1L)
                .build());
        assertEquals(missingIdExceptionMessage, exceptionOne.getMessage());
        assertEquals(missingIdExceptionMessage, exceptionTwo.getMessage());
        assertEquals(missingIdExceptionMessage, exceptionThree.getMessage());
    }

    @Test
    public void shouldNotBuildItemWithoutQuantity() {
        String missingQuantityExceptionMessage = "Fail! Can't build an Item object without providing a quantity!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withId(1L)
                .withUnitPrice(new BigDecimal("1.50"))
                .build());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withId(1L)
                .build());
        assertEquals(missingQuantityExceptionMessage, exceptionOne.getMessage());
        assertEquals(missingQuantityExceptionMessage, exceptionTwo.getMessage());
    }

    @Test
    public void shouldNotBuildItemWithoutUnitPrice() {
        String missingUnitPriceExceptionMessage = "Fail! Can't build an Item object without providing a unit price!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> ItemBuilder.builder()
                .withId(1L)
                .withQuantity(1L)
                .build());
        assertEquals(missingUnitPriceExceptionMessage, exceptionOne.getMessage());
    }


}
