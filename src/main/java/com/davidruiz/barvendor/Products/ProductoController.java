package com.davidruiz.barvendor.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductService productoService;

    @GetMapping("/listar")
    public String mostrarProductos(Model model) {
        List<ProductModel> productos = productoService.getProducts();
        model.addAttribute("productos", productos);
        return "listarProductos";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("product", new ProductModel());
        return "crearProducto";
    }

    @PostMapping("/crear")
    public String crearProducto(@ModelAttribute("product") ProductModel producto) {
        productoService.saveProduct(producto);
        return "redirect:/productos/listar";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificar(@PathVariable("id") Long id, Model model) {
        Optional<ProductModel> producto = productoService.getProductById(id);
        if (producto.isPresent()) {
            model.addAttribute("product", producto.get());
            return "modificarProducto";
        } else {
            // Manejar el caso en que el producto no exista
            return "redirect:/productos/listar";
        }
    }

    @PostMapping("/modificar/{id}")
    public String modificarProducto(@PathVariable("id") Long id, @ModelAttribute("product") ProductModel producto) {
        productoService.updateProduct(id, producto);
        return "redirect:/productos/listar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        productoService.deleteProductById(id);
        return "redirect:/productos/listar";
    }
   

}
