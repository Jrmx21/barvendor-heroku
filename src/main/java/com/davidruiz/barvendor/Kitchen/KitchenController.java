package com.davidruiz.barvendor.Kitchen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.davidruiz.barvendor.Orders.OrderService;
import com.davidruiz.barvendor.Products.ProductModel.Categoria;
import com.davidruiz.barvendor.Orders.OrderModel;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class KitchenController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/kitchen/orders")
    public String viewOrders(Model model) {
        List<OrderModel> allOrders = orderService.getAllOrders();
        
        List<OrderModel> filteredOrders = allOrders.stream()
                .filter(order -> !order.isListoParaServir())
                .filter(order -> order.getProducts().stream().anyMatch(product ->
                        !product.getCategoria().equals(Categoria.Bebida) && !product.getCategoria().equals(Categoria.Entrante)))
                .collect(Collectors.toList());

        model.addAttribute("orders", filteredOrders);
        return "kitchen";
    }

    @GetMapping("/kitchen/orders/ready")
    public String viewReadyOrders(Model model) {
        List<OrderModel> readyOrders = orderService.getReadyOrders();
        List<OrderModel> filteredOrders = readyOrders.stream()
                .filter(order -> order.getProducts().stream().anyMatch(product ->
                        !product.getCategoria().equals(Categoria.Bebida) && !product.getCategoria().equals(Categoria.Entrante)))
                .collect(Collectors.toList());
        model.addAttribute("orders", filteredOrders);
        return "readyOrders";
    }

    @PostMapping("/kitchen/orders/{id}/ready")
    public String setOrderReady(@PathVariable Long id) {
        orderService.markOrderAsReady(id);
        return "redirect:/kitchen/orders";
    }

    @PostMapping("/kitchen/orders/{id}/restore")
    public String restoreOrder(@PathVariable Long id) {
        orderService.restoreOrder(id);
        return "redirect:/kitchen/orders/ready";
    }
}
