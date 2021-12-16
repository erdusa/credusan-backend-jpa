package com.credusan.adapters.jpa.aportes.entities;

import com.credusan.domain.models.aportes.Asociado;
import com.credusan.domain.models.aportes.Beneficiario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "beneficiario", indexes = {@Index(name = "idx_bene_asocid", columnList = "asocid")})
public class BeneficiarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "beneid")
    private Integer idBeneficiario;
    @ManyToOne
    @JoinColumn(name = "asocid", nullable = false, foreignKey = @ForeignKey(name = "fk_bene_asoc"))
    private AsociadoEntity asociadoEntity;
    @Column(name = "benenombres", nullable = false, length = 30)
    private String nombres;
    @Column(name = "beneprimerapellido", nullable = false, length = 30)
    private String primerApellido;
    @Column(name = "benesegundoapellido", length = 30)
    private String segundoApellido;
    @Column(name = "beneporcentaje", precision = 3)
    private Integer porcentaje;

    public BeneficiarioEntity() {
        //Necesario para el framework
    }

    public BeneficiarioEntity(Beneficiario beneficiario) {
        BeanUtils.copyProperties(beneficiario, this);
        this.setAsociadoEntity(this.asociadoToEntity(beneficiario.getAsociado()));
    }

    public Beneficiario toBeneficiario() {
        Beneficiario beneficiario = new Beneficiario();
        BeanUtils.copyProperties(this, beneficiario);
        beneficiario.setAsociado(this.asociadoEntity.toOnlyAsociado());
        return beneficiario;
    }

    private AsociadoEntity asociadoToEntity(Asociado asociado) {
        AsociadoEntity asociadoEntity = new AsociadoEntity();
        BeanUtils.copyProperties(asociado, asociadoEntity);
        return asociadoEntity;
    }

    public void setAsociadoEntity(AsociadoEntity asociadoEntity) {
        setAsociadoEntity(asociadoEntity, true);
    }

    void setAsociadoEntity(AsociadoEntity asociadoEntity, boolean add) {
        this.asociadoEntity = asociadoEntity;
        if (asociadoEntity != null && add) {
            asociadoEntity.addBeneficiarioEntity(this, false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeneficiarioEntity that = (BeneficiarioEntity) o;
        return Objects.equals(asociadoEntity, that.asociadoEntity) && Objects.equals(nombres, that.nombres) && Objects.equals(primerApellido, that.primerApellido) && Objects.equals(segundoApellido, that.segundoApellido) && Objects.equals(porcentaje, that.porcentaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asociadoEntity, nombres, primerApellido, segundoApellido, porcentaje);
    }
}
