package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.repository.CurrentFileDataRepository;
import com.damico958.desafio_tecnico.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ItemService.class, ItemRepository.class, CurrentFileDataService.class, CurrentFileDataRepository.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    private Long id = 1L;
    private final Long quantity = 1L;
    private final BigDecimal unitPrice = new BigDecimal("2.50");

    @Test
    public void shouldAcceptValidId() {
        Item item = itemService.save(id, quantity, unitPrice);
        assertEquals(id, item.getItemId());
        assertEquals(quantity, item.getItemQuantity());
        assertEquals(unitPrice, item.getItemUnitPrice());
    }

    @Test
    public void shouldNotAcceptInvalidId() {
        id = 0L;
        String invalidIdExceptionMessage = "Fail! Could not build an item object with null or negative ID!";
        IllegalArgumentException exceptionOne = assertThrows(IllegalArgumentException.class, () -> itemService.save(id, quantity, unitPrice));
        id = -1L;
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> itemService.save(id, quantity, unitPrice));
        assertEquals(invalidIdExceptionMessage, exceptionOne.getMessage());
        assertEquals(invalidIdExceptionMessage, exceptionTwo.getMessage());
    }

    @Test
    public void shouldFindItemById() {
        itemService.save(id, quantity, unitPrice);
        Item item = itemService.findItemById(1L);
        assertEquals(id, item.getItemId());
        assertEquals(quantity, item.getItemQuantity());
        assertEquals(unitPrice, item.getItemUnitPrice());
    }

    @Test
    public void shouldNotFindItemById() {
        String itemNotFoundExceptionMessage = "Fail! Could not find an Item with this ID!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> itemService.findItemById(1L));
        assertEquals(itemNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldRemoveItemById() {
        itemService.save(id, quantity, unitPrice);
        Item item = itemService.findItemById(1L);
        assertEquals(id, item.getItemId());
        assertEquals(quantity, item.getItemQuantity());
        assertEquals(unitPrice, item.getItemUnitPrice());
        itemService.remove(id);
        String itemNotFoundExceptionMessage = "Fail! Could not find an Item with this ID!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> itemService.findItemById(1L));
        assertEquals(itemNotFoundExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotRemoveItemNotFoundById() {
        String itemNotRemovedExceptionMessage = "Fail! Could not remove an Item with this ID as it doesn't exist!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> itemService.remove(1L));
        assertEquals(itemNotRemovedExceptionMessage, exceptionOne.getMessage());
    }

    @Test
    public void shouldNotSaveIdAlreadyRegistered() {
        itemService.save(id, quantity, unitPrice);
        String duplicateItemExceptionMessage = "Fail! Could not create new Item object as this ID is already registered!";
        InvalidBehaviorException exceptionOne = assertThrows(InvalidBehaviorException.class, () -> itemService.save(id, quantity, unitPrice));
        assertEquals(duplicateItemExceptionMessage, exceptionOne.getMessage());
    }
}
