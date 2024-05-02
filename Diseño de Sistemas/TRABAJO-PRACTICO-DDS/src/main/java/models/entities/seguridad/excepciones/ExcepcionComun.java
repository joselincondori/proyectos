package models.entities.seguridad.excepciones;

public class ExcepcionComun extends RuntimeException {

    public ExcepcionComun() {
        super("La contrasenia es muy facil, intente con algo mas dificil");
    }
}