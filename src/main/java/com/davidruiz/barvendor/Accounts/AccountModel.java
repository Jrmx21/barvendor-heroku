package com.davidruiz.barvendor.Accounts;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import com.davidruiz.barvendor.Products.ProductModel;
import com.davidruiz.barvendor.Tables.TableModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cuentas")
public class AccountModel {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<OrderModel> orders;
    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private TableModel mesa;

    @Column(nullable = true)
    private double precioTotal;
    
    private boolean pagado;
    @Column(name = "fecha_pago" , nullable = true)
    private Date fechaPago;
    // @ManyToOne
    // @JsonIgnore
    // @JoinColumn(name = "id_pedido")
    // private OrderModel pedido;
    // Ubicacion de la cuenta (asumo que se relaciona con la mesa)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderModel> getOrders() {
        calcularPrecioTotal ();
        return orders;
        
    }

    public void setOrders(List<OrderModel> orders) {
        calcularPrecioTotal ();
        this.orders = orders;
   
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


    public TableModel getMesa() {
        return mesa;
    }

    public void setMesa(TableModel mesa) {
        this.mesa = mesa;
    }

  // Método para calcular el precio total de todos los pedidos asociados a esta cuenta
  public void calcularPrecioTotal() {
    double total = 0.0;
    if (orders != null) {
        for (OrderModel order : orders) {
            total += order.calcularPrecioTotal();
        }
    }
    this.precioTotal = Math.round(total * 100.0) / 100.0;
}


}
