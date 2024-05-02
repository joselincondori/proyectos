package com.servicio3.api.controllers;

import com.servicio3.api.domain.rankings.GeneradorRanking;
import com.servicio3.api.domain.rankings.RankingGenerado;
import com.servicio3.api.dto.RankingDto;

import com.servicio3.api.docs_examples.RankingsExample;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/rankings/")
public class GeneradorRankingController {
    private final GeneradorRanking generadorRanking;

    public GeneradorRankingController(GeneradorRanking generadorRanking) {
        this.generadorRanking = generadorRanking;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = {
                    @Content(
                            mediaType = "application/json",
                            examples = {@ExampleObject(
                                    name = "Response Example",
                                    summary = "Response Example",
                                    value = RankingsExample.RESPONSE_EXAMPLE_ONE)},
                            schema = @Schema(implementation = RankingGenerado.class))
            })
    @PostMapping(value="/ranking",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RankingGenerado> calculateRanking(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = RankingDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Request Example",
                                            summary = "Request Example",
                                            value = RankingsExample.REQUEST_EXAMPLE_ONE)}))
            @RequestBody RankingDto rankingDto) {
        RankingGenerado ranking = null;
        try
        {
            ranking = generadorRanking.generarRanking(rankingDto);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        ResponseEntity<RankingGenerado> response;
        if(ranking != null) {
            response = new ResponseEntity<>(ranking, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

}
