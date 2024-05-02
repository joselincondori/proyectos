package models.entities.domain.georef;

import lombok.Getter;
import models.entities.importadores.servicioGeoRef.MunicipioDto;

import java.util.List;

public class ListadoDeMunicipios {
    public int cantidad;
    public int total;
    public  int inicio;
    public Parametro parametros;
    @Getter
    public List<MunicipioDto> municipios;

    private class Parametro{
        public List<String> campos;
        public int max;
        public List<String> provincia; //es una lista pero en el postman esta asi NO CAMBIAR
    }
}
