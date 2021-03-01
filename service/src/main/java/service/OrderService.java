package service;

import model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrders(String uid);
    int addOrder(dao.model.Order order);
}
