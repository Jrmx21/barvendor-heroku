package com.davidruiz.barvendor.Orders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.davidruiz.barvendor.Accounts.AccountModel;
import com.davidruiz.barvendor.Accounts.AccountService;
import com.davidruiz.barvendor.Products.ProductModel;
import com.davidruiz.barvendor.Tables.TableModel;
import com.davidruiz.barvendor.Tables.TableService;
import com.davidruiz.barvendor.Users.UserModel;
import com.davidruiz.barvendor.Users.UserService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/pedidos")
    public String getAllOrders(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "pedido", required = false) Long pedido,
            @RequestParam(value = "mesa", required = false) Long mesa,
            @RequestParam(value = "fecha", required = false) String fecha,
            @RequestParam(value = "usuario", required = false) Long usuario,
            @RequestParam(value = "cuenta", required = false) Long cuenta,
            @RequestParam(value = "precioMin", required = false) Double precioMin,
            @RequestParam(value = "precioMax", required = false) Double precioMax,
            Model model) {

        List<OrderModel> orders = orderService.getAllOrders();

        // Filtrado por ID de pedido
        if (pedido != null) {
            orders = orders.stream()
                    .filter(order -> order.getId().equals(pedido))
                    .collect(Collectors.toList());
        }

        // Filtrado por mesa
        if (mesa != null) {
            orders = orders.stream()
                    .filter(order -> order.getCuenta().getMesa() != null && order.getCuenta().getMesa().getId().equals(mesa))
                    .collect(Collectors.toList());
        }

        // Filtrado por fecha
        if (fecha != null && !fecha.isEmpty()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = format.parse(fecha);
                orders = orders.stream()
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

        // Filtrado por usuario
        if (usuario != null) {
            orders = orders.stream()
                    .filter(order -> order.getUser().getId().equals(usuario))
                    .collect(Collectors.toList());
        }
if (cuenta != null) {
            orders = orders.stream()
                    .filter(order -> order.getCuenta().getId().equals(cuenta))
                    .collect(Collectors.toList());
        }

        // Filtrado por rango de precio
        if (precioMin != null) {
            orders = orders.stream()
                    .filter(order -> order.calcularPrecioTotal() >= precioMin)
                    .collect(Collectors.toList());
        }
        if (precioMax != null) {
            orders = orders.stream()
                    .filter(order -> order.calcularPrecioTotal() <= precioMax)
                    .collect(Collectors.toList());
        }

        // Obtener todos los usuarios, mesas y cuentas para los filtros
        List<UserModel> usuarios = userService.getAllUsers();
        List<TableModel> mesas = tableService.getAllTables();
        List<AccountModel> cuentas = accountService.getAllAccounts();

        model.addAttribute("orders", orders);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("mesas", mesas);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("filterType", filterType);
        model.addAttribute("pedido", pedido);
        model.addAttribute("mesaSeleccionada", mesa);
        model.addAttribute("fecha", fecha);
        model.addAttribute("usuarioSeleccionado", usuario);
        model.addAttribute("cuentaSeleccionada", cuenta);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        return "orders"; // Nombre de la plantilla para mostrar los pedidos
    }
      @GetMapping("/productos-mas-pedidos")
    public String getPopularProducts(Model model) {
        Map<ProductModel, Integer> productCounts = orderService.getProductCounts();
        model.addAttribute("productCounts", productCounts);
        return "popular_products"; // Nombre de la nueva plantilla para mostrar los productos más pedidos
    }
}

        //
