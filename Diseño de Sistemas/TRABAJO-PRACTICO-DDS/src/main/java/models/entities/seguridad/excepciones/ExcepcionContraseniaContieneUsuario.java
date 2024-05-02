package models.entities.seguridad.excepciones;

public class ExcepcionContraseniaContieneUsuario extends RuntimeException {
    public ExcepcionContraseniaContieneUsuario() {
        super("La contrasenia no debe contiene el nombre de usuario");
    }
}
