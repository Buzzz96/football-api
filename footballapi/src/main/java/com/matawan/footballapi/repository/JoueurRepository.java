package com.matawan.footballapi.repository;

import com.matawan.footballapi.entity.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repository pour l'entité Joueur.
 * Hérite des opérations CRUD de JpaRepository.
 * Peut être étendue avec des méthodes de recherche personnalisées si besoin.
 */
@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {
    // Méthodes personnalisées de recherche à ajouter ici si nécessaire
}
