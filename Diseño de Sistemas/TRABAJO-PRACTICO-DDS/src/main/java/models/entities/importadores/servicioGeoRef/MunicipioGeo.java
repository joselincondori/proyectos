package models.entities.importadores.servicioGeoRef;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.entities.domain.georef.Municipio;

@Getter
@AllArgsConstructor
public class MunicipioGeo {
    private Long idGeo;
    private Municipio municipio;
}
