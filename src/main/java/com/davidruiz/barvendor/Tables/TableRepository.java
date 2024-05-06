package com.davidruiz.barvendor.Tables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<TableModel, Long> {

    // Métodos del repositorio
}
