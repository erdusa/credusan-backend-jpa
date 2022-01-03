package com.credusan.asociados.dominio.modelos;

import com.credusan.shared.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Asociado {
    private Integer idAsociado;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private List<Beneficiario> beneficiarios;
    private Boolean activo;

    public Asociado(TipoDocumento tipoDocumento, String numeroDocumento, String nombres, String primerApellido, String segundoApellido, LocalDate fechaNacimiento) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        setNombres(nombres);
        setPrimerApellido(primerApellido);
        setSegundoApellido(segundoApellido);
        this.fechaNacimiento = fechaNacimiento;
        this.activo = true;
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

    public Integer getEdad() {
        if (fechaNacimiento == null) {
            return null;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getNombreCompleto() {
        return (this.nombres + ' ' + this.primerApellido + ' ' + Objects.toString(this.segundoApellido, "")).trim();
    }


    @Override
    public String toString() {
        return "Asociado{" +
                "tipoDocumento=" + (tipoDocumento == null ? "" : tipoDocumento.getIdTipoDocumento()) +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", nombres='" + nombres + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", activo=" + activo +
                '}';
    }
}
