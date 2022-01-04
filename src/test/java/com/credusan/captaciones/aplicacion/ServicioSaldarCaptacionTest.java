package com.credusan.captaciones.aplicacion;

import com.credusan.TestConfig;
import com.credusan.asociados.aplicacion.ServicioCrearAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
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

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ServicioSaldarCaptacionTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioSaldarCaptacion servicioSaldarCaptacion;
    @Autowired
    ServicioCrearCaptacion servicioCrearCaptacion;
    @Autowired
    ServicioConsultarCaptacion servicioConsultarCaptacion;


    Asociado asociado;
    Captacion captacion;
    Asociado asociadoCreado;
    Captacion captacionAhorros;

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
        captacionAhorros = servicioCrearCaptacion.create(captacion);
    }

    @Test
    void deberiaSaldarCaptacion() throws Exception {

        Boolean respuesta = servicioSaldarCaptacion.saldar(captacionAhorros.getIdCaptacion());

        assertTrue(respuesta);
    }

    @Test
    void noDeberiaSaldarSiEsCuentaAportes() throws Exception {
        Captacion captacionAportes = servicioConsultarCaptacion.getCuentaAportes(asociadoCreado.getIdAsociado());

        Exception thrown = assertThrows(Exception.class, () -> servicioSaldarCaptacion.saldar(captacionAportes.getIdCaptacion()));

        assertEquals(ServicioSaldarCaptacion.NO_SE_PUEDE_SALDAR_LA_CUENTA_DE_APORTES, thrown.getMessage());
    }

    @Test
    void noDeberiaSaldarSiYaEstaSaldada() throws Exception {
        servicioSaldarCaptacion.saldar(captacionAhorros.getIdCaptacion());

        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.APORTES.id));
        Exception thrown = assertThrows(Exception.class, () -> servicioSaldarCaptacion.saldar(captacionAhorros.getIdCaptacion()));

        assertEquals(ServicioSaldarCaptacion.LA_CAPTACION_YA_ESTA_SALDADA, thrown.getMessage());
    }

}