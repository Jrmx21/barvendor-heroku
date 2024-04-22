package com.davidruiz.barvendor.Orders;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }
    
}
