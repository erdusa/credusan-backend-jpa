package com.credusan.captaciones.aplicacion;

import com.credusan.TestConfig;
import com.credusan.asociados.aplicacion.ServicioCrearAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import com.credusan.captaciones.dominio.modelos.Captacion;
import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class ServicioCrearCaptacionExtractoTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioConsultarCaptacion servicioConsultarCaptacion;
    @Autowired
    ServicioCrearCaptacionExtracto servicioCrearCaptacionExtracto;

    Asociado asociado;
    CaptacionExtracto captacionExtracto;
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
                LocalDate.of(2000, 10, 5)
        );
        asociadoCreado = servicioCrearAsociado.create(asociado);
        captacionCreada = servicioConsultarCaptacion.getAllByIdAsociado(asociadoCreado.getIdAsociado()).get(0);

        captacionExtracto = new CaptacionExtracto(
                LocalDate.now(),
                LocalTime.now(),
                (double) 200000,
                (double) 0
        );
    }

    @Test
    void deberiaCrearCaptacionExtracto() {

        captacionExtracto.setCaptacion(captacionCreada);
        CaptacionExtracto captacionExtractoCreado = servicioCrearCaptacionExtracto.create(captacionExtracto);

        assertEquals(captacionExtracto.toString(), captacionExtractoCreado.toString());
    }
}