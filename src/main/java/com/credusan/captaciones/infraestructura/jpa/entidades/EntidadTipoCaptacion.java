package com.credusan.captaciones.infraestructura.jpa.entidades;

import com.credusan.captaciones.dominio.modelos.TipoCaptacion;
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
@Table(name = "tipocaptacion")
public class EntidadTipoCaptacion {
    @Id
    @Column(name = "tipcapid")
    private Integer idTipoCaptacion;
    @Column(name = "tipcapnombre", nullable = false, length = 30)
    private String nombre;

    public EntidadTipoCaptacion() {
        //PARA EL FRAMEWORK
    }

    public EntidadTipoCaptacion(TipoCaptacion tipoCaptacion) {
        BeanUtils.copyProperties(tipoCaptacion, this);
    }

    public TipoCaptacion toTipoCaptacion() {
        TipoCaptacion tipoCaptacion = new TipoCaptacion();
        BeanUtils.copyProperties(this, tipoCaptacion);
        return tipoCaptacion;
    }
}
