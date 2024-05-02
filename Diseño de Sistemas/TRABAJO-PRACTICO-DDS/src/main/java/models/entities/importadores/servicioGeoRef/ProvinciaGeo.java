package models.entities.importadores.servicioGeoRef;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.entities.domain.georef.Provincia;

@Getter
@AllArgsConstructor
public class ProvinciaGeo {
    private Long idGeo;
    private Provincia provincia;
}
