package com.credusan.captaciones.infraestructura.jpa.especificaciones;

import com.credusan.captaciones.infraestructura.jpa.entidades.EntidadCaptacionExtracto;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CaptacionExtractoEspec {

    public static Specification<EntidadCaptacionExtracto> esFechaMayorOIgualQue(LocalDate fechaInicial) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("fecha"), fechaInicial);
    }

    public static Specification<EntidadCaptacionExtracto> esFechaMenorOIgualQue(LocalDate fechaFinal) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("fecha"), fechaFinal);
    }

    public static Specification<EntidadCaptacionExtracto> perteneceACaptacion(Integer idCaptacion) {
        return (root, query, builder) -> builder.equal(root.get("entidadCaptacion").get("idCaptacion"), idCaptacion);
    }

}
