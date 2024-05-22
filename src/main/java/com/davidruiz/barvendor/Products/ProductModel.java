package com.davidruiz.barvendor.Products;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreProducto;

    public enum Categoria {
        Bebida,
        Entrante,
        Desayunos,
        Pescados,
        Carnes,
        Serranitos,
        Montaditos,
        Postre
    }

    private boolean discountActive;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private String alergenos;

    private double discountPercentage;
    private String descripcion;
// Constructor
public ProductModel() {
    this.precio = 0; // Establecer el precio predeterminado como 0
    this.originalPrice = 0; // Establecer el precio original predeterminado como 0
}
    private double precio;

    private boolean existencias;
    @Column(name = "discount_end_date")
    private LocalDate discountEndDate;
    private double originalPrice;
    // Getters y Setters

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(String alergenos) {
        this.alergenos = alergenos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isExistencias() {
        return existencias;
    }

    public void setExistencias(boolean existencias) {
        this.existencias = existencias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getDiscountEndDate() {
        return discountEndDate;
    }

    public void setDiscountEndDate(LocalDate discountEndDate) {
        this.discountEndDate = discountEndDate;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isDiscountActive() {
        return discountActive;
    }

    public void setDiscountActive(boolean discountActive) {
        this.discountActive = discountActive;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

   // Método para recalcular el precio original
   public void recalcularPrecioOriginal() {
    if (this.discountPercentage > 0) {
        double precioDescuento = this.precio / (1 - (this.discountPercentage / 100));
        this.originalPrice = Math.round(precioDescuento * 100.0) / 100.0;
    } else {
        this.originalPrice = this.precio;
    }
}
}
