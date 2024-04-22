package com.davidruiz.barvendor.Products;

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
        Principal,
        Postre
    }
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private String alergenos;

    private double precio;

    private boolean existencias;

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
}