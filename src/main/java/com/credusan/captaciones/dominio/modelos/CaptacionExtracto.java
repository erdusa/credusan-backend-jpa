package com.credusan.captaciones.dominio.modelos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CaptacionExtracto {

    private Long idCaptacionExtracto;
    private Captacion captacion;
    private LocalDate fecha;
    private LocalTime hora;
    private Double valorDebito;
    private Double valorCredito;

    public CaptacionExtracto() {
    }

    public CaptacionExtracto(LocalDate fecha, LocalTime hora, Double valorDebito, Double valorCredito) {
        this.setFecha(fecha);
        this.setHora(hora);
        this.setValorDebito(valorDebito);
        this.setValorCredito(valorCredito);
    }

    @Override
    public String toString() {
        return "CaptacionExtracto{" +
                "captacion=" + (captacion != null ? captacion.getIdCaptacion() : null) +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", valorDebito=" + valorDebito +
                ", valorCredito=" + valorCredito +
                '}';
    }
}
