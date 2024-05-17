package com.davidruiz.barvendor.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private IProductRepository productRepository;

    // Obtener todos los productos
    public ArrayList<ProductModel> getProducts() {
        return (ArrayList<ProductModel>) productRepository.findAll();
    }

    // Guardar un nuevo producto
    public ProductModel saveProduct(ProductModel product) {
        return productRepository.save(product);
    }

    // Obtener un producto por su ID
    public Optional<ProductModel> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Actualizar un producto
    public ProductModel updateProduct(Long id, ProductModel product) {
        ProductModel productToUpdate = productRepository.findById(id).orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setNombreProducto(product.getNombreProducto());
            productToUpdate.setCategoria(product.getCategoria());
            productToUpdate.setAlergenos(product.getAlergenos());
            productToUpdate.setPrecio(product.getPrecio());
            productToUpdate.setExistencias(product.isExistencias());
            return productRepository.save(productToUpdate);
        }
        return null; // Manejar el caso en que el producto no se encuentre
    }

    // Eliminar un producto por su ID
    public boolean deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<ProductModel> getProductsByCategory(ProductModel.Categoria categoria) {
        // Obtiene todos los productos desde el repositorio
        List<ProductModel> allProducts = productRepository.findAll();

        // Filtra los productos por la categoría especificada
        List<ProductModel> productsByCategory = allProducts.stream()
                .filter(product -> product.getCategoria() == categoria)
                .collect(Collectors.toList());

        return productsByCategory;
    }

    public void applyTemporaryDiscount(double discountPercentage, int durationDays) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100");
        }

        if (durationDays <= 0) {
            throw new IllegalArgumentException("La duración del descuento debe ser mayor que 0");
        }

        LocalDate endDate = LocalDate.now().plusDays(durationDays);

        List<ProductModel> products = productRepository.findAll();
        for (ProductModel product : products) {
            // Aplicar el descuento solo si no hay descuento actualmente
            if (product.getDiscountEndDate() == null) {
                double currentPrice = product.getPrecio();
                double discountedPrice = currentPrice * (1 - (discountPercentage / 100));
                discountedPrice = Math.round(discountedPrice * 100.0) / 100.0; // Redondear a dos decimales
                product.setPrecio(discountedPrice);
                product.setDiscountPercentage(discountPercentage); // Guardar el porcentaje de descuento
                product.setDiscountEndDate(endDate); // Agregar la fecha de finalización del descuento
                productRepository.save(product);
            }
        }
    }


}
