package com.credusan.asociados.domain.services;

import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.Beneficiario;
import com.credusan.captaciones.domain.enums.EnumTipoCaptacion;
import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.models.TipoCaptacion;
import com.credusan.captaciones.domain.models.TipoEstadoCaptacion;
import com.credusan.asociados.domain.ports.input.AsociadoService;
import com.credusan.asociados.domain.ports.output.AsociadoPersistence;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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

        if (esPorcentajeBeneficiariosErrado(asociado)) {
            throw new Exception("Los porcentajes asignados a los beneficiarios deben sumar 100");
        }
        if (asociado.getBeneficiarios() != null) {
            asociado.getBeneficiarios().forEach(beneficiario -> beneficiario.setAsociado(asociado));
        }

        Asociado asociadoCreado = persistence.save(asociado);

        crearCaptacion(asociadoCreado);

        return asociadoCreado;
    }

    private void crearCaptacion(Asociado asociado) throws Exception {
        Captacion captacion = new Captacion();
        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id));
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setFechaApertura(LocalDate.now());
        captacion.setNumeroCuenta(captacionPersistence.getMaxNumeroCuentaByTipoCaptacion(EnumTipoCaptacion.APORTES.id));
        captacion.setSaldo((double) 0);
        captacion.setAsociado(asociado);
        captacionPersistence.save(captacion);
    }

    @Override
    public Asociado update(Integer idAsociado, Asociado asociado) throws Exception {

        if (persistence.getById(idAsociado).getNumeroDocumento() == null) {
            throw new Exception("No existe el asociado con id = " + idAsociado);
        }

        if (esPorcentajeBeneficiariosErrado(asociado)) {
            throw new Exception("Los porcentajes asignados a los beneficiarios deben sumar 100");
        }

        if (asociado.getBeneficiarios() != null) {
            asociado.getBeneficiarios().forEach(beneficiario -> beneficiario.setAsociado(asociado));
        }

        return persistence.save(asociado);
    }

    @Override
    public Page<Asociado> getAll(Pageable page) throws Exception {
        return persistence.getAll(page);
    }

    @Override
    public Asociado getById(Integer idAsociado) throws Exception {
        return persistence.getById(idAsociado);
    }

    @Override
    public List<Asociado> getAllByNames(String nombres) throws Exception {
        return persistence.getAllByNames(nombres);
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

        persistence.save(asociado);
    }

    private boolean tieneIdAsociado(@NonNull Asociado asociado) {
        return asociado.getIdAsociado() != null;
    }

    private boolean esPorcentajeBeneficiariosErrado(@NonNull Asociado asociado) {
        if (asociado.getBeneficiarios() == null || asociado.getBeneficiarios().size() == 0) {
            return false;
        }
        int totalPorcentaje = asociado.getBeneficiarios().stream()
                .mapToInt(Beneficiario::getPorcentaje)
                .sum();

        return (totalPorcentaje != 100);
    }
}