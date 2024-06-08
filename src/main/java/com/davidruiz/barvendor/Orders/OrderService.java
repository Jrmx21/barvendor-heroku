package com.davidruiz.barvendor.Orders;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidruiz.barvendor.Products.ProductModel;

import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderModel> getReadyOrders() {
        return orderRepository.findAll().stream()
                .filter(OrderModel::isListoParaServir)
                .collect(Collectors.toList());
    }

    public OrderModel getOrderById(Long id) {
        Optional<OrderModel> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public OrderModel createOrder(OrderModel order) {
        return orderRepository.save(order);
    }

    public List<OrderModel> obtenerPedidosRecientes() {
        return orderRepository.findTop10ByOrderByFechaDesc();
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

    public Map<ProductModel, Integer> getProductCounts() {
        List<OrderModel> orders = getAllOrders();
        Map<ProductModel, Integer> productCounts = new HashMap<>();

        // Iterar sobre todos los pedidos
        for (OrderModel order : orders) {
            // Iterar sobre los productos de cada pedido
            for (ProductModel product : order.getProducts()) {
                // Incrementar el contador del producto
                productCounts.put(product, productCounts.getOrDefault(product, 0) + 1);
            }
        }

        // Ordenar el mapa por la cantidad de veces que han sido pedidos los productos
        return productCounts.entrySet().stream()
                .sorted(Map.Entry.<ProductModel, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new)); // LinkedHashMap para mantener el orden
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
