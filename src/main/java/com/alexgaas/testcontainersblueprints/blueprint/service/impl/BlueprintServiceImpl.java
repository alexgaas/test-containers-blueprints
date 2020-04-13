package com.alexgaas.testcontainersblueprints.blueprint.service.impl;


import com.alexgaas.testcontainersblueprints.blueprint.model.Blueprint;
import com.alexgaas.testcontainersblueprints.blueprint.repository.BlueprintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class BlueprintServiceImpl
    implements com.alexgaas.testcontainersblueprints.blueprint.service.BlueprintService {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private BlueprintRepository blueprintRepository;

  @Override
  public Flux<Blueprint> getBlueprints() {
    return Flux.defer(() -> Flux.fromIterable(blueprintRepository.findAll()))
        .subscribeOn(Schedulers.elastic())
        .doOnSubscribe(ctx -> log.trace("get all ExamplesCalled()"));
  }

  @Override
  public Mono<Blueprint> getBlueprintById(String id) {
    return Mono.defer(
            () ->
                Mono.just(
                    this.blueprintRepository.findById(id).isPresent()
                        ? blueprintRepository.findById(id).get()
                        : null))
        .subscribeOn(Schedulers.elastic())
        .doOnSubscribe(ctx -> log.trace("find by example Id called()"));
  }
}
