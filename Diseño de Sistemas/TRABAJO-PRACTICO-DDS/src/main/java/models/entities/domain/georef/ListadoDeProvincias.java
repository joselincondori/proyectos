package models.entities.domain.georef;

import lombok.Getter;
import models.entities.importadores.servicioGeoRef.ProvinciaDto;

import java.util.List;

public class ListadoDeProvincias {
    public int cantidad;
    public int total;
    public int inicio;
    public Parametro parametros;
    @Getter
    public List<ProvinciaDto> provincias;

    private class Parametro {
        public List<String> campos;
    }
}
