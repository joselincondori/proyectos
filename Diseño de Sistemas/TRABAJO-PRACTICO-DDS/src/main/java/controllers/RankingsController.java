package controllers;

import io.javalin.http.Context;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.rankings.*;
import models.repositories.EntidadRepository;
import models.repositories.IncidenteRepository;
import servicios.servicio3Api.Servicio3API;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingsController {
    private final EntidadRepository entidadRepository;
    private final IncidenteRepository incidenteRepository;
    private final Servicio3API servicio3API;

    public RankingsController() {
        this.entidadRepository = new EntidadRepository();
        this.incidenteRepository = new IncidenteRepository();
        this.servicio3API = new Servicio3API();
    }

    public void rankings(Context context) {
        context.render("rankings/rankings.hbs");
    }

    public void ranking1(Context context) {
        Map<String, Object> model = new HashMap<>();
        ConceptoRanking concepto = new RankingEntidadesMayorTiempoPromedioCierres();
        List<Entidad> entidades = (List<Entidad>)entidadRepository.findAll();
        List<Incidente> incidentes = (List<Incidente>)incidenteRepository.findAll();
        RankingGenerado ranking = concepto.generar(entidades, incidentes);
        model.put("ranking", ranking);
        context.render("rankings/ranking.hbs", model);
    }

    public void ranking2(Context context) {
        try {
            Map<String, Object> model = new HashMap<>();
            RankingGenerado ranking = servicio3API.getRanking();
            model.put("ranking", ranking);
            ranking.setDescripcion("Ranking entidades segun impacto de incidentes");
            context.render("rankings/ranking.hbs", model);
        } catch (IOException e) {
            e.printStackTrace();
            context.status(500);
        }
    }

    public void ranking3(Context context) {
        Map<String, Object> model = new HashMap<>();
        ConceptoRanking concepto = new RankingEntidadesMayorCantIncidentes();
        List<Entidad> entidades = (List<Entidad>)entidadRepository.findAll();
        List<Incidente> incidentes = (List<Incidente>)incidenteRepository.findAll();
        RankingGenerado ranking = concepto.generar(entidades, incidentes);
        model.put("ranking",ranking);
        context.render("rankings/ranking.hbs", model);
    }
}
