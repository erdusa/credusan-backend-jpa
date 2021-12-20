package com.credusan.captaciones.domain.models;

import lombok.Data;

@Data
public class TipoCaptacion {
    private Integer idTipoCaptacion;
    private String nombre;

    public TipoCaptacion() {
    }

    public TipoCaptacion(Integer idTipoCaptacion) {
        this.idTipoCaptacion = idTipoCaptacion;
    }
}
