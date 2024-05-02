package seguridad.validador;

import models.entities.seguridad.excepciones.ExcepcionCaracteresInvalidos;
import models.entities.seguridad.excepciones.ExcepcionComun;
import models.entities.seguridad.excepciones.ExcepcionContraseniaContieneUsuario;
import models.entities.seguridad.excepciones.ExceptionLongitudContrasenia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import models.entities.seguridad.validaciones.Validar10KContrasenias;
import models.entities.seguridad.validaciones.ValidarCaracteres;
import models.entities.seguridad.validaciones.ValidarContraseniaNoContieneUsuario;
import models.entities.seguridad.validaciones.ValidarLongitudContrasenia;

public class ValidadoresContraseniaTest {

    @Test
    public void contraseniaNoContieneUsuarioEsValida(){ //deberia ser: contraseniaDistintaUsuarioEsValida
        ValidarContraseniaNoContieneUsuario validador = new ValidarContraseniaNoContieneUsuario();
        String usuario = "Joselin Carolina";
        String contrasenia = "Franco";
        Assertions.assertTrue(validador.esValida(usuario, contrasenia));
    }

    @Test
    public void contraseniaContieneUsuarioEsInvalida(){
        ValidarContraseniaNoContieneUsuario validador = new ValidarContraseniaNoContieneUsuario();
        String usuario = "Joselin";
        String contrasenia = "josinkjshdflkjhasdJOSELINNklfhsjkldahfjklsadh";
        Assertions.assertThrowsExactly(ExcepcionContraseniaContieneUsuario.class, () -> validador.esValida(usuario, contrasenia));
    }

    @Test
    public void contraseniaComun10kEsInvalida(){
        Validar10KContrasenias validador = new Validar10KContrasenias();
        Assertions.assertThrowsExactly(ExcepcionComun.class, () -> validador.esValida("usuario", "123456"));
    }

    @Test
    public void contraseniaNoComun10kEsValida(){
        Validar10KContrasenias validador = new Validar10KContrasenias();
        Assertions.assertTrue(validador.esValida("usuario", "sajkdhfjksdhfk"));
    }

    @Test
    public void contraseniaConCaracteresASCIIesValida(){
        ValidarCaracteres validador = new ValidarCaracteres();
        String usuario = "Juan";
        String contraseniaConCaracteresASCII = "constrasenia";
        Assertions.assertTrue(validador.esValida(usuario, contraseniaConCaracteresASCII));
    }

    @Test
    public void contraseniaConCaracteresUNICODEesValida(){
        ValidarCaracteres validador = new ValidarCaracteres();
        String usuario = "Juan";
        String contraseniaConCaracteresUNICODE = "☀♣♦♥♠";
        Assertions.assertTrue(validador.esValida(usuario, contraseniaConCaracteresUNICODE));
    }

    @Test
    public void contraseniaConCaracteresInvalidosEsInvalida() {
        ValidarCaracteres validador = new ValidarCaracteres();
        String usuario = "Juan";
        String contraseniaConCaracterNulo = "";
        Assertions.assertThrowsExactly(ExcepcionCaracteresInvalidos.class, () -> validador.esValida(usuario, contraseniaConCaracterNulo));
    }

    @Test
    public void contraseniaConLongitudMayorOIgualA8EsValida(){
        ValidarLongitudContrasenia validador = new ValidarLongitudContrasenia();
        String usuario = "Agustin";
        String contrasenia = "123456789";
        Assertions.assertTrue(validador.esValida(usuario, contrasenia));

    }

    @Test
    public void contraseniaConLongitudMenorA8EsInvalida(){
        ValidarLongitudContrasenia validador = new ValidarLongitudContrasenia();
        String usuario = "Agustin";
        String contrasenia = "1234";
        Assertions.assertThrowsExactly(ExceptionLongitudContrasenia.class, () -> validador.esValida(usuario, contrasenia));

    }
}
