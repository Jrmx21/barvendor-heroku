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
        LocalDate endDate = LocalDate.now().plusDays(durationDays);
        List<ProductModel> products = productRepository.findAll();
        for (ProductModel product : products) {
            if (!product.isDiscountActive() || (product.getDiscountEndDate() != null && product.getDiscountEndDate().isBefore(LocalDate.now()))) {
                if (!product.isDiscountActive()) {
                    product.setOriginalPrice(product.getPrecio());
                }
                double discountedPrice = product.getPrecio() * (1 - (discountPercentage / 100));
                discountedPrice = Math.round(discountedPrice * 100.0) / 100.0;
                product.setPrecio(discountedPrice);
                product.setDiscountPercentage(discountPercentage);
                product.setDiscountEndDate(endDate);
                product.setDiscountActive(true);
                productRepository.save(product);
            }
        }
    }

    public void removeTemporaryDiscount() {
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
    public void disableProductById(Long id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductModel productModel = product.get();
            productModel.setActive(false);
            productRepository.save(productModel);
        }
    }

    public void enableProductById(Long id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductModel productModel = product.get();
            productModel.setActive(true);
            productRepository.save(productModel);
        }
    }
    public List<ProductModel> getDisabledProducts() {
        return productRepository.findAll().stream()
                .filter(product -> !product.isActive())
                .collect(Collectors.toList());
    }
    public void removeDiscountManually() {
        List<ProductModel> products = productRepository.findAll();
        for (ProductModel product : products) {
            if (product.isDiscountActive()) {
                product.setPrecio(product.getOriginalPrice());
                product.setDiscountPercentage(0);
                product.setDiscountEndDate(null);
                product.setDiscountActive(false);
                productRepository.save(product);
            }
        }
    }
}
