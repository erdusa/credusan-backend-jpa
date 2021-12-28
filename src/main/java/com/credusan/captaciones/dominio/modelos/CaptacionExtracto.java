package com.credusan.captaciones.dominio.modelos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CaptacionExtracto {

    private static final String LA_FECHA_NO_PUEDE_SER_SUPERIOR_A_LA_ACTUAL = "La fecha no puede ser superior a la actual";
    private static final String LA_HORA_NO_PUEDE_SER_SUPERIOR_A_LA_ACTUAL = "La hora no puede ser superior a la actual";
    private static final String EL_VALOR_DEBITO_NO_PUEDE_SER_MENOR_QUE_CERO = "El valor débito no puede ser menor que cero";
    private static final String EL_VALOR_CREDITO_NO_PUEDE_SER_MENOR_QUE_CERO = "El valor crédito no puede ser menor que cero";
    private static final String EL_VALOR_DEBITO_O_CREDITO_DEBE_SER_MAYOR_A_CERO = "El valor débito o crédito debe ser mayor que cero";
    private static final String NO_PUEDE_ASIGNAR_VALOR_DEBITO_Y_CREDITO_AL_MISMO_TIEMPO = "No puede asignar valor débito y crédito al mismo tiempo";

    private Long idCaptacionExtracto;
    private Captacion captacion;
    private LocalDate fecha;
    private LocalTime hora;
    private Double valorDebito;
    private Double valorCredito;

    public CaptacionExtracto() {
    }

    public CaptacionExtracto(LocalDate fecha, LocalTime hora, Double valorDebito, Double valorCredito) throws Exception {
        this.setFecha(fecha);
        this.setHora(hora);
        this.setValorDebito(valorDebito);
        this.setValorCredito(valorCredito);

        if (valorDebito <= 0 && valorCredito <= 0) {
            throw new Exception(EL_VALOR_DEBITO_O_CREDITO_DEBE_SER_MAYOR_A_CERO);
        }

        if (valorDebito > 0 && valorCredito > 0) {
            throw new Exception(NO_PUEDE_ASIGNAR_VALOR_DEBITO_Y_CREDITO_AL_MISMO_TIEMPO);
        }
    }

    public void setFecha(LocalDate fecha) throws Exception {
        if (fecha.isAfter(LocalDate.now())) {
            throw new Exception(LA_FECHA_NO_PUEDE_SER_SUPERIOR_A_LA_ACTUAL);
        }
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) throws Exception {
        if (hora.isAfter(LocalTime.now())) {
            throw new Exception(LA_HORA_NO_PUEDE_SER_SUPERIOR_A_LA_ACTUAL);
        }
        this.hora = hora;
    }

    public void setValorDebito(Double valorDebito) throws Exception {
        if (valorDebito < 0) {
            throw new Exception(EL_VALOR_DEBITO_NO_PUEDE_SER_MENOR_QUE_CERO);
        }
        this.valorDebito = valorDebito;
    }

    public void setValorCredito(Double valorCredito) throws Exception {
        if (valorCredito < 0) {
            throw new Exception(EL_VALOR_CREDITO_NO_PUEDE_SER_MENOR_QUE_CERO);
        }
        this.valorCredito = valorCredito;
    }

    @Override
    public String toString() {
        return "CaptacionExtracto{" +
                "captacion=" + (captacion != null ? captacion.getIdCaptacion() : null) +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", valorDebito=" + valorDebito +
                ", valorCredito=" + valorCredito +
                '}';
    }
}
