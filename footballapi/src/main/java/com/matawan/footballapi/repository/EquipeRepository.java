package com.matawan.footballapi.repository;

import com.matawan.footballapi.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    List<Equipe> findByNameContainingIgnoreCase(String name);
    List<Equipe> findByBudgetBetween(Double minBudget, Double maxBudget);
    List<Equipe> findByNameContainingIgnoreCaseAndBudgetBetween(String name, Double minBudget, Double maxBudget);


}
