package com.alexgaas.testcontainersblueprints.blueprint.controller;


import com.alexgaas.testcontainersblueprints.blueprint.model.Blueprint;
import com.alexgaas.testcontainersblueprints.blueprint.service.BlueprintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class BlueprintController extends BaseController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private BlueprintService service;

  @RequestMapping(path = "/blueprint/{id}", method = RequestMethod.GET)
  public Mono<ResponseEntity<Blueprint>> getBlueprint(@PathVariable String id) {
    return service
        .getBlueprintById(id)
        .flatMap(blueprint -> createResponse(blueprint, HttpStatus.OK))
        .doOnSubscribe(ctx -> log.trace("find example by id controller called()"));
  }

  @RequestMapping(path = "/blueprints", method = RequestMethod.GET)
  public Flux<ResponseEntity<Blueprint>> getBlueprints() {
    return service
        .getBlueprints()
        .flatMap(blueprint -> createResponseList(blueprint, HttpStatus.OK))
        .doOnSubscribe(ctx -> log.trace("find example by id controller called()"));
  }

  private Mono<ResponseEntity<Blueprint>> createResponse(
      Blueprint blueprint, HttpStatus httpStatus) {
    return Mono.just(new ResponseEntity<>(blueprint, httpStatus));
  }

  private Flux<ResponseEntity<Blueprint>> createResponseList(
      Blueprint blueprint, HttpStatus httpStatus) {
    return Flux.just(new ResponseEntity<>(blueprint, httpStatus));
  }
}
