package com.credusan.captaciones.dominio.modelos;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CaptacionExtractoTest {

    CaptacionExtracto captacionExtracto;

    @Test
    void deberiaCrearCaptacionExtracto() throws Exception {

        captacionExtracto = new CaptacionExtracto(
                LocalDate.now(),
                LocalTime.now(),
                (double) 200000,
                (double) 0
        );

        assertNotNull(captacionExtracto);
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiFechaEsFutura() {

        Exception thrown = assertThrows(Exception.class, () -> new CaptacionExtracto(
                LocalDate.now().plusDays(1),
                LocalTime.now(),
                (double) 200000,
                (double) 0
        ));

        assertEquals(CaptacionExtracto.LA_FECHA_NO_PUEDE_SER_SUPERIOR_A_LA_ACTUAL, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiHoraEsFutura() {

        Exception thrown = assertThrows(Exception.class, () -> new CaptacionExtracto(
                LocalDate.now(),
                LocalTime.now().plusSeconds(1),
                (double) 200000,
                (double) 0
        ));

        assertEquals(CaptacionExtracto.LA_HORA_NO_PUEDE_SER_SUPERIOR_A_LA_ACTUAL, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiValorDebitoEsNegativo() {

        Exception thrown = assertThrows(Exception.class, () -> new CaptacionExtracto(
                LocalDate.now(),
                LocalTime.now(),
                (double) -200000,
                (double) 0
        ));

        assertEquals(CaptacionExtracto.EL_VALOR_DEBITO_NO_PUEDE_SER_MENOR_QUE_CERO, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiValorCreditoEsNegativo() {

        Exception thrown = assertThrows(Exception.class, () -> new CaptacionExtracto(
                LocalDate.now(),
                LocalTime.now(),
                (double) 0,
                (double) -200000
        ));

        assertEquals(CaptacionExtracto.EL_VALOR_CREDITO_NO_PUEDE_SER_MENOR_QUE_CERO, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiDebitoYCreditoSonCero() {

        Exception thrown = assertThrows(Exception.class, () -> new CaptacionExtracto(
                LocalDate.now(),
                LocalTime.now(),
                (double) 0,
                (double) 0
        ));

        assertEquals(CaptacionExtracto.EL_VALOR_DEBITO_O_CREDITO_DEBE_SER_MAYOR_A_CERO, thrown.getMessage());
    }

    @Test
    void noDeberiaCrearCaptacionExtractoSiDebitoYCreditoTienenValor() {

        Exception thrown = assertThrows(Exception.class, () -> new CaptacionExtracto(
                LocalDate.now(),
                LocalTime.now(),
                (double) 100,
                (double) 100
        ));

        assertEquals(CaptacionExtracto.NO_PUEDE_ASIGNAR_VALOR_DEBITO_Y_CREDITO_AL_MISMO_TIEMPO, thrown.getMessage());
    }
}