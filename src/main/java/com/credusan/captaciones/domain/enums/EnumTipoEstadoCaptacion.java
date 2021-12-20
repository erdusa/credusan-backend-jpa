package com.credusan.captaciones.domain.enums;

public enum EnumTipoEstadoCaptacion {
    ACTIVA(1),
    SALDADA(2);

    public final Integer id;

    EnumTipoEstadoCaptacion(int id) {
        this.id = id;

    }
}
