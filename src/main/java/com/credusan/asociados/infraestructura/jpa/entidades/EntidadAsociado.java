package com.credusan.asociados.infraestructura.jpa.entidades;

import com.credusan.asociados.dominio.modelos.Asociado;
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
public class EntidadAsociado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asocid")
    private Integer idAsociado;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipdocid", nullable = false, foreignKey = @ForeignKey(name = "fk_asoc_tipdoc"))
    private EntidadTipoDocumento entidadTipoDocumento;
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
    @OneToMany(mappedBy = "entidadAsociado", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<EntidadBeneficiario> beneficiarioEntities;

    public EntidadAsociado() {
        //Necesario para el framework
    }

    public EntidadAsociado(Asociado asociado) {
        BeanUtils.copyProperties(asociado, this);
        this.setEntidadTipoDocumento(new EntidadTipoDocumento(asociado.getTipoDocumento()));

        this.limpiarBeneficiarios();
        if (asociado.getBeneficiarios() != null && asociado.getBeneficiarios().size() > 0) {
            asociado.getBeneficiarios().forEach(beneficiario -> addBeneficiarioEntity(new EntidadBeneficiario(beneficiario)));
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

        asociado.setTipoDocumento(this.entidadTipoDocumento == null ? null : this.entidadTipoDocumento.toTipoDocumento());

        asociado.setBeneficiarios(this.beneficiarioEntities == null ? null : this.beneficiarioEntities.stream()
                .map(EntidadBeneficiario::toBeneficiario)
                .collect(Collectors.toList()));

        return asociado;
    }

    public Asociado toOnlyAsociado() {
        Asociado asociado = new Asociado();
        BeanUtils.copyProperties(this, asociado);
        return asociado;
    }

    public void addBeneficiarioEntity(EntidadBeneficiario entidadBeneficiario) {
        addBeneficiarioEntity(entidadBeneficiario, true);
    }

    void addBeneficiarioEntity(EntidadBeneficiario entidadBeneficiario, boolean set) {
        if (entidadBeneficiario == null) {
            return;
        }

        if (getBeneficiarioEntities() == null) {
            setBeneficiarioEntities(new ArrayList<>());
        }

        if (getBeneficiarioEntities().contains(entidadBeneficiario)) {
            getBeneficiarioEntities().set(getBeneficiarioEntities().indexOf(entidadBeneficiario), entidadBeneficiario);
        } else {
            getBeneficiarioEntities().add(entidadBeneficiario);
        }
        if (set) {
            entidadBeneficiario.setEntidadAsociado(this, false);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadAsociado that = (EntidadAsociado) o;
        return Objects.equals(entidadTipoDocumento, that.entidadTipoDocumento) && Objects.equals(numeroDocumento, that.numeroDocumento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entidadTipoDocumento, numeroDocumento);
    }
}
