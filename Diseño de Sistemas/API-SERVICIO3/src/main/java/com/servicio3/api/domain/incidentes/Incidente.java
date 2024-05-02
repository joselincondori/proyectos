package com.servicio3.api.domain.incidentes;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Incidente {
    private LocalDateTime fechaHoraApertura;
    private LocalDateTime fechaHoraCierre;
    private int idEstablecimiento;

    public Long calcularDuracion() {
        if(fechaHoraCierre == null) {
            return -1L;
        } else {
            return Duration.between(fechaHoraApertura, fechaHoraCierre).toHours();
        }
    }

    public Boolean estaAbierto() {
        return fechaHoraCierre == null;
    }
}
