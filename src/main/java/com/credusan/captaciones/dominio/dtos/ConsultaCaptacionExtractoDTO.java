package com.credusan.captaciones.dominio.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConsultaCaptacionExtractoDTO {
    private Integer idCaptacion;
    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
}
