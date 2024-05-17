package com.davidruiz.barvendor.Orders;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.davidruiz.barvendor.Products.ProductModel;

@Controller

public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/pedidos")
    public String getAllOrders(Model model) {
        List<OrderModel> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders"; // Este es el nombre de la plantilla que crearemos para mostrar los pedidos
    }
    @GetMapping("/productos-mas-pedidos")
    public String getPopularProducts(Model model) {
        Map<ProductModel, Integer> productCounts = orderService.getProductCounts();
        model.addAttribute("productCounts", productCounts);
        return "popular_products"; // Nombre de la nueva plantilla para mostrar los productos más pedidos
    }
}
