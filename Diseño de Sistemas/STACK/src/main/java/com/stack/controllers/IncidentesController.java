package com.stack.controllers;

import com.stack.entity.Comunidad;
import com.stack.entity.Incidente;
import com.stack.services.ComunidadService;
import com.stack.services.IncidentesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/app/")
public class IncidentesController extends com.stack.controllers.Controller {

    private IncidentesService incidentesService;
    private ComunidadService comunidadService;

    @Value("${base-redirect-url}")
    String baseRedirectUrl;

    public IncidentesController(IncidentesService incidentesService, ComunidadService comunidadService) {
        this.incidentesService = incidentesService;
        this.comunidadService = comunidadService;
    }

    @GetMapping("comunidades/{idComunidad}/incidentes")
    public String incidentesDeComunidad(Model model, @PathVariable Long idComunidad, @RequestParam(name = "filtro", required = false) String filtro) {
        Comunidad comunidad;
        if (comunidadService.findById(idComunidad) == null) {
            return "/app/portal";
        }
        comunidad = comunidadService.findById(idComunidad);
        List<Incidente> incidentes = aplicarFiltro(comunidad, filtro);
        model.addAttribute("incidentes", incidentes);
        model.addAttribute("url", baseRedirectUrl);
        model.addAttribute("comunidad", comunidad);
        return "incidentes_comunidad";
    }

    public List<Incidente> aplicarFiltro(Comunidad comunidad, String filtro) {
        List<Incidente> incidentes = incidentesService.findIncidentesDeComunidad(comunidad);

        if (filtro != null) {
            switch (filtro) {
                case "cerrados" -> incidentes = incidentesService.findIncidentesCerradosDeComunidad(comunidad);
                case "abiertos" -> incidentes = incidentesService.findIncidentesAbiertosDeComunidad(comunidad);
            }
        }
        return incidentes;
    }
}