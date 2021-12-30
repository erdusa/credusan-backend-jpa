package com.credusan.asociados.aplicacion;

import com.credusan.TestConfig;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
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
class ServicioRetirarAsociadoTest {
    private static final String NO_TIENE_CUENTA_DE_APORTES_ACTIVA = "No tiene cuenta de aportes activa";
    private static final String EL_ASOCIADO_NO_ESTA_ACTIVO = "El asociado no está activo";

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioRetirarAsociado servicioRetirarAsociado;

    Asociado asociadoCreado;

    @BeforeEach
    void setUp() throws Exception {
        DataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:testdb", "sa", "password");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/test-data-asociado.sql"));

        Asociado asociado = new Asociado(
                new TipoDocumento(3),
                "202020",
                "pedro",
                "pascasio",
                "perez",
                LocalDate.of(2000, 10, 5));

        asociadoCreado = servicioCrearAsociado.create(asociado);
    }

    @Test
    public void deberiaRetirarAsociado() throws Exception {

        assertTrue(servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado()));
    }

    @Test
    public void noDeberiaRetirarAsociadoSiEstaInactivo() throws Exception {
        servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado());

        Exception thrown = assertThrows(Exception.class, () -> servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado()));

        assertEquals(EL_ASOCIADO_NO_ESTA_ACTIVO, thrown.getMessage());
    }
}