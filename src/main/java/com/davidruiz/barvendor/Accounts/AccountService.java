package com.davidruiz.barvendor.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<AccountModel> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<AccountModel> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public AccountModel createAccount(AccountModel account) {
        return accountRepository.save(account);
    }
    public List<AccountModel> getCuentasNoPagadas() {
        return accountRepository.findByPagadoFalse();
    }
    public AccountModel updateAccount(Long id, AccountModel updatedAccount) {
        Optional<AccountModel> existingAccountOptional = accountRepository.findById(id);
        if (existingAccountOptional.isPresent()) {
            AccountModel existingAccount = existingAccountOptional.get();
            // Actualizar los campos necesarios del objeto existingAccount con los valores de updatedAccount
            // Por ejemplo:
            // existingAccount.setNombreProducto(updatedAccount.getNombreProducto());
            // existingAccount.setPrecioTotal(updatedAccount.getPrecioTotal());
            // ...
            return accountRepository.save(existingAccount);
        }
        return null; // Manejar el caso en que la cuenta no exista
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
