package com.credusan.captaciones.infraestructura.jpa.entidades;

import com.credusan.captaciones.dominio.modelos.TipoEstadoCaptacion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tipoestadocaptacion")
public class EntidadTipoEstadoCaptacion {
    @Id
    @Column(name = "tiescaid")
    private Integer idTipoEstadoCaptacion;
    @Column(name = "tiescanombre", nullable = false, length = 30)
    private String nombre;

    public EntidadTipoEstadoCaptacion() {
        //PARA EL FRAMEWORK
    }

    public EntidadTipoEstadoCaptacion(TipoEstadoCaptacion tipoEstadoCaptacion) {
        BeanUtils.copyProperties(tipoEstadoCaptacion, this);
    }

    public TipoEstadoCaptacion toTipoEstadoCaptacion() {
        TipoEstadoCaptacion tipoEstadoCaptacion = new TipoEstadoCaptacion();
        BeanUtils.copyProperties(this, tipoEstadoCaptacion);
        return tipoEstadoCaptacion;
    }
}
