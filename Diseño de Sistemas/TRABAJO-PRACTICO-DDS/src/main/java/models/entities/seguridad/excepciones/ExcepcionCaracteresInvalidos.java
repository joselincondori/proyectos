package models.entities.seguridad.excepciones;

public class ExcepcionCaracteresInvalidos extends RuntimeException {
    public ExcepcionCaracteresInvalidos() {
        super("La contrasenia contiene caracterers invalidos");
    }
}
