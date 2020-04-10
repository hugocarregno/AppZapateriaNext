package com.example.appzapaterianext.models;

import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private Date fecha;
    private double montoTotal;
    private String cliente;
    private Byte estado;
    private int idEmpleado;
    private Empleado empleado;
    private List<DetalleVenta> detalles;

    public Venta() {
    }

    public Venta(int id, Date fecha, double montoTotal, String cliente, Byte estado, int idEmpleado, Empleado empleado, List<DetalleVenta> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.cliente = cliente;
        this.estado = estado;
        this.idEmpleado = idEmpleado;
        this.empleado = empleado;
        this.detalles = detalles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Byte getEstado() {
        return estado;
    }

    public void setEstado(Byte estado) {
        this.estado = estado;
    }

    public int getIdEmpleado(){
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado){
        this.idEmpleado = idEmpleado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado idEmpleado) {
        this.empleado = empleado;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", montoTotal=" + montoTotal +
                ", cliente='" + cliente + '\'' +
                ", estado=" + estado +
                ", idEmpleado=" + idEmpleado +
                ", empleado=" + empleado +
                ", detalles=" + detalles +
                '}';
    }
}
