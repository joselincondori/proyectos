package models.entities.domain.georef;

import lombok.Getter;
import models.entities.importadores.servicioGeoRef.LocalidadDto;

import java.util.List;

public class ListadoDeLocalidades {
    public int cantidad;
    public int inicio;
    public int total;
    public Parametro parametros;
    @Getter
    public List<LocalidadDto> localidades;

    private class Parametro{
        public List<String> campos;
        public int max;
        public  List<String> municipio;
    }
}