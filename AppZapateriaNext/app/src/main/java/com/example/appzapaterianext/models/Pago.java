package com.example.appzapaterianext.models;

public class Pago {
    private int id;
    private int cantidadCuotas;
    private String Estado;
    private int idVenta;
    private Venta venta;

    public Pago() {
    }

    public Pago(int id, int cantidadCuotas, String estado, int idVenta, Venta venta) {
        this.id = id;
        this.cantidadCuotas = cantidadCuotas;
        Estado = estado;
        this.idVenta = idVenta;
        this.venta = venta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", cantidadCuotas=" + cantidadCuotas +
                ", Estado='" + Estado + '\'' +
                ", idVenta=" + idVenta +
                ", venta=" + venta +
                '}';
    }
}
