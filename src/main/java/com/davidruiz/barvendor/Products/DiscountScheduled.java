package com.davidruiz.barvendor.Products;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class DiscountScheduled {

        @Autowired
    private IProductRepository productRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Esta expresión cron ejecutará la tarea a medianoche todos los días
    public void removeExpiredDiscounts() {
        List<ProductModel> products = productRepository.findAll();
        for (ProductModel product : products) {
            if (product.isDiscountActive() && product.getDiscountEndDate() != null && product.getDiscountEndDate().isBefore(LocalDate.now())) {
                product.setPrecio(product.getOriginalPrice());
                product.setDiscountPercentage(0);
                product.setDiscountEndDate(null);
                product.setDiscountActive(false);
                productRepository.save(product);
            }
        }
    }
}
