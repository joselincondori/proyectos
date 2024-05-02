package models.entities.importadores.entidadesPrestadoras;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportacionPrestadoresCSV {
    private String pathArchivo;

    public ImportacionPrestadoresCSV(String pathArchivo) {
        this.pathArchivo = pathArchivo;
    }

    public List<DatosPrestadoresCSV> importar() {
        Reader reader = null;
        CSVParser parser = null;
        List<DatosPrestadoresCSV> datos = new ArrayList<>();
        try {
            reader = Files.newBufferedReader(Paths.get(pathArchivo));
            parser = new CSVParser(reader, CSVFormat.DEFAULT.withSkipHeaderRecord().withFirstRecordAsHeader());

            for (CSVRecord registro : parser) {
                String nombrePrestador = registro.get(0);
                String nombreUsuario = registro.get(1);
                String tipo = registro.get(2);
                String apellidoUsuario = registro.get(3);
                String usuarioUsuario = registro.get(4);
                String mailUsuario = registro.get(5);

                datos.add(new DatosPrestadoresCSV(nombrePrestador, nombreUsuario, tipo, apellidoUsuario, usuarioUsuario, mailUsuario));
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        } //finally {
           // try {
                //reader.close();
                //parser.close();
           // } catch (IOException e) {
               // System.out.println(e);
            //}
       // }

        return datos;
    }
}
