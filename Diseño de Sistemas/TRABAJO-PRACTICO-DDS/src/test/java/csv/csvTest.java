package csv;

import models.entities.domain.entidades.EntidadPrestadora;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.importadores.entidadesPrestadoras.ImportadorCSV;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class csvTest {
    private static List<EntidadPrestadora> entidadesPrestadoras;
    private static List<OrganismoDeControl> organismosDeControl;

    @BeforeAll
    public static void init() {
        String archivoDePrueba = obtenerArchivoDePrueba();
        ImportadorCSV importadorCSV = new ImportadorCSV();
        entidadesPrestadoras = importadorCSV.importarEntidadesPrestadoras(archivoDePrueba);
        organismosDeControl = importadorCSV.importarOrganismosDeControl(archivoDePrueba);
    }

    private static String obtenerArchivoDePrueba() {
        ClassLoader classLoader = csvTest.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("datosCSV.csv")).getFile());
        return file.getAbsolutePath();
    }

    @Test
    @DisplayName("Se corrobora que se hayan instanciado la cantidad correcta de elementos")
    public void importarEntidadesPrestadorasCantidad(){
        assertEquals(1,entidadesPrestadoras.size());
    }

    @Test
    @DisplayName("Se corrobora que se hayan instanciado correctamente la denominacion del primer elemento")
    public void importarEntidadesPrestadorasDenominacion(){
        assertEquals("Santander", entidadesPrestadoras.get(0).getNombre());
    }

    @Test
    @DisplayName("Se corrobora que se hayan instanciado la cantidad correcta de elementos")
    public void importarOrganismosDeControlCantidad(){
        assertEquals(1, organismosDeControl.size());
    }

    @Test
    @DisplayName("Se corrobora que se hayan instanciado correctamente la denominacion del primer elemento")
    public void importarOrganismosDeControlDenominacion(){
        assertEquals("LineaB", organismosDeControl.get(0).getNombre());
    }
}
