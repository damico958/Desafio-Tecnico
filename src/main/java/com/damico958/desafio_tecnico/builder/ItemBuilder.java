package com.damico958.desafio_tecnico.builder;

import com.damico958.desafio_tecnico.model.Item;

import java.math.BigDecimal;

public class ItemBuilder {
    private final Item item;

    public ItemBuilder() {
        this.item = new Item();
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public ItemBuilder withId(Long id) {
        this.item.setItemId(id);
        return this;
    }

    public ItemBuilder withQuantity(Long quantity) {
        this.item.setItemQuantity(quantity);
        return this;
    }

    public ItemBuilder withUnitPrice(BigDecimal price) {
        this.item.setItemUnitPrice(price);
        return this;
    }

    public Item build() {
        if(item.getItemId() == null) {
            throw new IllegalArgumentException("Fail! Can't build an Item object without providing an ID!");
        }
        if(item.getItemQuantity() == null) {
            throw new IllegalArgumentException("Fail! Can't build an Item object without providing a quantity!");
        }
        if(item.getItemUnitPrice() == null) {
            throw new IllegalArgumentException("Fail! Can't build an Item object without providing a unit price!");
        }
        this.item.setTotalCost();
        return this.item;
    }
}
