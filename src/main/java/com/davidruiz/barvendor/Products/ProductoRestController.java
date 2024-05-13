package com.davidruiz.barvendor.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private ProductService productService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productos = productService.getProducts();
        return ResponseEntity.ok().body(productos);
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable("id") Long id) {
        Optional<ProductModel> producto = productService.getProductById(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok().body(producto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
  @GetMapping("/categoria/{categoria}")
public List<ProductModel> getProductsByCategory(@PathVariable("categoria") String categoria) {
    // Convierte el nombre de la categoría a minúsculas para asegurar la comparación exacta
    String categoriaLowercase = categoria.toLowerCase();

    // Busca la categoría correspondiente en el enum
    ProductModel.Categoria categoriaEnum = null;
    for (ProductModel.Categoria c : ProductModel.Categoria.values()) {
        if (c.name().toLowerCase().equals(categoriaLowercase)) {
            categoriaEnum = c;
            break;
        }
    }

    // Si la categoría no se encuentra en el enum, devuelve una lista vacía
    if (categoriaEnum == null) {
        return new ArrayList<>();
    }

    // Obtiene los productos filtrados por categoría desde el servicio
    return productService.getProductsByCategory(categoriaEnum);
}

}
