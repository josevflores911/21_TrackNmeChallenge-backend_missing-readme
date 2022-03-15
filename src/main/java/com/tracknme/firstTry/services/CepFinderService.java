package com.tracknme.firstTry.services;

import com.tracknme.firstTry.entities.CepDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class CepFinderService {

    private final WebClient webClient;

    public CepFinderService(WebClient.Builder builder) {
       webClient = builder.baseUrl("https://viacep.com.br/ws/").build();
    }

    public CepDetailsDto findCep(Integer cepNumber){

        //log.info("searching cep with number ",cepNumber);
        System.out.printf("searching cep with number %s",cepNumber);

        return webClient
                .get()
                .uri(cepNumber + "/json/")
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error->Mono.error(new RuntimeException("please check paramenters")))
                .bodyToMono(CepDetailsDto.class).block();

    }

    //cep = cep.replaceAll("[^0-9]","");

}










//return a json with the info
    /*
    public Mono<Cep> find(String cepNumber){

        log.info("searching cep with number %s",cepNumber);

        return webClient
                .get()
                .uri(cepNumber + "/json/")
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error->Mono.error(new RuntimeException("please check paramenters")))
                .bodyToMono(Cep.class);

    }
    */

