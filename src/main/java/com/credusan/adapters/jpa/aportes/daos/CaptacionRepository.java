package com.credusan.adapters.jpa.aportes.daos;

import com.credusan.adapters.jpa.aportes.entities.CaptacionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaptacionRepository extends GenericRepository<CaptacionEntity, Integer> {
    @Query(value = "SELECT MAX(c.captNumeroCuenta) FROM captacion c WHERE c.tipcapid = :idTipoCaptacion", nativeQuery = true)
    Integer findMaxNumeroCuentaByTipoCaptacion(@Param("idTipoCaptacion") Integer idTipoCaptacion);

    @Query(value = "FROM #{#entityName} c WHERE c.asociadoEntity.idAsociado = :idAsociado")
    List<CaptacionEntity> findAllByIdAsociado(@Param("idAsociado") Integer idAsociado);
}
