package com.credusan.captaciones.domain.services;

import com.credusan.TestConfig;
import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.TipoDocumento;
import com.credusan.asociados.domain.services.AsociadoCreatorService;
import com.credusan.captaciones.domain.enums.EnumTipoCaptacion;
import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.models.TipoCaptacion;
import com.credusan.captaciones.domain.models.TipoEstadoCaptacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class CaptacionUpdaterServiceTest {

    @Autowired
    AsociadoCreatorService asociadoCreatorService;
    @Autowired
    CaptacionUpdaterService captacionUpdaterService;
    @Autowired
    CaptacionCreatorService captacionCreatorService;

    Asociado asociado;
    Captacion captacion;

    @BeforeEach
    void setUp() throws Exception {
        DataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:testdb", "sa", "password");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/test-data-captacion.sql"));

        asociado = new Asociado(
                new TipoDocumento(3),
                "202020",
                "pedro",
                "pascasio",
                "perez",
                LocalDate.of(2000, 10, 5));

        captacion = new Captacion(
                new TipoCaptacion(EnumTipoCaptacion.AHORROS.id),
                0,
                asociado,
                new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.ACTIVA.id),
                LocalDate.now(),
                (double) 0
        );
    }

    @Test
    void deberiaActualizarCaptacion() throws Exception {
        Asociado asociadoCreado = asociadoCreatorService.create(asociado);
        captacion.setAsociado(asociadoCreado);

        Captacion captacionCreada = captacionCreatorService.create(captacion);

        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.SALDADA.id));
        captacion.setSaldo((double) 5000);

        Captacion captacionActualizada = captacionUpdaterService.update(captacionCreada.getIdCaptacion(), captacion);

        assertEquals(captacion.toString(), captacionActualizada.toString());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaTipoCaptacion() throws Exception {
        Asociado asociadoCreado = asociadoCreatorService.create(asociado);

        captacion.setAsociado(asociadoCreado);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        Captacion captacionCreada = captacionCreatorService.create(captacion);

        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        Exception thrown = assertThrows(Exception.class, () -> captacionUpdaterService.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el tipo de captación", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaAsociado() throws Exception {

        asociado.setNumeroDocumento("1");
        Asociado asociadoCreado = asociadoCreatorService.create(asociado);

        captacion.setAsociado(asociadoCreado);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        Captacion captacionCreada = captacionCreatorService.create(captacion);

        asociado.setNumeroDocumento("2");
        asociadoCreado = asociadoCreatorService.create(asociado);

        captacion.setAsociado(asociadoCreado);
        Exception thrown = assertThrows(Exception.class, () -> captacionUpdaterService.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el propietario de la captación", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaNumeroCuenta() throws Exception {
        Asociado asociadoCreado = asociadoCreatorService.create(asociado);
        captacion.setAsociado(asociadoCreado);
        Captacion captacionCreada = captacionCreatorService.create(captacion);

        captacion.setNumeroCuenta(100);
        Exception thrown = assertThrows(Exception.class, () -> captacionUpdaterService.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el número de cuenta", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaFechaApertura() throws Exception {
        Asociado asociadoCreado = asociadoCreatorService.create(asociado);
        captacion.setAsociado(asociadoCreado);
        Captacion captacionCreada = captacionCreatorService.create(captacion);

        captacion.setFechaApertura(LocalDate.of(2000, 1, 1));
        Exception thrown = assertThrows(Exception.class, () -> captacionUpdaterService.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar la fecha de apertura", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiSaldoEsNegativo() throws Exception {
        Asociado asociadoCreado = asociadoCreatorService.create(asociado);
        captacion.setAsociado(asociadoCreado);
        Captacion captacionCreada = captacionCreatorService.create(captacion);

        captacion.setSaldo((double) -100);
        Exception thrown = assertThrows(Exception.class, () -> captacionUpdaterService.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("El saldo de la cuenta no puede ser negativo", thrown.getMessage());
    }


}