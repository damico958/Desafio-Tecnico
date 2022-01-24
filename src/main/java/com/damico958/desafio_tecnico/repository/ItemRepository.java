package com.damico958.desafio_tecnico.repository;

import com.damico958.desafio_tecnico.model.Item;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Map<Long, Item> itemRepository;

    public ItemRepository() {
        this.itemRepository = new HashMap<Long, Item>();
    }

    public void save(Item item) {
        itemRepository.put(item.getItemId(), item);
    }

    public Item findItemById(Long id) {
        return itemRepository.get(id);
    }

    public boolean existsById(Long id) {
        return itemRepository.containsKey(id);
    }

    public Item remove(Long id) {
        Item foundItem = itemRepository.get(id);
        this.itemRepository.remove(id);
        return foundItem;
    }
}
