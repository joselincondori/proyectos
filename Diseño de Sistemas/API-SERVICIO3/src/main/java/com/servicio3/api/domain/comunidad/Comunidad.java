package com.servicio3.api.domain.comunidad;

import com.servicio3.api.domain.incidentes.Incidente;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Comunidad {
    private int cantMiembrosAfectados;
    private List<Incidente> incidentes;
}

