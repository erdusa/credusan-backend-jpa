package com.credusan.adapters.jpa.aportes.entities;

import com.credusan.domain.models.aportes.TipoCaptacion;
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
public class TipoCaptacionEntity {
    @Id
    @Column(name = "tipcapid")
    private Integer idTipoCaptacion;
    @Column(name = "tipcapnombre", nullable = false, length = 30)
    private String nombre;

    public TipoCaptacionEntity() {
        //PARA EL FRAMEWORK
    }

    public TipoCaptacionEntity(TipoCaptacion tipoCaptacion) {
        BeanUtils.copyProperties(tipoCaptacion, this);
    }

    public TipoCaptacion toTipoCaptacion() {
        TipoCaptacion tipoCaptacion = new TipoCaptacion();
        BeanUtils.copyProperties(this, tipoCaptacion);
        return tipoCaptacion;
    }
}
