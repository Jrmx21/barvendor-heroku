package com.davidruiz.barvendor.Tables;

import java.util.List;

import com.davidruiz.barvendor.Accounts.AccountModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesas")
public class TableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private List<AccountModel> accounts;

    @Column(name = "ubicacion")
    private String ubicacion;


    @Column(name = "espacio_comensales")
    private int espacio_comensales;
    @Column(name = "ocupada")
    private boolean ocupada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

 
    public int getEspacio_comensales() {
        return espacio_comensales;
    }

    public void setEspacio_comensales(int espacio_comensales) {
        this.espacio_comensales = espacio_comensales;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public List<AccountModel> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountModel> accounts) {
        this.accounts = accounts;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}
