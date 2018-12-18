package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Entidades;

import java.io.Serializable;

public class TipoVehiculo implements Serializable {

    private Integer id;
    private  String tipoVehiculo;
    private Double tarifa;

    public TipoVehiculo(Integer id, String tipoVehiculo, Double tarifa) {
        this.id = id;
        this.tipoVehiculo = tipoVehiculo;
        this.tarifa = tarifa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }
}
