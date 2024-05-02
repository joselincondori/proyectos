package seguridad.validador;

import models.entities.seguridad.encriptadores.Encriptador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncriptadorTest {
    private Encriptador encriptador;

    @Test
    @DisplayName("Se comprueba que funciona el algoritmo de encriptamiento")
    public void contraseniaEncriptada() throws NoSuchAlgorithmException {
        encriptador = new Encriptador();
        String hashCorrecto = "siHZ27CDp/M0KNfCo8MZiuklYU1wIQ4ocWzKp81N23k=";
        String miHash = encriptador.encriptarContrasenia("hola");

        assertEquals(hashCorrecto, miHash);
    }
}
