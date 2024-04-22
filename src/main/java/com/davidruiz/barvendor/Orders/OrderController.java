package com.davidruiz.barvendor.Orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
