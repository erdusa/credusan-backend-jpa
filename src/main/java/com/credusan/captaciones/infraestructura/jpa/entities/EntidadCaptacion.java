package com.credusan.captaciones.infraestructura.jpa.entities;

import com.credusan.asociados.infraestructura.jpa.entities.EntidadAsociado;
import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.captaciones.dominio.modelos.Captacion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "captacion",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_captacion", columnNames = {"tipcapid", "captnumerocuenta"})
        },
        indexes = {
                @Index(name = "idx_capt_tiescaid", columnList = "tiescaid"),
                @Index(name = "idx_capt_tipcapid", columnList = "tipcapid"),
                @Index(name = "idx_capt_asocid", columnList = "asocid")
        })
public class EntidadCaptacion {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "captid")
    private Integer idCaptacion;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipcapid", nullable = false, foreignKey = @ForeignKey(name = "fk_capt_tipcap"))
    private EntidadTipoCaptacion entidadTipoCaptacion;
    @Column(name = "captnumerocuenta", nullable = false)
    private Integer numeroCuenta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asocid", nullable = false, foreignKey = @ForeignKey(name = "fk_capt_asoc"))
    private EntidadAsociado entidadAsociado;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tiescaid", nullable = false, foreignKey = @ForeignKey(name = "fk_capt_tiesca"))
    private EntidadTipoEstadoCaptacion entidadTipoEstadoCaptacion;
    @Column(name = "captfechaapertura", nullable = false)
    private LocalDate fechaApertura;
    @Column(name = "captsaldo", nullable = false, scale = 15, precision = 2)
    private Double saldo;

    public EntidadCaptacion() {
        //Necesario para el framework
    }

    public EntidadCaptacion(Captacion captacion) {
        BeanUtils.copyProperties(captacion, this);
        this.setEntidadAsociado(this.asociadoToEntity(captacion.getAsociado()));
        this.setEntidadTipoCaptacion(new EntidadTipoCaptacion(captacion.getTipoCaptacion()));
        this.setEntidadTipoEstadoCaptacion(new EntidadTipoEstadoCaptacion(captacion.getTipoEstadoCaptacion()));
    }

    public Captacion toCaptacion() {
        Captacion captacion = new Captacion();
        BeanUtils.copyProperties(this, captacion);
        captacion.setAsociado(this.entidadAsociado.toOnlyAsociado());
        captacion.setTipoCaptacion(this.entidadTipoCaptacion.toTipoCaptacion());
        captacion.setTipoEstadoCaptacion(this.entidadTipoEstadoCaptacion.toTipoEstadoCaptacion());
        return captacion;
    }

    private EntidadAsociado asociadoToEntity(Asociado asociado) {
        EntidadAsociado entidadAsociado = new EntidadAsociado();
        BeanUtils.copyProperties(asociado, entidadAsociado);
        return entidadAsociado;
    }

}
