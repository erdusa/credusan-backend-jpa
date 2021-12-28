package com.credusan.asociados.infraestructura.jpa.entidades;

import com.credusan.asociados.dominio.modelos.Asociado;
import com.credusan.asociados.dominio.modelos.Beneficiario;
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
public class EntidadBeneficiario {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "beneid")
    private Integer idBeneficiario;
    @ManyToOne
    @JoinColumn(name = "asocid", nullable = false, foreignKey = @ForeignKey(name = "fk_bene_asoc"))
    private EntidadAsociado entidadAsociado;
    @Column(name = "benenombres", nullable = false, length = 30)
    private String nombres;
    @Column(name = "beneprimerapellido", nullable = false, length = 30)
    private String primerApellido;
    @Column(name = "benesegundoapellido", length = 30)
    private String segundoApellido;
    @Column(name = "beneporcentaje", precision = 3)
    private Integer porcentaje;

    public EntidadBeneficiario() {
        //Necesario para el framework
    }

    public EntidadBeneficiario(Beneficiario beneficiario) {
        BeanUtils.copyProperties(beneficiario, this);
        this.setEntidadAsociado(this.asociadoToEntity(beneficiario.getAsociado()));
    }

    public Beneficiario toBeneficiario() {
        Beneficiario beneficiario = new Beneficiario();
        BeanUtils.copyProperties(this, beneficiario);
        beneficiario.setAsociado(this.entidadAsociado.toOnlyAsociado());
        return beneficiario;
    }

    private EntidadAsociado asociadoToEntity(Asociado asociado) {
        EntidadAsociado entidadAsociado = new EntidadAsociado();
        BeanUtils.copyProperties(asociado, entidadAsociado);
        return entidadAsociado;
    }

    public void setEntidadAsociado(EntidadAsociado entidadAsociado) {
        setEntidadAsociado(entidadAsociado, true);
    }

    void setEntidadAsociado(EntidadAsociado entidadAsociado, boolean add) {
        this.entidadAsociado = entidadAsociado;
        if (entidadAsociado != null && add) {
            entidadAsociado.addBeneficiarioEntity(this, false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadBeneficiario that = (EntidadBeneficiario) o;
        return Objects.equals(entidadAsociado, that.entidadAsociado) && Objects.equals(nombres, that.nombres) && Objects.equals(primerApellido, that.primerApellido) && Objects.equals(segundoApellido, that.segundoApellido) && Objects.equals(porcentaje, that.porcentaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entidadAsociado, nombres, primerApellido, segundoApellido, porcentaje);
    }
}
