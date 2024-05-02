package models.entities.seguridad;

import models.entities.seguridad.excepciones.ExcepcionCaracteresInvalidos;
import models.entities.seguridad.excepciones.ExcepcionComun;
import models.entities.seguridad.validaciones.*;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeContrasenia {
    private List<Validacion> validadores;

    public ValidadorDeContrasenia() {
        this.validadores = new ArrayList<>();
        this.validadores.add(new ValidarCaracteres());
//        this.validadores.add(new Validar10KContrasenias());
        this.validadores.add(new ValidarLongitudContrasenia());
        this.validadores.add(new ValidarContraseniaNoContieneUsuario());
    }

    public boolean validarContrasenia(String nombre, String contrasenia) throws RuntimeException {
        return validadores.stream().allMatch(validacion ->
                validacion.esValida(nombre, contrasenia));
    }
}
