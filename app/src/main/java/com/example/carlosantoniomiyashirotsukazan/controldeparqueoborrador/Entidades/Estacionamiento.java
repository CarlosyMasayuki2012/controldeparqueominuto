package com.example.carlosantoniomiyashirotsukazan.controldeparqueoborrador.Entidades;

import java.io.Serializable;

/**
 * Created by carlosantoniomiyashirotsukazan on 28/03/18.
 */

public class Estacionamiento implements Serializable {

    private int Id_estacionamiento;
    private String fecha_ingreso;
    private String fecha_salida;
    private String hora_ingreso;
    private String minuto_ingreso;
    private String hora_salida;
    private String minuto_salida;
    private String tarifavehiculo;
    private String nombretipovehiculo;
    private String monto;
    private String placa;
    private String condicion;
    private String pendientepago;

    public Estacionamiento() {
    }

    public Estacionamiento(int id_estacionamiento, String fecha_ingreso, String fecha_salida,
                           String hora_ingreso, String minuto_ingreso, String hora_salida,
                           String minuto_salida, String tarifavehiculo, String nombretipovehiculo,
                           String monto, String placa, String condicion, String pendientepago) {
        Id_estacionamiento = id_estacionamiento;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_salida = fecha_salida;
        this.hora_ingreso = hora_ingreso;
        this.minuto_ingreso = minuto_ingreso;
        this.hora_salida = hora_salida;
        this.minuto_salida = minuto_salida;
        this.tarifavehiculo = tarifavehiculo;
        this.nombretipovehiculo = nombretipovehiculo;
        this.monto = monto;
        this.placa = placa;
        this.condicion = condicion;
        this.pendientepago = pendientepago;
    }

    public int getId_estacionamiento() {
        return Id_estacionamiento;
    }

    public void setId_estacionamiento(int id_estacionamiento) {
        Id_estacionamiento = id_estacionamiento;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getHora_ingreso() {
        return hora_ingreso;
    }

    public void setHora_ingreso(String hora_ingreso) {
        this.hora_ingreso = hora_ingreso;
    }

    public String getMinuto_ingreso() {
        return minuto_ingreso;
    }

    public void setMinuto_ingreso(String minuto_ingreso) {
        this.minuto_ingreso = minuto_ingreso;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public String getMinuto_salida() {
        return minuto_salida;
    }

    public void setMinuto_salida(String minuto_salida) {
        this.minuto_salida = minuto_salida;
    }

    public String getTarifavehiculo() {
        return tarifavehiculo;
    }

    public void setTarifavehiculo(String tarifavehiculo) {
        this.tarifavehiculo = tarifavehiculo;
    }

    public String getNombretipovehiculo() {
        return nombretipovehiculo;
    }

    public void setNombretipovehiculo(String nombretipovehiculo) {
        this.nombretipovehiculo = nombretipovehiculo;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getPendientepago() {
        return pendientepago;
    }

    public void setPendientepago(String pendientepago) {
        this.pendientepago = pendientepago;
    }
}
