package com.davidruiz.barvendor.Kitchen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.davidruiz.barvendor.Orders.OrderService;

@Controller
public class KitchenController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/kitchen/orders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "kitchen";
    }

    @PostMapping("/kitchen/orders/{id}/ready")
    public String setOrderReady(@PathVariable Long id) {
        orderService.markOrderAsReady(id);
        return "redirect:/kitchen/orders";
    }
}