package com.credusan.domain.ports.input.aportes;

import com.credusan.TestConfig;
import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.TipoDocumento;
import com.credusan.asociados.domain.ports.input.AsociadoService;
import com.credusan.captaciones.domain.ports.input.CaptacionService;
import com.credusan.captaciones.domain.enums.EnumTipoCaptacion;
import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.models.TipoCaptacion;
import com.credusan.captaciones.domain.models.TipoEstadoCaptacion;
import org.junit.jupiter.api.AfterEach;
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
class CaptacionServiceTest {

    @Autowired
    AsociadoService asociadoService;
    @Autowired
    CaptacionService service;

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

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    void deberiaRetornarRegistrosPorIdAsociado() throws Exception {
        Asociado asociadoCreado = asociadoService.create(asociado);

        captacion.setAsociado(asociadoCreado);

        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        service.create(captacion);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        service.create(captacion);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        service.create(captacion);

        assertEquals(3, (int) service.getAllByIdAsociado(asociadoCreado.getIdAsociado())
                .stream()
                .filter(c -> c.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.AHORROS.id)).count());
    }

    @Test
    void noDeberiaCrearAportesSiTieneUnaActiva() throws Exception {
        Asociado asociadoCreado = asociadoService.create(asociado);

        captacion.setAsociado(asociadoCreado);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));

        Exception thrown = assertThrows(Exception.class, () -> service.create(captacion));

        assertEquals("El asociado ya tiene una cuenta de aportes activa", thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionSiIdCaptacionTieneValorAsignado() throws Exception {
        Asociado asociadoCreado = asociadoService.create(asociado);

        captacion.setIdCaptacion(1);

        Exception thrown = assertThrows(Exception.class, () -> service.create(captacion));

        assertEquals("El identificador de la captación no debe tener valor", thrown.getMessage());
    }

    @Test
    void deberiaCrearCaptacion() throws Exception {

        Asociado asociadoCreado = asociadoService.create(asociado);
        captacion.setAsociado(asociadoCreado);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        Captacion captacionCreada = service.create(captacion);

        assertEquals(captacion.toString(), captacionCreada.toString());
    }

    @Test
    void deberiaActualizarCaptacion() throws Exception {
        Asociado asociadoCreado = asociadoService.create(asociado);
        captacion.setAsociado(asociadoCreado);

        Captacion captacionCreada = service.create(captacion);

        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.SALDADA.id));
        captacion.setSaldo((double) 5000);

        Captacion captacionActualizada = service.update(captacionCreada.getIdCaptacion(), captacion);

        assertEquals(captacion.toString(), captacionActualizada.toString());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaTipoCaptacion() throws Exception {
        Asociado asociadoCreado = asociadoService.create(asociado);

        captacion.setAsociado(asociadoCreado);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        Captacion captacionCreada = service.create(captacion);

        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        Exception thrown = assertThrows(Exception.class, () -> service.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el tipo de captación", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaAsociado() throws Exception {

        asociado.setNumeroDocumento("1");
        Asociado asociadoCreado = asociadoService.create(asociado);

        captacion.setAsociado(asociadoCreado);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        Captacion captacionCreada = service.create(captacion);

        asociado.setNumeroDocumento("2");
        asociadoCreado = asociadoService.create(asociado);

        captacion.setAsociado(asociadoCreado);
        Exception thrown = assertThrows(Exception.class, () -> service.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el propietario de la captación", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaNumeroCuenta() throws Exception {
        Asociado asociadoCreado = asociadoService.create(asociado);
        captacion.setAsociado(asociadoCreado);
        Captacion captacionCreada = service.create(captacion);

        captacion.setNumeroCuenta(100);
        Exception thrown = assertThrows(Exception.class, () -> service.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el número de cuenta", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaFechaApertura() throws Exception {
        Asociado asociadoCreado = asociadoService.create(asociado);
        captacion.setAsociado(asociadoCreado);
        Captacion captacionCreada = service.create(captacion);

        captacion.setFechaApertura(LocalDate.of(2000, 1, 1));
        Exception thrown = assertThrows(Exception.class, () -> service.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar la fecha de apertura", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiSaldoEsNegativo() throws Exception {
        Asociado asociadoCreado = asociadoService.create(asociado);
        captacion.setAsociado(asociadoCreado);
        Captacion captacionCreada = service.create(captacion);

        captacion.setSaldo((double) -100);
        Exception thrown = assertThrows(Exception.class, () -> service.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("El saldo de la cuenta no puede ser negativo", thrown.getMessage());
    }
}