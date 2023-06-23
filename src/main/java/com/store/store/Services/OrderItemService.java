package com.store.store.Services;

import com.store.store.Model.Entities.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItemById(int id);

    OrderItem saveOrderItem(OrderItem orderItem);

    void deleteOrderItem(int id);
}

