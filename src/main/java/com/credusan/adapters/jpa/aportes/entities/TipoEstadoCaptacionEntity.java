package com.credusan.adapters.jpa.aportes.entities;

import com.credusan.domain.models.aportes.TipoEstadoCaptacion;
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
public class TipoEstadoCaptacionEntity {
    @Id
    @Column(name = "tiescaid")
    private Integer idTipoEstadoCaptacion;
    @Column(name = "tiescanombre", nullable = false, length = 30)
    private String nombre;

    public TipoEstadoCaptacionEntity() {
        //PARA EL FRAMEWORK
    }

    public TipoEstadoCaptacionEntity(TipoEstadoCaptacion tipoEstadoCaptacion) {
        BeanUtils.copyProperties(tipoEstadoCaptacion, this);
    }

    public TipoEstadoCaptacion toTipoEstadoCaptacion() {
        TipoEstadoCaptacion tipoEstadoCaptacion = new TipoEstadoCaptacion();
        BeanUtils.copyProperties(this, tipoEstadoCaptacion);
        return tipoEstadoCaptacion;
    }
}
