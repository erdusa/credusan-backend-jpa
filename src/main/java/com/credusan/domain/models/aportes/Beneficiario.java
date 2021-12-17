package com.credusan.domain.models.aportes;

import com.credusan.utils.StringUtils;
import lombok.Data;

import java.util.Objects;

@Data
public class Beneficiario {
    private Integer idBeneficiario;
    private Asociado asociado;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private Integer porcentaje;

    public Beneficiario() {
    }

    public Beneficiario(String nombres, String primerApellido, String segundoApellido, Integer porcentaje) {
        setNombres(nombres);
        setPrimerApellido(primerApellido);
        setSegundoApellido(segundoApellido);
        this.porcentaje = porcentaje;
    }

    public void setNombres(String nombres) {
        this.nombres = StringUtils.toUpperCase(nombres);
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = StringUtils.toUpperCase(primerApellido);
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = StringUtils.toUpperCase(segundoApellido);
    }

    public String getNombreCompleto() {
        return (this.nombres + ' ' +this.primerApellido + ' '+ Objects.toString(this.segundoApellido, "")).trim();
    }

    @Override
    public String toString() {
        return "Beneficiario{" +
                ", nombres='" + nombres + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", porcentaje=" + porcentaje +
                '}';
    }
}
