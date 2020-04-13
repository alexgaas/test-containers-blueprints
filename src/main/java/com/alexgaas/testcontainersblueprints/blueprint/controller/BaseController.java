package com.alexgaas.testcontainersblueprints.blueprint.controller;


import java.util.Collections;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

public class BaseController {

  @ExceptionHandler(IllegalArgumentException.class)
  public Mono<ResponseEntity<Map<String, String>>> invalidBlueprintId(IllegalArgumentException e) {
    return Mono.just(Collections.singletonMap("errorMessage", e.getMessage()))
        .flatMap(
            responseBody -> Mono.just(new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST)));
  }
}
