package com.credusan.captaciones.domain.enums;

public enum EnumTipoCaptacion {
    APORTES(1),
    AHORROS(2);

    public final Integer id;

    EnumTipoCaptacion(int id) {
        this.id = id;
    }
}
