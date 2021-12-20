package com.credusan.asociados.domain.services;

import com.credusan.TestConfig;
import com.credusan.asociados.domain.models.Asociado;
import com.credusan.asociados.domain.models.Beneficiario;
import com.credusan.asociados.domain.models.TipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class AsociadoCreatorServiceTest {

    @Autowired
    AsociadoCreatorService asociadoCreatorService;

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
    public void deberiaCrearAsociadoSinBeneficiarios() throws Exception {
        Asociado asociadoActual = asociadoCreatorService.create(asociado);
        assertNotNull(asociadoActual.getIdAsociado());
    }

    @Test
    public void deberiaCrearAsociadoSiPorcentajeBeneficiariosIgualA100() throws Exception {
        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 100));

        Asociado asociadoActual = asociadoCreatorService.create(asociado);
        assertNotNull(asociadoActual.getIdAsociado());
    }

    @Test
    public void noDeberiaCrearAsociadoSiIdAsociadoTieneValorAsignado() {

        asociado.setIdAsociado(1);

        Exception thrown = assertThrows(Exception.class, () -> asociadoCreatorService.create(asociado));

        assertEquals("El identificador del asociado no debe tener valor", thrown.getMessage());

    }

    @Test
    public void noDeberiaCrearAsociadoSiPorcentajeBeneficiariosDiferenteDe100() {
        asociado.setBeneficiarios(new ArrayList<>());
        asociado.getBeneficiarios().add(new Beneficiario("carlos", "perez", "diaz", 50));

        Exception thrown = assertThrows(Exception.class, () -> asociadoCreatorService.create(asociado));

        assertEquals("Los porcentajes asignados a los beneficiarios deben sumar 100", thrown.getMessage());

    }

}