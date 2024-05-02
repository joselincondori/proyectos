package controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PrestacionServicioDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
