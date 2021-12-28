package com.credusan.asociados.infraestructura.jpa.entidades;

import com.credusan.asociados.dominio.modelos.TipoDocumento;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tipodocumento")
public class EntidadTipoDocumento {
    @Id
    @Column(name = "tipdocid")
    private Integer idTipoDocumento;
    @Column(name = "tipdocabreviatura", nullable = false, length = 3)
    private String abreviatura;
    @Column(name = "tipdocdescripcion", nullable = false, length = 50)
    private String descripcion;

    public EntidadTipoDocumento() {
        //Necesario para el framework
    }

    public EntidadTipoDocumento(TipoDocumento tipoDocumento) {
        BeanUtils.copyProperties(tipoDocumento, this);
    }

    public TipoDocumento toTipoDocumento() {
        TipoDocumento tipoDocumento = new TipoDocumento();
        BeanUtils.copyProperties(this, tipoDocumento);
        return tipoDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadTipoDocumento that = (EntidadTipoDocumento) o;
        return Objects.equals(idTipoDocumento, that.idTipoDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoDocumento);
    }
}