package models.entities.seguridad.excepciones;

public class ExcepcionContraseniaIncorrecta extends RuntimeException {
    public ExcepcionContraseniaIncorrecta() {
        super("La contrasenia ingresada es incorrecta, intente de nuevo.");
    }
}
