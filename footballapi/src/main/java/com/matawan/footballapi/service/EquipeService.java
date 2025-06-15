package com.matawan.footballapi.service;

import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.entity.Equipe;
import com.matawan.footballapi.entity.Joueur;
import com.matawan.footballapi.mapper.EquipeMapper;
import com.matawan.footballapi.repository.EquipeRepository;
import com.matawan.footballapi.repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service métier pour la gestion des équipes de football.
 * Fournit les opérations principales : ajout, recherche, suppression et pagination.
 */
@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;
    @Autowired
    private EquipeMapper equipeMapper;

    /**
     * Ajoute une nouvelle équipe en liant les joueurs à celle-ci.
     *
     * @param equipe l'entité à enregistrer
     * @return l’équipe enregistrée en base
     */
    public Equipe addEquipe(Equipe equipe) {
        if (equipe.getJoueurs() != null) {
            equipe.getJoueurs().forEach(joueur -> joueur.setEquipe(equipe));
        }
        logger.info("Ajout de l’équipe : {} ({}) avec {} joueur(s)",
                equipe.getName(),
                equipe.getAcronym(),
                equipe.getJoueurs() != null ? equipe.getJoueurs().size() : 0);
        return equipeRepository.save(equipe);
    }

    /**
     * Recherche d’équipes selon le nom et/ou un intervalle de budget.
     *
     * @param name       nom partiel de l’équipe
     * @param minBudget  budget minimum
     * @param maxBudget  budget maximum
     * @return liste des équipes trouvées sous forme de DTOs
     */
    public List<EquipeDto> searchEquipes(String name, Double minBudget, Double maxBudget) {
        List<Equipe> resultats;

        if (name != null && minBudget != null && maxBudget != null) {
            resultats = equipeRepository.findByNameContainingIgnoreCaseAndBudgetBetween(name, minBudget, maxBudget);
        } else if (name != null) {
            resultats = equipeRepository.findByNameContainingIgnoreCase(name);
        } else if (minBudget != null && maxBudget != null) {
            resultats = equipeRepository.findByBudgetBetween(minBudget, maxBudget);
        } else {
            resultats = equipeRepository.findAll();
        }

        return resultats.stream().map(equipeMapper::toDto).toList();
    }

    /**
     * Retourne toutes les équipes paginées.
     *
     * @param pageable configuration de pagination (page, taille, tri)
     * @return page d’équipes
     */
    public Page<Equipe> getAllEquipes(Pageable pageable) {
        Page<Equipe> page = equipeRepository.findAll(pageable);
        logger.info("Équipes retournées : page {}, taille {}, total éléments : {}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                page.getTotalElements());
        return page;
    }

    private static final Logger logger = LoggerFactory.getLogger(EquipeService.class);

    /**
     * Supprime une équipe par son identifiant.
     *
     * @param id identifiant de l’équipe à supprimer
     */
    public void deleteEquipe(Long id) {
        Equipe equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée avec id: " + id));
        equipeRepository.delete(equipe);
    }


}
