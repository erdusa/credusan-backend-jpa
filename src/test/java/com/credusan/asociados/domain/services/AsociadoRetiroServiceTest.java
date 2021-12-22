package com.credusan.asociados.domain.services;

import com.credusan.TestConfig;
import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.TipoDocumento;
import com.credusan.captaciones.domain.enums.EnumTipoEstadoCaptacion;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.captaciones.domain.ports.output.CaptacionPersistence;
import com.credusan.captaciones.domain.services.CaptacionUpdaterService;
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
class AsociadoRetiroServiceTest {

    @Autowired
    AsociadoCreatorService asociadoCreatorService;
    @Autowired
    AsociadoRetiroService asociadoRetiroService;

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
        Asociado asociadoActual = asociadoCreatorService.create(asociado);
        assertTrue(asociadoRetiroService.retirarAsociado(asociadoActual.getIdAsociado()));
    }
}