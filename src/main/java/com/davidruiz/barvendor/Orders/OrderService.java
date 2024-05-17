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

    public OrderModel getOrderById(Long id) {
        Optional<OrderModel> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public OrderModel createOrder(OrderModel order) {
        // Puedes agregar aquí la lógica para validar el pedido antes de guardarlo
        return orderRepository.save(order);
    }

    // Método para obtener los pedidos más recientes
    public List<OrderModel> obtenerPedidosRecientes() {
        // Aquí llama al repositorio para obtener los pedidos más recientes
        return orderRepository.findTop10ByOrderByFechaDesc(); // Suponiendo que tienes un método en tu repositorio para
                                                              // obtener los 10 pedidos más recientes

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
                .sorted((Map.Entry.<ProductModel, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, HashMap::new));
    }
}
