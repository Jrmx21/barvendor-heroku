package com.davidruiz.barvendor.Orders;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public OrderModel getOrderById(Long id) {
        Optional<OrderModel> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public OrderModel createOrder(OrderModel order) {
        // Puedes agregar aquí la lógica para validar el pedido antes de guardarlo
        return orderRepository.save(order);
    }

    public OrderModel updateOrder(Long id, OrderModel updatedOrder) {
        Optional<OrderModel> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            updatedOrder.setId(id); // Aseguramos que el ID del pedido actualizado sea el mismo que el original
            return orderRepository.save(updatedOrder);
        } else {
            return null; // Devolvemos null si el pedido no existe
        }
    }

    public boolean deleteOrder(Long id) {
        Optional<OrderModel> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false; // Devolvemos false si el pedido no existe
        }
    }
}
