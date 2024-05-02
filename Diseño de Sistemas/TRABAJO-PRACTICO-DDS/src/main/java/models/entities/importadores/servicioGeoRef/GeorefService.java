package models.entities.importadores.servicioGeoRef;

import models.entities.domain.georef.ListadoDeMunicipios;
import models.entities.domain.georef.ListadoDeProvincias;
import models.entities.domain.georef.ListadoDeLocalidades;
import models.entities.domain.georef.Ubicacion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeorefService {
    //No se si es necesario hacer todas las llamadas posibles porque solo se van a usar la mitad
    @GET("provincias")
    Call<ListadoDeProvincias> provincias();
    @GET("provincias")
    Call<ListadoDeProvincias> provincias(@Query("campos") String campos);
    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") Long idProvincia);
    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") Long idProvincia,@Query("campos") String campos);
    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") Long idProvincia,@Query("campos") String campos,@Query("max") int max);
    @GET("localidades")
    Call<ListadoDeLocalidades> localidades(@Query("municipio") Long idMunicipio);
    @GET("localidades")
    Call<ListadoDeLocalidades> localidades(@Query("municipio") Long idMunicipio,@Query("campos") String campos);
    @GET("localidades")
    Call<ListadoDeLocalidades> localidades(@Query("municipio") Long idMunicipio,@Query("campos") String campos,@Query("max") int max);
    @GET("ubicacion")
    Call<Ubicacion> ubicacion(@Query("lat") double lat, @Query("lon") double lon); // TODO compatibilidad con el resto del programa
}