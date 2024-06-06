package com.davidruiz.barvendor.Kitchen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.davidruiz.barvendor.Orders.OrderService;
import com.davidruiz.barvendor.Products.ProductModel.Categoria;
import com.davidruiz.barvendor.Orders.OrderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String viewReadyOrders(
            @RequestParam(value = "fecha", required = false) String fecha,
            Model model) {
        List<OrderModel> readyOrders = orderService.getReadyOrders();
        
        if (fecha != null && !fecha.isEmpty()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = format.parse(fecha);
                readyOrders = readyOrders.stream()
                        .filter(order -> {
                            if (order.getFecha() != null) {
                                SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String orderFecha = dbFormat.format(order.getFecha());
                                String filterFecha = dbFormat.format(parsedDate);
                                return orderFecha.equals(filterFecha);
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("orders", readyOrders);
        model.addAttribute("fecha", fecha);
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
