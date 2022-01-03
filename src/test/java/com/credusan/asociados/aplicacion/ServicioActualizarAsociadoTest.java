package com.credusan.asociados.aplicacion;

import com.credusan.TestConfig;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
import com.credusan.asociados.dominio.modelos.TipoDocumento;
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
class ServicioActualizarAsociadoTest {

    private static final String LOS_PORCENTAJES_ASIGNADOS_A_LOS_BENEFICIARIOS_DEBEN_SUMAR_100 = "Los porcentajes asignados a los beneficiarios deben sumar 100";
    private static final String NO_EXISTE_EL_ASOCIADO = "No existe el asociado";
    private static final String NO_PUEDE_INACTIVAR_EL_ASOCIADO_DEBERIA_RETIRARLO = "No puede inactivar el asociado, debería retirarlo";

    @Autowired
    ServicioCrearAsociado servicioCrearAsociado;
    @Autowired
    ServicioActualizarAsociado servicioActualizarAsociado;

    Asociado asociado, asociadoCreado;

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

        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        asociadoCreado = servicioCrearAsociado.create(asociado);
    }

    @Test
    public void deberiaActualizarAsociadoSiPorcentajeBeneficiariosIgualA100() throws Exception {

        Asociado asociadoU = asociadoCreado;
        asociadoU.setTipoDocumento(new TipoDocumento(2));
        asociadoU.setNumeroDocumento("202021");
        asociadoU.setNombres("pedro luis");
        asociadoU.setPrimerApellido("juliano");
        asociadoU.setSegundoApellido("marciano");
        asociadoU.setFechaNacimiento(LocalDate.of(1900, 10, 5));
        asociadoU.setBeneficiarios(new ArrayList<>());
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        Asociado asociadoActual = servicioActualizarAsociado.update(asociadoU.getIdAsociado(), asociadoU);
        assertEquals(asociadoU.toString(), asociadoActual.toString());
        assertEquals(asociadoU.getBeneficiarios().toString(), asociadoActual.getBeneficiarios().toString());
    }

    @Test
    public void noDeberiaActualizarAsociadoSiPorcentajeBeneficiariosDiferenteDe100() {

        Asociado asociadoU = new Asociado();
        BeanUtils.copyProperties(asociadoCreado, asociadoU);

        asociadoU.setBeneficiarios(new ArrayList<>());
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));

        Exception thrown = assertThrows(Exception.class, () -> servicioActualizarAsociado.update(asociadoU.getIdAsociado(), asociadoU));

        assertEquals(LOS_PORCENTAJES_ASIGNADOS_A_LOS_BENEFICIARIOS_DEBEN_SUMAR_100, thrown.getMessage());

    }

    @Test
    public void noDeberiaActualizarAsociadoSiNoExiste() {
        asociado.setIdAsociado(0);

        Exception thrown = assertThrows(Exception.class, () -> servicioActualizarAsociado.update(asociado.getIdAsociado(), asociado));

        assertEquals(NO_EXISTE_EL_ASOCIADO, thrown.getMessage());
    }

    @Test
    public void noDeberiaActualizarAsociadoSiIntentaInactivarlo() {
        asociadoCreado.setActivo(false);

        Exception thrown = assertThrows(Exception.class, () -> servicioActualizarAsociado.update(asociadoCreado.getIdAsociado(), asociadoCreado));

        assertEquals(NO_PUEDE_INACTIVAR_EL_ASOCIADO_DEBERIA_RETIRARLO, thrown.getMessage());
    }

    @Test
    public void deberiaActualizarDeTenerANoTenerBeneficiarios() throws Exception {

        asociadoCreado.setBeneficiarios(null);

        Asociado asociadoActual = servicioActualizarAsociado.update(asociadoCreado.getIdAsociado(), asociadoCreado);
        assertEquals(asociadoCreado.toString(), asociadoActual.toString());
        assertEquals(0, asociadoActual.getBeneficiarios().size());
    }

    @Test
    public void deberiaActualizarDeNoTenerATenerBeneficiarios() throws Exception {

        Asociado asociadoU = new Asociado();
        asociadoU.setTipoDocumento(new TipoDocumento(2));
        asociadoU.setNumeroDocumento("202021");
        asociadoU.setNombres("pedro luis");
        asociadoU.setPrimerApellido("juliano");
        asociadoU.setSegundoApellido("marciano");
        asociadoU.setFechaNacimiento(LocalDate.of(1900, 10, 5));

        asociadoCreado = servicioCrearAsociado.create(asociadoU);

        asociadoU.setIdAsociado(asociadoCreado.getIdAsociado());
        asociadoU.setBeneficiarios(new ArrayList<>());
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));
        asociadoU.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));

        Asociado asociadoActual = servicioActualizarAsociado.update(asociadoU.getIdAsociado(), asociadoU);
        assertEquals(asociadoU.toString(), asociadoActual.toString());
        assertEquals(asociadoU.getBeneficiarios().toString(), asociadoActual.getBeneficiarios().toString());
    }

}