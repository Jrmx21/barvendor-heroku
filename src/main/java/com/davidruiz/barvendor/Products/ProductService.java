package com.davidruiz.barvendor.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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
}
