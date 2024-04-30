package com.davidruiz.barvendor.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    // Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<OrderModel>> getAllOrders() {
        List<OrderModel> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(orders);
    }


    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable("id") Long id) {
        OrderModel order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok().body(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<OrderModel> createOrder(@RequestBody OrderModel order) {
        OrderModel createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // Actualizar un pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<OrderModel> updateOrder(@PathVariable("id") Long id, @RequestBody OrderModel order) {
        OrderModel updatedOrder = orderService.updateOrder(id, order);
        if (updatedOrder != null) {
            return ResponseEntity.ok().body(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
