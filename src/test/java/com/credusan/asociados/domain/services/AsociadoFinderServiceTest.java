package com.credusan.asociados.domain.services;

import com.credusan.TestConfig;
import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.TipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class AsociadoFinderServiceTest {

    @Autowired
    AsociadoCreatorService asociadoCreatorService;
    @Autowired
    AsociadoFinderService asociadoFinderService;

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
    public void noDeberiaRetornarRegistros() throws Exception {

        Pageable page = PageRequest.of(0, 1);
        Page<Asociado> asociados = asociadoFinderService.getAll(page);

        assertEquals(0, asociados.getTotalElements());
    }

    @Test
    public void deberiaRetornarCincoRegistros() throws Exception {
        asociado.setNumeroDocumento("1");
        asociadoCreatorService.create(asociado);
        asociado.setNumeroDocumento("2");
        asociadoCreatorService.create(asociado);
        asociado.setNumeroDocumento("3");
        asociadoCreatorService.create(asociado);
        asociado.setNumeroDocumento("4");
        asociadoCreatorService.create(asociado);
        asociado.setNumeroDocumento("5");
        asociadoCreatorService.create(asociado);

        Pageable page = PageRequest.of(0, 5);

        Page<Asociado> asociados = asociadoFinderService.getAll(page);

        assertEquals(5, asociados.getTotalElements());
    }

    //////// Test para getAllByNames

    @Test
    public void noDeberiaRetornarRegistrosPorNombre() throws Exception {

        List<Asociado> asociados = asociadoFinderService.getAllByNames("");

        assertEquals(0, asociados.size());
    }

    @Test
    public void deberiaRetornarRegistrosPorNombres() throws Exception {
        asociado.setNumeroDocumento("1");
        asociado.setNombres("pedro maria");
        asociado.setPrimerApellido("pascasio");
        asociado.setSegundoApellido("perez");
        asociadoCreatorService.create(asociado);
        asociado.setNumeroDocumento("2");
        asociado.setNombres("martin maria");
        asociado.setPrimerApellido("ascanio");
        asociado.setSegundoApellido("perez");
        asociadoCreatorService.create(asociado);

        asociado.setNumeroDocumento("3");
        asociado.setNombres("lucy maria");
        asociado.setPrimerApellido("ascanio");
        asociado.setSegundoApellido(null);
        asociadoCreatorService.create(asociado);

        asociado.setNumeroDocumento("4");
        asociado.setNombres("maria");
        asociado.setPrimerApellido("rodriguez");
        asociado.setSegundoApellido("ascanio");
        asociadoCreatorService.create(asociado);

        assertEquals(4, asociadoFinderService.getAll(PageRequest.of(0, 1)).getTotalElements());
        assertEquals(1, asociadoFinderService.getAllByNames("pedro").size());
        assertEquals(2, asociadoFinderService.getAllByNames("perez").size());
        assertEquals(3, asociadoFinderService.getAllByNames("ascanio").size());
        assertEquals(4, asociadoFinderService.getAllByNames("maria").size());
        assertEquals(1, asociadoFinderService.getAllByNames("pedro pascasio").size());
        assertEquals(1, asociadoFinderService.getAllByNames("maria rodriguez ascanio").size());
        assertEquals(1, asociadoFinderService.getAllByNames("lucy maria ascanio").size());
    }

}