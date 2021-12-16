package com.credusan.domain.models.aportes;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Captacion {
    private Integer idCaptacion;
    private TipoCaptacion tipoCaptacion;
    private Integer numeroCuenta;
    private Asociado asociado;
    private TipoEstadoCaptacion tipoEstadoCaptacion;
    private LocalDate fechaApertura;
    private Double saldo;
}
