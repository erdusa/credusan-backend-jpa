package com.credusan.asociados.domain.services;

import com.credusan.TestConfig;
import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.Beneficiario;
import com.credusan.asociados.domain.models.TipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
class AsociadoUpdaterServiceTest {

    @Autowired
    AsociadoCreatorService asociadoCreatorService;
    @Autowired
    AsociadoUpdaterService asociadoUpdaterService;

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
    public void deberiaActualizarAsociadoSiPorcentajeBeneficiariosIgualA100() throws Exception {
        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        Asociado asociadoCreado = asociadoCreatorService.create(asociado);

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

        Asociado asociadoActual = asociadoUpdaterService.update(asociadoU.getIdAsociado(), asociadoU);
        assertEquals(asociadoU.toString(), asociadoActual.toString());
        assertEquals(asociadoU.getBeneficiarios().toString(), asociadoActual.getBeneficiarios().toString());
    }

    @Test
    public void noDeberiaActualizarAsociadoSiPorcentajeBeneficiariosDiferenteDe100() throws Exception {
        Asociado asociadoCreado = asociadoCreatorService.create(asociado);

        Asociado asociadoU = new Asociado();
        BeanUtils.copyProperties(asociadoCreado, asociadoU);

        asociadoU.setBeneficiarios(new ArrayList<>());
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));

        Exception thrown = assertThrows(Exception.class, () -> asociadoUpdaterService.update(asociadoU.getIdAsociado(), asociadoU));

        assertEquals("Los porcentajes asignados a los beneficiarios deben sumar 100", thrown.getMessage());

    }

    @Test
    public void noDeberiaActualizarAsociadoSiNoExiste() {
        asociado.setIdAsociado(1);
        Exception thrown = assertThrows(Exception.class, () -> asociadoUpdaterService.update(asociado.getIdAsociado(), asociado));

        assertEquals("No existe el asociado con id = " + asociado.getIdAsociado(), thrown.getMessage());
    }

    @Test
    public void deberiaActualizarDeTenerANoTenerBeneficiarios() throws Exception {
        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        Asociado asociadoCreado = asociadoCreatorService.create(asociado);

        Asociado asociadoU = new Asociado();
        asociadoU.setIdAsociado(asociadoCreado.getIdAsociado());
        asociadoU.setTipoDocumento(new TipoDocumento(2));
        asociadoU.setNumeroDocumento("202021");
        asociadoU.setNombres("pedro luis");
        asociadoU.setPrimerApellido("juliano");
        asociadoU.setSegundoApellido("marciano");
        asociadoU.setFechaNacimiento(LocalDate.of(1900, 10, 5));

        Asociado asociadoActual = asociadoUpdaterService.update(asociadoU.getIdAsociado(), asociadoU);
        assertEquals(asociadoU.toString(), asociadoActual.toString());
        assertEquals(0, asociadoActual.getBeneficiarios().size());
    }

    @Test
    public void deberiaActualizarDeNoTenerATenerBeneficiarios() throws Exception {

        Asociado asociadoCreado = asociadoCreatorService.create(asociado);

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

        Asociado asociadoActual = asociadoUpdaterService.update(asociadoU.getIdAsociado(), asociadoU);
        assertEquals(asociadoU.toString(), asociadoActual.toString());
        assertEquals(asociadoU.getBeneficiarios().toString(), asociadoActual.getBeneficiarios().toString());
    }

}