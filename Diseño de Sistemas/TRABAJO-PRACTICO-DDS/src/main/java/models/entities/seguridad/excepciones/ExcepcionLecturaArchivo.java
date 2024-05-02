package models.entities.seguridad.excepciones;

public class ExcepcionLecturaArchivo extends RuntimeException {
    public ExcepcionLecturaArchivo() {
        super("No se puede leer el archivo");
    }
}
