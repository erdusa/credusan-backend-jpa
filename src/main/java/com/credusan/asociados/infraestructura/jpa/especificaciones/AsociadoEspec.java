package com.credusan.asociados.infraestructura.jpa.especificaciones;

import com.credusan.asociados.infraestructura.jpa.entidades.EntidadAsociado;
import org.springframework.data.jpa.domain.Specification;

public class AsociadoEspec {

    public static Specification<EntidadAsociado> esAsociadoActivo() {
        return (root, query, builder) -> builder.isTrue(root.get("activo"));
    }

}
