package com.davidruiz.barvendor.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    // Aquí puedes agregar métodos personalizados si necesitas consultas específicas
    List<OrderModel> findTop10ByOrderByFechaDesc();
    List<OrderModel> findByListoParaServir(boolean listoParaServir);

}

