package com.credusan.domain.ports.input.aportes;

import com.credusan.TestConfig;
import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.Beneficiario;
import com.credusan.asociados.domain.ports.input.AsociadoService;
import com.credusan.captaciones.domain.ports.input.CaptacionService;
import com.credusan.captaciones.domain.models.Captacion;
import com.credusan.asociados.domain.models.TipoDocumento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class AsociadoServiceTest {

    @Autowired
    AsociadoService service;
    @Autowired
    CaptacionService captacionService;

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

    @AfterEach
    void tearDown() throws Exception {

    }

    /////Test para create
    @Test
    public void deberiaCrearAsociadoSinBeneficiarios() throws Exception {
        Asociado asociadoActual = service.create(asociado);
        assertNotNull(asociadoActual.getIdAsociado());

        List<Captacion> captaciones = captacionService.getAllByIdAsociado(asociadoActual.getIdAsociado());
        assertEquals(1, captaciones.size());
    }

    @Test
    public void deberiaCrearAsociadoSiPorcentajeBeneficiariosIgualA100() throws Exception {
        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        Asociado asociadoActual = service.create(asociado);
        assertNotNull(asociadoActual.getIdAsociado());

        List<Captacion> captaciones = captacionService.getAllByIdAsociado(asociadoActual.getIdAsociado());
        assertEquals(1, captaciones.size());
    }

    @Test
    public void noDeberiaCrearAsociadoSiIdAsociadoTieneValorAsignado() throws Exception {

        asociado.setIdAsociado(1);

        Exception thrown = assertThrows(Exception.class, () -> service.create(asociado));

        assertEquals("El identificador del asociado no debe tener valor", thrown.getMessage());

    }

    @Test
    public void noDeberiaCrearAsociadoSiPorcentajeBeneficiariosDiferenteDe100() throws Exception {
        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));

        Exception thrown = assertThrows(Exception.class, () -> service.create(asociado));

        assertEquals("Los porcentajes asignados a los beneficiarios deben sumar 100", thrown.getMessage());

    }
    /////Test para update
    @Test
    public void deberiaActualizarAsociadoSiPorcentajeBeneficiariosIgualA100() throws Exception {
        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        Asociado asociadoCreado = service.create(asociado);

        Asociado asociadoU = new Asociado();
        asociadoU.setIdAsociado(asociadoCreado.getIdAsociado());
        asociadoU.setTipoDocumento(new TipoDocumento(2));
        asociadoU.setNumeroDocumento("202021");
        asociadoU.setNombres("pedro luis");
        asociadoU.setPrimerApellido("juliano");
        asociadoU.setSegundoApellido("marciano");
        asociadoU.setFechaNacimiento(LocalDate.of(1900, 10, 5));
        asociadoU.setBeneficiarios(new ArrayList<>());
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        Asociado asociadoActual = service.update(asociadoU.getIdAsociado(), asociadoU);
        assertEquals(asociadoU.toString(), asociadoActual.toString());
        assertEquals(asociadoU.getBeneficiarios().toString(), asociadoActual.getBeneficiarios().toString());
    }

    @Test
    public void noDeberiaActualizarAsociadoSiPorcentajeBeneficiariosDiferenteDe100() throws Exception {
        Asociado asociadoCreado = service.create(asociado);

        Asociado asociadoU = new Asociado();
        BeanUtils.copyProperties(asociadoCreado, asociadoU);

        asociadoU.setBeneficiarios(new ArrayList<>());
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));

        Exception thrown = assertThrows(Exception.class, () -> service.update(asociadoU.getIdAsociado(), asociadoU));

        assertEquals("Los porcentajes asignados a los beneficiarios deben sumar 100", thrown.getMessage());

    }

    @Test
    public void noDeberiaActualizarAsociadoSiNoExiste() throws Exception {
        asociado.setIdAsociado(1);
        Exception thrown = assertThrows(Exception.class, () -> service.update(asociado.getIdAsociado(), asociado));

        assertEquals("No existe el asociado con id = " + asociado.getIdAsociado(), thrown.getMessage());
     }

    @Test
    public void deberiaActualizarDeTenerANoTenerBeneficiarios() throws Exception {
        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        Asociado asociadoCreado = service.create(asociado);

        Asociado asociadoU = new Asociado();
        asociadoU.setIdAsociado(asociadoCreado.getIdAsociado());
        asociadoU.setTipoDocumento(new TipoDocumento(2));
        asociadoU.setNumeroDocumento("202021");
        asociadoU.setNombres("pedro luis");
        asociadoU.setPrimerApellido("juliano");
        asociadoU.setSegundoApellido("marciano");
        asociadoU.setFechaNacimiento(LocalDate.of(1900, 10, 5));

        Asociado asociadoActual = service.update(asociadoU.getIdAsociado(), asociadoU);
        assertEquals(asociadoU.toString(), asociadoActual.toString());
        assertEquals(0, asociadoActual.getBeneficiarios().size());
        System.out.println(service.getById(asociadoU.getIdAsociado()));
    }

    @Test
    public void deberiaActualizarDeNoTenerATenerBeneficiarios() throws Exception {

        Asociado asociadoCreado = service.create(asociado);

        Asociado asociadoU = new Asociado();
        asociadoU.setIdAsociado(asociadoCreado.getIdAsociado());
        asociadoU.setTipoDocumento(new TipoDocumento(2));
        asociadoU.setNumeroDocumento("202021");
        asociadoU.setNombres("pedro luis");
        asociadoU.setPrimerApellido("juliano");
        asociadoU.setSegundoApellido("marciano");
        asociadoU.setFechaNacimiento(LocalDate.of(1900, 10, 5));
        asociadoU.setBeneficiarios(new ArrayList<>());
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));

        Asociado asociadoActual = service.update(asociadoU.getIdAsociado(), asociadoU);
        assertEquals(asociadoU.toString(), asociadoActual.toString());
        assertEquals(asociadoU.getBeneficiarios().toString(), asociadoActual.getBeneficiarios().toString());
    }

    //////// Test para getAll

    @Test
    public void noDeberiaRetornarRegistros() throws Exception{

        Pageable page = PageRequest.of(0, 1);
        Page<Asociado> asociados = service.getAll(page);

        assertEquals(0, asociados.getTotalElements());
    }

    @Test
    public void deberiaRetornarCincoRegistros() throws Exception{
        asociado.setNumeroDocumento("1");
        service.create(asociado);
        asociado.setNumeroDocumento("2");
        service.create(asociado);
        asociado.setNumeroDocumento("3");
        service.create(asociado);
        asociado.setNumeroDocumento("4");
        service.create(asociado);
        asociado.setNumeroDocumento("5");
        service.create(asociado);

        Pageable page = PageRequest.of(0, 5);

        Page<Asociado> asociados = service.getAll(page);

        assertEquals(5, asociados.getTotalElements());
    }

    //////// Test para getAllByNames

    @Test
    public void noDeberiaRetornarRegistrosPorNombre() throws Exception{

        List<Asociado> asociados = service.getAllByNames("");

        assertEquals(0, asociados.size());
    }

    @Test
    public void deberiaRetornarRegistrosPorNombres() throws Exception{
        asociado.setNumeroDocumento("1");
        asociado.setNombres("pedro maria");
        asociado.setPrimerApellido("pascasio");
        asociado.setSegundoApellido("perez");
        service.create(asociado);
        asociado.setNumeroDocumento("2");
        asociado.setNombres("martin maria");
        asociado.setPrimerApellido("ascanio");
        asociado.setSegundoApellido("perez");
        service.create(asociado);

        asociado.setNumeroDocumento("3");
        asociado.setNombres("lucy maria");
        asociado.setPrimerApellido("ascanio");
        asociado.setSegundoApellido(null);
        service.create(asociado);

        asociado.setNumeroDocumento("4");
        asociado.setNombres("maria");
        asociado.setPrimerApellido("rodriguez");
        asociado.setSegundoApellido("ascanio");
        service.create(asociado);

        assertEquals(4, service.getAll(PageRequest.of(0, 1)).getTotalElements());
        assertEquals(1, service.getAllByNames("pedro").size());
        assertEquals(2, service.getAllByNames("perez").size());
        assertEquals(3, service.getAllByNames("ascanio").size());
        assertEquals(4, service.getAllByNames("maria").size());
        assertEquals(1, service.getAllByNames("pedro pascasio").size());
        assertEquals(1, service.getAllByNames("maria rodriguez ascanio").size());
        assertEquals(1, service.getAllByNames("lucy maria ascanio").size());
    }


}