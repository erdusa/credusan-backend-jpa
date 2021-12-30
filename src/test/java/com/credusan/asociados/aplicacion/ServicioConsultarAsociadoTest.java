package com.credusan.asociados.aplicacion;

import com.credusan.TestConfig;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class ServicioConsultarAsociadoTest {

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioConsultarAsociado servicioConsultarAsociado;

    Asociado asociado;
    boolean soloActivos = true;


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
    public void noDeberiaRetornarRegistros() throws Exception {

        Pageable page = PageRequest.of(0, 1);

        assertEquals(0, servicioConsultarAsociado.getAll(page, soloActivos).getTotalElements());
    }

    @Test
    public void deberiaRetornarCincoRegistros() throws Exception {
        asociado.setNumeroDocumento("1");
        servicioCrearAsociado.create(asociado);
        asociado.setNumeroDocumento("2");
        servicioCrearAsociado.create(asociado);
        asociado.setNumeroDocumento("3");
        servicioCrearAsociado.create(asociado);
        asociado.setNumeroDocumento("4");
        servicioCrearAsociado.create(asociado);
        asociado.setNumeroDocumento("5");
        servicioCrearAsociado.create(asociado);

        Pageable page = PageRequest.of(0, 5);

        assertEquals(5, servicioConsultarAsociado.getAll(page, soloActivos).getTotalElements());
        assertTrue(servicioConsultarAsociado.getAll(page, soloActivos).stream().allMatch(Asociado::isActivo));
    }

    //////// Test para getAllByNames

    @Test
    public void noDeberiaRetornarRegistrosPorNombre() throws Exception {
        assertEquals(0, servicioConsultarAsociado.getAllByNameOrSurnames("").size());
    }

    @Test
    public void deberiaRetornarRegistrosPorNombres() throws Exception {
        asociado.setNumeroDocumento("1");
        asociado.setNombres("pedro maria");
        asociado.setPrimerApellido("pascasio");
        asociado.setSegundoApellido("perez");
        servicioCrearAsociado.create(asociado);

        asociado.setNumeroDocumento("2");
        asociado.setNombres("martin maria");
        asociado.setPrimerApellido("ascanio");
        asociado.setSegundoApellido("perez");
        servicioCrearAsociado.create(asociado);

        asociado.setNumeroDocumento("3");
        asociado.setNombres("lucy maria");
        asociado.setPrimerApellido("ascanio");
        asociado.setSegundoApellido(null);
        servicioCrearAsociado.create(asociado);

        asociado.setNumeroDocumento("4");
        asociado.setNombres("maria");
        asociado.setPrimerApellido("rodriguez");
        asociado.setSegundoApellido("ascanio");
        servicioCrearAsociado.create(asociado);

        assertEquals(4, servicioConsultarAsociado.getAll(PageRequest.of(0, 1), soloActivos).getTotalElements());
        assertEquals(1, servicioConsultarAsociado.getAllByNameOrSurnames("pedro").size());
        assertEquals(2, servicioConsultarAsociado.getAllByNameOrSurnames("perez").size());
        assertEquals(3, servicioConsultarAsociado.getAllByNameOrSurnames("ascanio").size());
        assertEquals(4, servicioConsultarAsociado.getAllByNameOrSurnames("maria").size());
        assertEquals(1, servicioConsultarAsociado.getAllByNameOrSurnames("pedro pascasio").size());
        assertEquals(1, servicioConsultarAsociado.getAllByNameOrSurnames("maria rodriguez ascanio").size());
        assertEquals(1, servicioConsultarAsociado.getAllByNameOrSurnames("lucy maria ascanio").size());
        assertTrue(servicioConsultarAsociado.getAllByNameOrSurnames("maria").stream().allMatch(Asociado::isActivo));
    }

}