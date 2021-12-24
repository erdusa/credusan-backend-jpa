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

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioRetirarAsociado servicioRetirarAsociado;

    Asociado asociado;

    @BeforeEach
    void setUp() throws Exception {
        DataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:testdb", "sa", "password");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/test-data-asociado.sql"));

        asociado = new Asociado(
                new TipoDocumento(3),
                "202020",
                "pedro",
                "pascasio",
                "perez",
                LocalDate.of(2000, 10, 5));
    }

    @Test
    public void deberiaRetirarAsociado() throws Exception {
        Asociado asociadoActual = servicioCrearAsociado.create(asociado);
        assertTrue(servicioRetirarAsociado.retirarAsociado(asociadoActual.getIdAsociado()));
    }
}