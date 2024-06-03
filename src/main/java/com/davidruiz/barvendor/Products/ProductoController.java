package com.davidruiz.barvendor.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductService productoService;

    @PostMapping("/deshabilitar/{id}")
    public String deshabilitarProducto(@PathVariable("id") Long id) {
        productoService.disableProductById(id);
        return "redirect:/productos/listar";
    }

    @PostMapping("/habilitar/{id}")
    public String habilitarProducto(@PathVariable("id") Long id) {
        productoService.enableProductById(id);
        return "redirect:/productos/restaurar";
    }

    @GetMapping("/listar")
    public String mostrarProductos(Model model) {
        List<ProductModel> productos = productoService.getProducts();

        if (!productos.isEmpty()) {
            ProductModel primerProducto = productos.get(0);
            LocalDate tiempoRestanteDescuento = primerProducto.getDiscountEndDate();
            Double porcentajeDescuentoAplicado = primerProducto.getDiscountPercentage();
            model.addAttribute("tiempoRestanteDescuento", tiempoRestanteDescuento);
            model.addAttribute("porcentajeDescuentoAplicado", porcentajeDescuentoAplicado);
        }

        // Filtrar los productos activos
        List<ProductModel> productosActivos = productos.stream()
            .filter(ProductModel::isActive)
            .collect(Collectors.toList());

        model.addAttribute("productos", productosActivos);
        return "listarProductos";
    }
    
    @GetMapping("/restaurar")
    public String mostrarProductosDeshabilitados(Model model) {
        List<ProductModel> productosDeshabilitados = productoService.getDisabledProducts();
        model.addAttribute("productosDeshabilitados", productosDeshabilitados);
        return "restaurarProductos";
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
    @PostMapping("/descuento-temporal")
    public String applyTemporaryDiscount(@RequestParam("descuento") double discountPercentage,
                                         @RequestParam("duracion") int durationDays) {
        productoService.applyTemporaryDiscount(discountPercentage, durationDays);
        return "redirect:/productos/listar"; // Redirigir a la página de listar productos
    }
    
    @PostMapping("/recalcular-precio-original")
    public String recalcularPrecioOriginal() {
        productoService.removeTemporaryDiscount();
        return "redirect:/productos/listar";
    }

    @PostMapping("/eliminar-descuento")
    public String eliminarDescuento() {
        productoService.removeDiscountManually();
        return "redirect:/productos/listar";
    }

    @GetMapping("/descuento-temporal")
    public String showTemporaryDiscountForm() {
        return "discountProducts"; // Nombre de la plantilla para el formulario de descuento temporal
    }
    public String getMethodName(@RequestParam String param) {
        return new String();
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
