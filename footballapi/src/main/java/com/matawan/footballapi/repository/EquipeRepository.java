package com.matawan.footballapi.repository;

import com.matawan.footballapi.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Interface de repository pour l'entité Equipe.
 * Fournit des méthodes de recherche personnalisées en plus des opérations CRUD standard héritées de JpaRepository.
 */
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    /**
     * Recherche les équipes par nom (recherche partielle, insensible à la casse).
     * Recherche les équipes dont le budget est compris entre deux valeurs.
     * Recherche les équipes par nom et budget (filtres combinés).
     */
    List<Equipe> findByNameContainingIgnoreCase(String name);
    List<Equipe> findByBudgetBetween(Double minBudget, Double maxBudget);
    List<Equipe> findByNameContainingIgnoreCaseAndBudgetBetween(String name, Double minBudget, Double maxBudget);

}
