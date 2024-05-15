package com.davidruiz.barvendor.Accounts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidruiz.barvendor.Orders.OrderModel;
import com.davidruiz.barvendor.Products.ProductModel;

@Controller
public class AccountController {

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/cuentas")
    public String getAllAccounts(Model model) {
   List<AccountModel> accounts = accountService.getAllAccounts();
        
        // Calcular la suma del precio total de los productos para cada cuenta
        for (AccountModel account : accounts) {
            double totalPrice = 0.0;
            for (OrderModel order : account.getOrders()) {
                for (ProductModel product : order.getProducts()) {
                    totalPrice += product.getPrecio();
                }
            }
            totalPrice = Math.round(totalPrice * 100.0) / 100.0;
            account.setPrecioTotal(totalPrice);
            
            accountService.saveAccount(account);
        }
        
        model.addAttribute("accounts", accounts);
        return "accounts";
    }
}
