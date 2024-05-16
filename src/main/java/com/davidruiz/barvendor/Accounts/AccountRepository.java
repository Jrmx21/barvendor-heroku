package com.davidruiz.barvendor.Accounts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    List<AccountModel> findByPagadoFalse();
    List<AccountModel> findByPagadoTrue();
}
