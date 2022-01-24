package com.damico958.desafio_tecnico.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {
    static final int BIG_DECIMAL_SCALE = 2;
    private Long id;
    private Long quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalCost;

    public Item() {

    }

    public Long getItemId() {
        return id;
    }

    public void setItemId(Long id) {
        if(id == null || id <= 0L) {
            throw new IllegalArgumentException("Fail! Could not build an item object with null or negative ID!");
        }
        this.id = id;
    }

    public Long getItemQuantity() {
        return quantity;
    }

    public void setItemQuantity(Long quantity) {
        if(quantity == null || quantity < 0L) {
            throw new IllegalArgumentException("Fail! Could not build an item object with null or negative quantity!");
        }
        this.quantity = quantity;
    }

    public BigDecimal getItemUnitPrice() {
        return unitPrice;
    }

    public void setItemUnitPrice(BigDecimal unitPrice) {
        if(unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Fail! Could not build an item object with null or negative unit price!");
        }
        this.unitPrice = unitPrice.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost() {
        if((unitPrice.compareTo(BigDecimal.ZERO) > 0) && (quantity > 0)) {
            this.totalCost = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        else {
            throw new IllegalArgumentException("Fail! Could not calculate the item's total cost without a valid price and quantity!");
        }
    }

    @Override
    public String toString() {
        return "Item #ID: " + id +
                "\nQuantity: " + quantity +
                "\nPrice: " + unitPrice +
                "\nTotal cost: " + totalCost + "\n";
    }
}
