package com.alexgaas.testcontainersblueprints;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication(exclude = {RepositoryRestMvcAutoConfiguration.class})
public class BlueprintApplication {
  public static void main(String[] args) {
    SpringApplication.run(BlueprintApplication.class, args);
  }
}
