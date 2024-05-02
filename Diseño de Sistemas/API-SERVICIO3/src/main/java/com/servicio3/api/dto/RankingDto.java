package com.servicio3.api.dto;

import com.servicio3.api.domain.comunidad.Comunidad;
import com.servicio3.api.domain.entidades.Entidad;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RankingDto {
    private List<Comunidad> comunidades;
    private List<Entidad> entidades;
    private float cnf;
}
