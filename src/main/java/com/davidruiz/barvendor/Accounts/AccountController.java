package com.davidruiz.barvendor.Accounts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        
        model.addAttribute("accounts", accounts);
        return "accounts"; // Nombre de la vista que mostrará las cuentas
    }
}
