package com.credusan.captaciones.domain.models;

import com.credusan.asociados.domain.models.Asociado;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Captacion {
    private Integer idCaptacion;
    private TipoCaptacion tipoCaptacion;
    private Integer numeroCuenta;
    private Asociado asociado;
    private TipoEstadoCaptacion tipoEstadoCaptacion;
    private LocalDate fechaApertura;
    private Double saldo;

    public Captacion(TipoCaptacion tipoCaptacion, Integer numeroCuenta, Asociado asociado, TipoEstadoCaptacion tipoEstadoCaptacion, LocalDate fechaApertura, Double saldo) {
        this.tipoCaptacion = tipoCaptacion;
        this.numeroCuenta = numeroCuenta;
        this.asociado = asociado;
        this.tipoEstadoCaptacion = tipoEstadoCaptacion;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Captacion{" +
                "tipoCaptacion=" + (tipoCaptacion != null ? tipoCaptacion.getIdTipoCaptacion() : 0) +
                ", numeroCuenta=" + numeroCuenta +
                ", asociado=" + (asociado != null ? asociado.getIdAsociado() : 0) +
                ", tipoEstadoCaptacion=" + (tipoEstadoCaptacion != null ? tipoEstadoCaptacion.getIdTipoEstadoCaptacion() : 0) +
                ", fechaApertura=" + fechaApertura +
                ", saldo=" + saldo +
                '}';
    }
}
