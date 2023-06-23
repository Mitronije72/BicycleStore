package com.store.store.Services;

import com.store.store.Model.Entities.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(int id);

    Order saveOrder(Order order);

    void deleteOrder(int id);
}

