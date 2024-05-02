package com.stack.controllers;

import com.stack.entity.Incidente;
import com.stack.services.IncidentesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restprueba {
    private IncidentesService service;

    public restprueba(IncidentesService service) {
        this.service = service;
    }

    @GetMapping("/hola")
    public String prueba() {
        Incidente incidente = service.findById(1L);
        StringBuilder retornar = new StringBuilder();
        retornar.append("Comunidad: ").append(incidente.getComunidad().getNombre()).append("\n")
                .append("Establecimiento: ").append(incidente.getNombreEstablecimiento()).append("\n")
                .append("Servicio: ").append(incidente.getNombreServicio()).append("\n")
                .append("Creador: ").append(incidente.getNombreCreador()).append("\n")
                .append("Analizador: ").append(incidente.getAnalizador().getNombre()).append("\n");
        return retornar.toString();
    }
}
