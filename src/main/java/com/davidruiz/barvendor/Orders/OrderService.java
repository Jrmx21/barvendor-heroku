package com.davidruiz.barvendor.Orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidruiz.barvendor.Products.ProductModel;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderModel> getReadyOrders() {
        return orderRepository.findByListoParaServir(true);
    }

    public OrderModel getOrderById(Long id) {
        Optional<OrderModel> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public OrderModel createOrder(OrderModel order) {
        return orderRepository.save(order);
    }

    public OrderModel updateOrder(Long id, OrderModel updatedOrder) {
        Optional<OrderModel> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            updatedOrder.setId(id);
            return orderRepository.save(updatedOrder);
        } else {
            return null;
        }
    }

    public boolean deleteOrder(Long id) {
        Optional<OrderModel> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Map<ProductModel, Integer> getProductCounts() {
        List<OrderModel> orders = getAllOrders();
        Map<ProductModel, Integer> productCounts = new HashMap<>();

        for (OrderModel order : orders) {
            for (ProductModel product : order.getProducts()) {
                productCounts.put(product, productCounts.getOrDefault(product, 0) + 1);
            }
        }

        return productCounts.entrySet().stream()
                .sorted((Map.Entry.<ProductModel, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, HashMap::new));
    }

    public void markOrderAsReady(Long id) {
        OrderModel order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setListoParaServir(true);
        orderRepository.save(order);
    }

    public void restoreOrder(Long id) {
        OrderModel order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setListoParaServir(false);
        orderRepository.save(order);
    }
}
