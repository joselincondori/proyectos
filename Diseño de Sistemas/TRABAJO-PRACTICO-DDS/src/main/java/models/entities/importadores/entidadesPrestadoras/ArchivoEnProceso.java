package models.entities.importadores.entidadesPrestadoras;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchivoEnProceso {
    private String nombre;
    private EstadoEnProceso estado;

    public ArchivoEnProceso(String nombre, EstadoEnProceso estado) {
        this.nombre = nombre;
        this.estado = estado;
    }
}