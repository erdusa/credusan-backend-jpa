package com.credusan.captaciones.aplicacion;

import com.credusan.TestConfig;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.asociados.aplicacion.ServicioCrearAsociado;
import com.credusan.captaciones.dominio.enums.EnumTipoCaptacion;
import com.credusan.captaciones.dominio.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
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
class ServicioActualizarCaptacionTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioActualizarCaptacion servicioActualizarCaptacion;
    @Autowired
    ServicioCrearCaptacion servicioCrearCaptacion;

    Asociado asociado;
    Captacion captacion;
    Asociado asociadoCreado;
    Captacion captacionCreada;

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

        asociadoCreado = servicioCrearAsociado.create(asociado);
        captacion.setAsociado(asociadoCreado);
        captacionCreada = servicioCrearCaptacion.create(captacion);
    }

    @Test
    void deberiaActualizarCaptacion() throws Exception {

        captacion.setTipoEstadoCaptacion(new TipoEstadoCaptacion(EnumTipoEstadoCaptacion.SALDADA.id));
        captacion.setSaldo((double) 5000);

        Captacion captacionActualizada = servicioActualizarCaptacion.update(captacionCreada.getIdCaptacion(), captacion);

        assertEquals(captacion.toString(), captacionActualizada.toString());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaTipoCaptacion() {

        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        Exception thrown = assertThrows(Exception.class, () -> servicioActualizarCaptacion.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el tipo de captación", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaAsociado() throws Exception {

        asociado.setNumeroDocumento("2");
        asociadoCreado = servicioCrearAsociado.create(asociado);

        captacion.setAsociado(asociadoCreado);
        Exception thrown = assertThrows(Exception.class, () -> servicioActualizarCaptacion.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el propietario de la captación", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaNumeroCuenta() {

        captacion.setNumeroCuenta(100);
        Exception thrown = assertThrows(Exception.class, () -> servicioActualizarCaptacion.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar el número de cuenta", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiCambiaFechaApertura() {

        captacion.setFechaApertura(LocalDate.of(2000, 1, 1));
        Exception thrown = assertThrows(Exception.class, () -> servicioActualizarCaptacion.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("No se puede cambiar la fecha de apertura", thrown.getMessage());
    }

    @Test
    void noDeberiaActualizarCaptacionSiSaldoEsNegativo() {

        captacion.setSaldo((double) -100);
        Exception thrown = assertThrows(Exception.class, () -> servicioActualizarCaptacion.update(captacionCreada.getIdCaptacion(), captacion));

        assertEquals("El saldo de la cuenta no puede ser negativo", thrown.getMessage());
    }


}