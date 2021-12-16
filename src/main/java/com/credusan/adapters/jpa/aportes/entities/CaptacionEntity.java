package com.credusan.adapters.jpa.aportes.entities;

import com.credusan.domain.models.aportes.Asociado;
import com.credusan.domain.models.aportes.Captacion;
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
public class CaptacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "captid")
    private Integer idCaptacion;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipcapid", nullable = false, foreignKey = @ForeignKey(name = "fk_capt_tipcap"))
    private TipoCaptacionEntity tipoCaptacionEntity;
    @Column(name = "captnumerocuenta", nullable = false)
    private Integer numeroCuenta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asocid", nullable = false, foreignKey = @ForeignKey(name = "fk_capt_asoc"))
    private AsociadoEntity asociadoEntity;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tiescaid", nullable = false, foreignKey = @ForeignKey(name = "fk_capt_tiesca"))
    private TipoEstadoCaptacionEntity tipoEstadoCaptacionEntity;
    @Column(name = "captfechaapertura", nullable = false)
    private LocalDate fechaApertura;
    @Column(name = "captsaldo", nullable = false, scale = 15, precision = 2)
    private Double saldo;

    public CaptacionEntity() {
        //Necesario para el framework
    }

    public CaptacionEntity(Captacion captacion) {
        BeanUtils.copyProperties(captacion, this);
        this.setAsociadoEntity(this.asociadoToEntity(captacion.getAsociado()));
        this.setTipoCaptacionEntity(new TipoCaptacionEntity(captacion.getTipoCaptacion()));
        this.setTipoEstadoCaptacionEntity(new TipoEstadoCaptacionEntity(captacion.getTipoEstadoCaptacion()));
    }

    public Captacion toCaptacion() {
        Captacion captacion = new Captacion();
        BeanUtils.copyProperties(this, captacion);
        captacion.setAsociado(this.asociadoEntity.toOnlyAsociado());
        captacion.setTipoCaptacion(this.tipoCaptacionEntity.toTipoCaptacion());
        captacion.setTipoEstadoCaptacion(this.tipoEstadoCaptacionEntity.toTipoEstadoCaptacion());
        return captacion;
    }

    private AsociadoEntity asociadoToEntity(Asociado asociado) {
        AsociadoEntity asociadoEntity = new AsociadoEntity();
        BeanUtils.copyProperties(asociado, asociadoEntity);
        return asociadoEntity;
    }

}
