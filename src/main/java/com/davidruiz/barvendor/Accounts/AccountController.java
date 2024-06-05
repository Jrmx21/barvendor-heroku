package com.davidruiz.barvendor.Accounts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.davidruiz.barvendor.Tables.TableModel;
import com.davidruiz.barvendor.Tables.TableService;
import com.davidruiz.barvendor.Users.UserModel;
import com.davidruiz.barvendor.Users.UserService;

@Controller
public class AccountController {

    private AccountService accountService;
    private UserService userService;
    private TableService tableService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTableService(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/cuentas")
    public String getAllAccounts(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "pedido", required = false) Long pedido,
            @RequestParam(value = "mesa", required = false) Long mesa,
            @RequestParam(value = "fecha", required = false) String fecha,
            @RequestParam(value = "usuario", required = false) Long usuario,
            @RequestParam(value = "precioMin", required = false) Double precioMin,
            @RequestParam(value = "precioMax", required = false) Double precioMax,
            Model model) {

        List<AccountModel> accounts = accountService.getAllAccounts();

        // Filtrado por pedido
        if (pedido != null) {
            accounts = accounts.stream()
                    .filter(account -> account.getOrders().stream()
                            .anyMatch(order -> order.getId().equals(pedido)))
                    .collect(Collectors.toList());
        }

        // Filtrado por mesa
        if (mesa != null) {
            accounts = accounts.stream()
                    .filter(account -> account.getMesa() != null && account.getMesa().getId().equals(mesa))
                    .collect(Collectors.toList());
        }

        // Filtrado por fecha de pago (solo la parte de la fecha)
        if (fecha != null && !fecha.isEmpty()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = format.parse(fecha);
                accounts = accounts.stream()
                        .filter(account -> {
                            if (account.getFechaPago() != null) {
                                SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String accountFecha = dbFormat.format(account.getFechaPago());
                                String filterFecha = dbFormat.format(parsedDate);
                                return accountFecha.equals(filterFecha);
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
            accounts = accounts.stream()
                    .filter(account -> account.getOrders().stream()
                            .anyMatch(order -> order.getUser().getId().equals(usuario)))
                    .collect(Collectors.toList());
        }

        // Filtrado por rango de precio
        if (precioMin != null) {
            accounts = accounts.stream()
                    .filter(account -> account.getPrecioTotal() >= precioMin)
                    .collect(Collectors.toList());
        }
        if (precioMax != null) {
            accounts = accounts.stream()
                    .filter(account -> account.getPrecioTotal() <= precioMax)
                    .collect(Collectors.toList());
        }

        // Obtener todos los usuarios y mesas para los filtros
        List<UserModel> usuarios = userService.getAllUsers();
        List<TableModel> mesas = tableService.getAllTables();

        model.addAttribute("accounts", accounts);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("mesas", mesas);
        model.addAttribute("filterType", filterType);
        model.addAttribute("pedido", pedido);
        model.addAttribute("mesaSeleccionada", mesa);
        model.addAttribute("fecha", fecha);
        model.addAttribute("usuarioSeleccionado", usuario);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        return "accounts"; // Nombre de la plantilla que crearemos para mostrar las cuentas
    }
}
