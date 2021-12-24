package com.credusan.asociados.infraestructura.jpa.daos;

import com.credusan.asociados.infraestructura.jpa.entities.EntidadAsociado;
import com.credusan.shared.repository.RepositorioGenerico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioAsociado extends RepositorioGenerico<EntidadAsociado, Integer> {

    @Query(
            value = "SELECT * FROM asociado WHERE asocnombres||asocprimerApellido||coalesce(asocsegundoApellido,'') ilike REPLACE('%'||:nombres||'%', ' ','%') order by asocnombres, asocprimerApellido, asocsegundoApellido",
            nativeQuery = true
    )
    List<EntidadAsociado> findAllByNames(@Param("nombres") String nombres);
}
