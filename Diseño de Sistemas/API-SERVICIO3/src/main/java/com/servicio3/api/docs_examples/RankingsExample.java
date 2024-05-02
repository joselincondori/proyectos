package com.servicio3.api.docs_examples;

public interface RankingsExample {
    String REQUEST_EXAMPLE_ONE = "{\"cnf\": 25,\"comunidades\": [{\"cantMiembrosAfectados\": 5,\"incidentes\": [{\"fechaHoraApertura\": \"2023-09-12T23:17:38.951Z\",\"idEstablecimiento\": 0},{\"fechaHoraApertura\": \"2023-09-10T23:17:38.951Z\",\"idEstablecimiento\": 1},{\"fechaHoraApertura\": \"2023-09-11T23:17:38.951Z\",\"fechaHoraCierre\": \"2023-09-12T23:17:38.951Z\",\"idEstablecimiento\": 2},{\"fechaHoraApertura\": \"2023-09-12T23:17:38.951Z\",\"idEstablecimiento\": 3},{\"fechaHoraApertura\": \"2023-09-12T23:17:38.951Z\",\"fechaHoraCierre\": \"2023-09-14T23:17:38.951Z\",\"idEstablecimiento\": 4}]}],\"entidades\": [{\"nombre\": \"entidad1\",\"id\": 1,\"idEstablecimientos\": [0, 1]},{\"nombre\": \"entidad2\",\"id\": 2,\"idEstablecimientos\": [2, 3]},{\"nombre\": \"entidad3\",\"id\": 3,\"idEstablecimientos\": [4]}]}";

    String RESPONSE_EXAMPLE_ONE = "{\"descripcion\": \"ranking\",\"ranking\": [{\"puesto\": 1,\"puntaje\": 500.0,\"entidad\": \"entidad1\",\"entidadId\": 1},{\"puesto\": 2,\"puntaje\": 490.0,\"entidad\": \"entidad2\",\"entidadId\": 2},{\"puesto\": 3,\"puntaje\": 240.0,\"entidad\": \"entidad3\",\"entidadId\": 3}]}";
}
