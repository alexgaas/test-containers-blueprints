package com.alexgaas.testcontainersblueprints.blueprint.service;


import com.alexgaas.testcontainersblueprints.blueprint.model.Blueprint;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlueprintService {
  Flux<Blueprint> getBlueprints();

  Mono<Blueprint> getBlueprintById(String id);
}
