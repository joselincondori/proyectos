package models.entities.domain.rankings;

import models.entities.domain.entidades.Entidad;
import models.entities.domain.incidentes.Incidente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntradaRanking {
    private int posicion;
    private float puntaje;
    private Entidad entidad;

    public EntradaRanking(float puntaje, Entidad entidad) {
        this.puntaje = puntaje;
        this.entidad = entidad;
    }

    public EntradaRanking(int posicion, float puntaje, Entidad entidad) {
        this.posicion = posicion;
        this.puntaje = puntaje;
        this.entidad = entidad;
    }
}
