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

    private static final String LA_FECHA_NO_PUEDE_SER_SUPERIOR_A_LA_ACTUAL = "La fecha no puede ser superior a la actual";
    private static final String LA_HORA_NO_PUEDE_SER_SUPERIOR_A_LA_ACTUAL = "La hora no puede ser superior a la actual";
    private static final String EL_VALOR_DEBITO_NO_PUEDE_SER_MENOR_QUE_CERO = "El valor débito no puede ser menor que cero";
    private static final String EL_VALOR_CREDITO_NO_PUEDE_SER_MENOR_QUE_CERO = "El valor crédito no puede ser menor que cero";
    private static final String EL_VALOR_DEBITO_O_CREDITO_DEBE_SER_MAYOR_A_CERO = "El valor débito o crédito debe ser mayor que cero";
    private static final String NO_PUEDE_ASIGNAR_VALOR_DEBITO_Y_CREDITO_AL_MISMO_TIEMPO = "No puede asignar valor débito y crédito al mismo tiempo";

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