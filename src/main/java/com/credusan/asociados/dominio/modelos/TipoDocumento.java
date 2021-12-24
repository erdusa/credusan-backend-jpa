package com.credusan.asociados.dominio.modelos;

import lombok.Data;

@Data
public class TipoDocumento {
    private Integer idTipoDocumento;
    private String abreviatura;
    private String descripcion;

    public TipoDocumento() {
    }

    public TipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }
}
