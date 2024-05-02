package models.entities.domain.georef;

public class CalculadorCercania {
    private static double DIFERENCIA_MAX = 0.01; // TODO NI IDEA QUE VALOR PONER
    private static CalculadorCercania instance;

    public static CalculadorCercania getInstance() {
        if(instance == null) {
            instance = new CalculadorCercania();
        }
        return instance;
    }

    private CalculadorCercania() {}

    public Boolean estaCerca(Ubicacion ubi1, Ubicacion ubi2) {
        double difLatitud = Math.abs(ubi1.getLatitud() - ubi2.getLatitud());
        double difLongitud = Math.abs(ubi1.getLongitud() - ubi2.getLongitud());
        return difLatitud < DIFERENCIA_MAX && difLongitud < DIFERENCIA_MAX;
    }
}
