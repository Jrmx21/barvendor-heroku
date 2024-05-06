package com.davidruiz.barvendor.Accounts;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

import com.davidruiz.barvendor.Orders.OrderModel;
import com.davidruiz.barvendor.Tables.TableModel;

@Entity
@Table(name = "cuentas")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<OrderModel> orders;

    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private TableModel mesa;

    private String nombreProducto; // Todos los productos que se hayan pedido a esa cuenta

    private double precioTotal;

    private boolean pagado;

    private Date fechaPago;

    // Ubicacion de la cuenta (asumo que se relaciona con la mesa)
    private String ubicacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

 
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public TableModel getMesa() {
        return mesa;
    }

    public void setMesa(TableModel mesa) {
        this.mesa = mesa;
    }

    // Getters y Setters

}
