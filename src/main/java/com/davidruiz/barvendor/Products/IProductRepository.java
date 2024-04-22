package com.davidruiz.barvendor.Products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductModel, Long> {
    // Puedes añadir métodos personalizados de consulta si es necesario
}
