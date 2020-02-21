package com.example.appzapaterianext.models;

public class DetalleVenta {
    private int id;
    private double precio;
    private int cantidad;
    private int idVenta;
    private int idZapatilla;
    private Venta venta;
    private Zapatilla zapatilla;

    public DetalleVenta() {
    }

    public DetalleVenta(int id, double precio, int cantidad, int idVenta, int idZapatilla, Venta venta, Zapatilla zapatilla) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idVenta = idVenta;
        this.idZapatilla = idZapatilla;
        this.venta = venta;
        this.zapatilla = zapatilla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdZapatilla() {
        return idZapatilla;
    }

    public void setIdZapatilla(int idZapatilla) {
        this.idZapatilla = idZapatilla;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Zapatilla getZapatilla() {
        return zapatilla;
    }

    public void setZapatilla(Zapatilla zapatilla) {
        this.zapatilla = zapatilla;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "id=" + id +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                ", idVenta=" + idVenta +
                ", idZapatilla=" + idZapatilla +
                ", venta=" + venta +
                ", zapatilla=" + zapatilla +
                '}';
    }


}
