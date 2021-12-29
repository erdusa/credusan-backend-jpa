package com.credusan.captaciones.infraestructura.jpa.daos;

import com.credusan.captaciones.infraestructura.jpa.entidades.EntidadCaptacion;
import com.credusan.shared.repository.RepositorioGenerico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioCaptacion extends RepositorioGenerico<EntidadCaptacion, Integer> {
    @Query(value = "SELECT MAX(c.captNumeroCuenta) FROM captacion c WHERE c.tipcapid = :idTipoCaptacion", nativeQuery = true)
    Integer findMaxNumeroCuentaByTipoCaptacion(@Param("idTipoCaptacion") Integer idTipoCaptacion);

    //@Query(value = "FROM #{#entityName} c WHERE c.asociadoEntity.idAsociado = :idAsociado")
    @Query(value = "SELECT * FROM captacion c WHERE c.asocid = :idAsociado ORDER BY tipcapid, captnumerocuenta", nativeQuery = true)
    List<EntidadCaptacion> findAllByIdAsociado(@Param("idAsociado") Integer idAsociado);
}
