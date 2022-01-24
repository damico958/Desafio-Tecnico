package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.builder.ItemBuilder;
import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.model.Item;
import com.damico958.desafio_tecnico.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CurrentFileDataService currentFileDataService;

    public Item save(Long id, Long quantity, BigDecimal unitPrice) {
        if(itemRepository.existsById(id)) {
            throw new InvalidBehaviorException("Fail! Could not create new Item object as this ID is already registered!");
        }
        Item item = new ItemBuilder()
                .withId(id)
                .withQuantity(quantity)
                .withUnitPrice(unitPrice)
                .build();
        itemRepository.save(item);
        currentFileDataService.saveItem(item);
        return item;
    }

    public Item findItemById(Long id) {
        Item foundItem = itemRepository.findItemById(id);
        if(foundItem == null) {
            throw new InvalidBehaviorException("Fail! Could not find an Item with this ID!");
        }
        return foundItem;
    }

    public Item remove(Long id) {
        Item foundItem = itemRepository.remove(id);
        if(foundItem == null) {
            throw new InvalidBehaviorException("Fail! Could not remove an Item with this ID as it doesn't exist!");
        }
        return foundItem;
    }
}
