package models.entities.seguridad.excepciones;

public class ExceptionLongitudContrasenia extends RuntimeException {
    public ExceptionLongitudContrasenia() {
        super("La contrasenia debe tener al menos 8 caracteres");
    }
}
