package controllers.dtos.servicio3.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EntidadDto {
    private Long id;
    private String nombre;
    private List<Long> idEstablecimientos;
}
