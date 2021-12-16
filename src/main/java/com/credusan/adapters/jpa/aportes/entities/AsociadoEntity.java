package com.credusan.adapters.jpa.aportes.entities;

import com.credusan.domain.models.aportes.Asociado;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Entity
@Table(name = "asociado", uniqueConstraints = {@UniqueConstraint(name = "uk_asociado", columnNames = {"tipdocid", "asocnumerodocumento"})})
public class AsociadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asocid")
    private Integer idAsociado;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipdocid", nullable = false, foreignKey = @ForeignKey(name = "fk_asoc_tipdoc"))
    private TipoDocumentoEntity tipoDocumentoEntity;
    @Column(name = "asocnumerodocumento", nullable = false, length = 15)
    private String numeroDocumento;
    @Column(name = "asocnombres", nullable = false, length = 30)
    private String nombres;
    @Column(name = "asocprimerapellido", nullable = false, length = 30)
    private String primerApellido;
    @Column(name = "asocsegundoapellido", length = 30)
    private String segundoApellido;
    @Column(name = "asocfechanacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    @OneToMany(mappedBy = "asociadoEntity", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @ToString.Exclude
    private List<BeneficiarioEntity> beneficiarioEntities;

    public AsociadoEntity() {
        //Necesario para el framework
    }

    public AsociadoEntity(Asociado asociado) {
        BeanUtils.copyProperties(asociado, this);
        this.setTipoDocumentoEntity(new TipoDocumentoEntity(asociado.getTipoDocumento()));

        this.limpiarBeneficiarios();
        if (asociado.getBeneficiarios() != null && asociado.getBeneficiarios().size() > 0) {
            asociado.getBeneficiarios().forEach(beneficiario -> addBeneficiarioEntity(new BeneficiarioEntity(beneficiario)));
        }
    }

    private void limpiarBeneficiarios() {
        //truco para eliminar los beneficiarios cuando no vengan registros
        //al usar la propiedad orphanRemoval = true
        this.setBeneficiarioEntities(new ArrayList<>());
        this.getBeneficiarioEntities().clear();
    }

    public Asociado toAsociado() {
        Asociado asociado = new Asociado();
        BeanUtils.copyProperties(this, asociado);

        asociado.setTipoDocumento(this.tipoDocumentoEntity == null ? null : this.tipoDocumentoEntity.toTipoDocumento());

        asociado.setBeneficiarios(this.beneficiarioEntities == null ? null : this.beneficiarioEntities.stream()
                .map(BeneficiarioEntity::toBeneficiario)
                .collect(Collectors.toList()));

        return asociado;
    }

    public Asociado toOnlyAsociado() {
        Asociado asociado = new Asociado();
        BeanUtils.copyProperties(this, asociado);
        return asociado;
    }

    public void addBeneficiarioEntity(BeneficiarioEntity beneficiarioEntity) {
        addBeneficiarioEntity(beneficiarioEntity, true);
    }

    void addBeneficiarioEntity(BeneficiarioEntity beneficiarioEntity, boolean set) {
        if (beneficiarioEntity == null) {
            return;
        }

        if (getBeneficiarioEntities() == null) {
            setBeneficiarioEntities(new ArrayList<>());
        }

        if (getBeneficiarioEntities().contains(beneficiarioEntity)) {
            getBeneficiarioEntities().set(getBeneficiarioEntities().indexOf(beneficiarioEntity), beneficiarioEntity);
        } else {
            getBeneficiarioEntities().add(beneficiarioEntity);
        }
        if (set) {
            beneficiarioEntity.setAsociadoEntity(this, false);
        }

    }

    public void removeBeneficiarioEntity(BeneficiarioEntity beneficiarioEntity) {
        getBeneficiarioEntities().remove(beneficiarioEntity);
        beneficiarioEntity.setAsociadoEntity(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsociadoEntity that = (AsociadoEntity) o;
        return Objects.equals(tipoDocumentoEntity, that.tipoDocumentoEntity) && Objects.equals(numeroDocumento, that.numeroDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoDocumentoEntity, numeroDocumento);
    }
}
