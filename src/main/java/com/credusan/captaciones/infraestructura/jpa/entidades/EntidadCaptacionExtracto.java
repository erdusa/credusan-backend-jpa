package com.credusan.captaciones.infraestructura.jpa.entidades;

import com.credusan.captaciones.dominio.modelos.CaptacionExtracto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "captacionextracto")
public class EntidadCaptacionExtracto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capextid")
    private Integer idCaptacionExtracto;
    @ManyToOne(optional = false)
    @JoinColumn(name = "captid", nullable = false, foreignKey = @ForeignKey(name = "fk_capext_capt"))
    private EntidadCaptacion entidadCaptacion;
    @Column(name = "capextfecha", nullable = false)
    private LocalDate fecha;
    @Column(name = "capexthora", nullable = false)
    private LocalTime hora;
    @Column(name = "capextvalordebito", nullable = false, scale = 15, precision = 2)
    private Double valorDebito;
    @Column(name = "capextvalorcredito", nullable = false, scale = 15, precision = 2)
    private Double valorCredito;

    public EntidadCaptacionExtracto() {
        //necesario para el framework
    }

    public EntidadCaptacionExtracto(CaptacionExtracto captacionExtracto) {
        BeanUtils.copyProperties(captacionExtracto, this);
        this.setEntidadCaptacion(new EntidadCaptacion(captacionExtracto.getCaptacion()));
    }

    public CaptacionExtracto toCaptacionExtracto() {
        CaptacionExtracto captacionExtracto = new CaptacionExtracto();
        BeanUtils.copyProperties(this, captacionExtracto);
        captacionExtracto.setCaptacion(this.getEntidadCaptacion() == null ? null : this.getEntidadCaptacion().toCaptacion());
        return captacionExtracto;
    }

}
