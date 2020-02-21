package com.example.appzapaterianext.models;

import java.util.Date;

public class Cuota {
    private int id;
    private int nroCuota;
    private Date fecha;
    private Double monto;
    private String estado;
    private int idPago;
    private Pago pago;

    public Cuota() {
    }

    public Cuota(int id, int nroCuota, Date fecha, Double monto, String estado, int idPago, Pago pago) {
        this.id = id;
        this.nroCuota = nroCuota;
        this.fecha = fecha;
        this.monto = monto;
        this.estado = estado;
        this.idPago = idPago;
        this.pago = pago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(int nroCuota) {
        this.nroCuota = nroCuota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "Cuota{" +
                "id=" + id +
                ", nroCuota=" + nroCuota +
                ", fecha=" + fecha +
                ", monto=" + monto +
                ", estado='" + estado + '\'' +
                ", idPago=" + idPago +
                ", pago=" + pago +
                '}';
    }
}
