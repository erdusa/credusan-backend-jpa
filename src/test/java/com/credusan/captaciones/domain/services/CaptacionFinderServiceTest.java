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

@TestConfig
class CaptacionFinderServiceTest {

    @Autowired
    AsociadoCreatorService asociadoCreatorService;
    @Autowired
    CaptacionFinderService captacionFinderService;
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
    void deberiaRetornarRegistrosPorIdAsociado() throws Exception {
        Asociado asociadoCreado = asociadoCreatorService.create(asociado);

        captacion.setAsociado(asociadoCreado);

        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        captacionCreatorService.create(captacion);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        captacionCreatorService.create(captacion);
        captacion.setTipoCaptacion(new TipoCaptacion(EnumTipoCaptacion.AHORROS.id));
        captacionCreatorService.create(captacion);

        assertEquals(3, (int) captacionFinderService.getAllByIdAsociado(asociadoCreado.getIdAsociado())
                .stream()
                .filter(c -> c.getTipoCaptacion().getIdTipoCaptacion().equals(EnumTipoCaptacion.AHORROS.id)).count());
    }


}