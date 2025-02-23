package com.davidruiz.barvendor.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.davidruiz.barvendor.Orders.OrderModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<AccountModel> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/abiertas")
    public ResponseEntity<List<AccountModel>> getCuentasNoPagadas() {
        List<AccountModel> cuentasNoPagadas = accountService.getCuentasNoPagadas();
        return ResponseEntity.ok(cuentasNoPagadas);
    }

    @GetMapping("/cerradas")
public ResponseEntity<List<AccountModel>> getClosedAccounts() {
    List<AccountModel> closedAccounts = accountService.getClosedAccounts();
    return ResponseEntity.ok(closedAccounts);
}
    @GetMapping("/{id}/pedidos")
    public ResponseEntity<List<OrderModel>> getOrdersForAccount(@PathVariable Long id) {
        Optional<AccountModel> optionalAccount = accountService.getAccountById(id);
        if (optionalAccount.isPresent()) {
            List<OrderModel> orders = optionalAccount.get().getOrders();
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public Optional<AccountModel> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping
    public AccountModel createAccount(@RequestBody AccountModel account) {
        return accountService.createAccount(account);
    }

    @PutMapping("/{id}")
    public AccountModel updateAccount(@PathVariable Long id, @RequestBody AccountModel updatedAccount) {
        return accountService.updateAccount(id, updatedAccount);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @PutMapping("/{id}/pagar") // Ruta para marcar la cuenta como pagada
    public ResponseEntity<AccountModel> markAccountAsPaid(@PathVariable Long id) {
        Optional<AccountModel> optionalAccount = accountService.getAccountById(id);
        if (optionalAccount.isPresent()) {
            AccountModel account = optionalAccount.get();
            account.setPagado(true); // Marcar la cuenta como pagada
            // Aquí podrías realizar otras acciones, como establecer la fecha de pago, etc.
           account.setFechaPago(new Date());
            accountService.updateAccount(id, account); // Actualizar la cuenta en la base de datos
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
