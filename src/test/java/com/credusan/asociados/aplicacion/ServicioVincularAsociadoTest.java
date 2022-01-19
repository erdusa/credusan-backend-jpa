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
class ServicioVincularAsociadoTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioRetirarAsociado servicioRetirarAsociado;
    @Autowired
    ServicioVincularAsociado servicioVincularAsociado;

    Asociado asociadoCreado;

    @BeforeEach
    void setUp() throws Exception {
        DataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:testdb", "sa", "password");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/test-data-captacion.sql"));

        Asociado asociado = new Asociado(
                new TipoDocumento(3),
                "202020",
                "pedro",
                "pascasio",
                "perez",
                LocalDate.of(2000, 10, 5));

        asociadoCreado = servicioCrearAsociado.create(asociado);

        servicioRetirarAsociado.retirarAsociado(asociadoCreado.getIdAsociado());
    }

    @Test
    public void deberiaVincularAsociado() throws Exception {
        assertTrue(servicioVincularAsociado.vincular(asociadoCreado.getIdAsociado()));
    }

    @Test
    public void noDeberiaVincularAsociadoSiEstaActivo() throws Exception {
        servicioVincularAsociado.vincular(asociadoCreado.getIdAsociado());

        Exception thrown = assertThrows(Exception.class, () -> servicioVincularAsociado.vincular(asociadoCreado.getIdAsociado()));

        assertEquals(ServicioVincularAsociado.EL_ASOCIADO_ESTA_ACTIVO, thrown.getMessage());
    }
}