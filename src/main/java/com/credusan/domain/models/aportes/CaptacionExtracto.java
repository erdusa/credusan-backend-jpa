package com.credusan.domain.models.aportes;

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
}
