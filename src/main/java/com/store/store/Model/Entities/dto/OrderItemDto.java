package com.store.store.Model.Entities.dto;

import com.store.store.Model.Entities.Bicycle;
import com.store.store.Model.Entities.Order;
import java.math.BigDecimal;

public class OrderItemDto {

    private Integer orderId;

    private Integer bicycleId;

    private Integer quantity;

    private Long price;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(Integer bicycleId) {
        this.bicycleId = bicycleId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
