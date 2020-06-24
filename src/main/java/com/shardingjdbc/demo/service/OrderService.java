package com.shardingjdbc.demo.service;

import com.shardingjdbc.demo.pojo.Order;

import java.util.List;

public interface OrderService {
    public boolean addOneOrder(Order userOne);
    public List<Order> getAllOrder();
}
