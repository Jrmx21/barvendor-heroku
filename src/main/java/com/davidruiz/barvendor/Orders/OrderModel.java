package com.davidruiz.barvendor.Orders;

import java.sql.Date;
import java.util.List;

import com.davidruiz.barvendor.Accounts.AccountModel;
import com.davidruiz.barvendor.Products.ProductModel;
import com.davidruiz.barvendor.Tables.TableModel;
import com.davidruiz.barvendor.Users.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pedidos")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_pedido", nullable = false)
    private Date fecha;

    @Column(name = "Notas", length = 255)
    private String notas;
    @Column(name = "Precio", length = 255)
    private String precio;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_usuario")
    private UserModel user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cuenta")
    private AccountModel cuenta;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_mesa")
    private TableModel mesa;
    @ManyToMany
    @JoinTable(name = "pedido_producto", joinColumns = @JoinColumn(name = "FK1_id_pedidos"), inverseJoinColumns = @JoinColumn(name = "FK1_id_productos"))
    private List<ProductModel> products;

    // Getters y setters

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public AccountModel getCuenta() {
        return cuenta;
    }

    public void setCuenta(AccountModel cuenta) {
        this.cuenta = cuenta;
    }

    public TableModel getMesa() {
        return mesa;
    }

    public void setMesa(TableModel mesa) {
        this.mesa = mesa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    // Método para calcular el precio total del pedido
    public double calcularPrecioTotal() {
        double precioTotal = 0.0;
        for (ProductModel product : products) {
            precioTotal += product.getPrecio();
        }
        return precioTotal;
    }
}