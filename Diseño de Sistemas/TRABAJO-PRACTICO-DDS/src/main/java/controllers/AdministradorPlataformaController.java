package controllers;

import controllers.dtos.UbicacionDto;
import io.javalin.http.Context;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.entidades.EntidadPrestadora;
import models.entities.domain.entidades.Establecimiento;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.domain.georef.Localidad;
import models.entities.domain.georef.Municipio;
import models.entities.domain.servicio.Servicio;
import models.repositories.*;
import server.utils.ICrudViewsHandler;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdministradorPlataformaController {
    private final MunicipioRepository municipioRepository;
    private final LocalidadRepository localidadRepository;

    public AdministradorPlataformaController() {
        this.municipioRepository = new MunicipioRepository();
        this.localidadRepository = new LocalidadRepository();
    }

    public void index(Context context) {
        context.render("admin-plataforma/admin-plataforma.hbs");
    }

    public void getMunicipiosDeProvincia(Context context) {
        Long provinciaId = Long.parseLong(context.pathParam("id"));
        List<Municipio> municipios = municipioRepository.getAllByProvinciaId(provinciaId);
        if (!municipios.isEmpty()) {
            List<UbicacionDto> dtos = municipios.stream().map(m -> new UbicacionDto(m.getId(), m.getNombre())).toList();
            context.json(dtos);
            context.status(200);
        } else
            context.status(400);
    }

    public void getLocalidadesDeMunicipio(Context context) {
        Long municipioId = Long.parseLong(context.pathParam("id"));
        List<Localidad> localidades = localidadRepository.getAllByMunicipioId(municipioId);
        if (!localidades.isEmpty()) {
            List<UbicacionDto> dtos = localidades.stream().map(l -> new UbicacionDto(l.getId(), l.getNombre())).toList();
            context.json(dtos);
            context.status(200);
        } else
            context.status(400);
    }
}

