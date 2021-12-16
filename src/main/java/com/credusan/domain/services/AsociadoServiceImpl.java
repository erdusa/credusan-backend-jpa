package com.credusan.domain.services;

import com.credusan.domain.models.aportes.*;
import com.credusan.domain.ports.input.aportes.AsociadoService;
import com.credusan.domain.ports.output.aportes.AsociadoPersistence;
import com.credusan.domain.ports.output.aportes.CaptacionPersistence;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class AsociadoServiceImpl implements AsociadoService {

    private final AsociadoPersistence persistence;
    private final CaptacionPersistence captacionPersistence;

    public AsociadoServiceImpl(AsociadoPersistence asociadoPersistence, CaptacionPersistence captacionPersistence) {
        this.persistence = asociadoPersistence;
        this.captacionPersistence = captacionPersistence;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Asociado create(Asociado asociado) throws Exception {

        if (tieneIdAsociado(asociado)) {
            throw new Exception("El identificador del asociado no debe tener valor");
        }

        if (!esPorcentajeBeneficiariosCorrecto(asociado)) {
            throw new Exception("Los porcentajes asignados a los beneficiarios deben sumar 100");
        }
        if (asociado.getBeneficiarios() != null) {
            asociado.getBeneficiarios().forEach(beneficiario -> beneficiario.setAsociado(asociado));
        }

        Asociado asociadoCreado = persistence.create(asociado);

        crearCaptacion(asociadoCreado);

        return asociadoCreado;
    }

    private void crearCaptacion(Asociado asociado) throws Exception{
        Integer numeroCuenta = captacionPersistence.getMaxNumeroCuentaByTipoCaptacion(EnumTipoCaptacion.APORTES.id);
        numeroCuenta = (numeroCuenta == null ? 0 : numeroCuenta) + 1;

        Captacion captacion = new Captacion();
        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id));
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setFechaApertura(LocalDate.now());
        captacion.setNumeroCuenta(numeroCuenta);
        captacion.setSaldo((double) 0);
        captacion.setAsociado(asociado);
        captacionPersistence.create(captacion);

    }

    @Override
    public Asociado update(Integer idAsociado, Asociado asociado) throws Exception {

        if (persistence.getById(idAsociado).getNumeroDocumento() == null) {
            throw new Exception("No existe el asociado con id = " + idAsociado);
        }

        if (!esPorcentajeBeneficiariosCorrecto(asociado)) {
            throw new Exception("Los porcentajes asignados a los beneficiarios deben sumar 100");
        }

        if (asociado.getBeneficiarios() != null) {
            asociado.getBeneficiarios().forEach(beneficiario -> beneficiario.setAsociado(asociado));
        }

        return persistence.update(asociado);
    }

    @Override
    public List<Asociado> getAll() throws Exception {
        return persistence.getAll();
    }

    @Override
    public Asociado getById(Integer idAsociado) throws Exception {
        return persistence.getById(idAsociado);
    }

    @Override
    public void retirarAsociado(Integer idAsociado) throws Exception {
        Asociado asociado = persistence.getById(idAsociado);

        if (asociado.getNumeroDocumento() == null) {
            throw new Exception("No existe el asociado con id = " + idAsociado);
        }
        //1. cambiar estado de captacion a anulada
        //2. verificar si tiene creditos vigentes
        //2.1 si aportes + ahorros cubre saldo, hacer cruce
        //2.2 si aportes + ahorros < saldo, no permitir retiro
        //mirar otras validaciones

        persistence.update(asociado);
    }

    private boolean tieneIdAsociado(@NonNull Asociado asociado) {
        return asociado.getIdAsociado() != null;
    }

    private boolean esPorcentajeBeneficiariosCorrecto(@NonNull Asociado asociado) {
        if (asociado.getBeneficiarios() == null || asociado.getBeneficiarios().size() == 0) {
            return true;
        }
        int totalPorcentaje = asociado.getBeneficiarios().stream()
                .mapToInt(Beneficiario::getPorcentaje)
                .sum();

        return (totalPorcentaje == 100);
    }
}
