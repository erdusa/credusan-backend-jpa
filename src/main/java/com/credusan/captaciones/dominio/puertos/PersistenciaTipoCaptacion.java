package com.credusan.captaciones.dominio.puertos;

import com.credusan.captaciones.dominio.modelos.TipoCaptacion;

import java.util.List;

public interface PersistenciaTipoCaptacion {
    List<TipoCaptacion> getAll();
}
