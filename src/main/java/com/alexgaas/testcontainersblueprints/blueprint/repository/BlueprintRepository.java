package com.alexgaas.testcontainersblueprints.blueprint.repository;


import com.alexgaas.testcontainersblueprints.blueprint.model.Blueprint;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlueprintRepository extends JpaRepository<Blueprint, String> {
  Optional<Blueprint> findById(String id);
}
