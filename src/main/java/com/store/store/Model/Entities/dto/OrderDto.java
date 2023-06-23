package com.store.store.Model.Entities.dto;

import com.store.store.Model.Entities.Customer;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class OrderDto {
    private float totalAmount;

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
