package com.matawan.footballapi.controller;

import com.matawan.footballapi.entity.Equipe;
import com.matawan.footballapi.repository.EquipeRepository;
import com.matawan.footballapi.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.mapper.EquipeMapper;
import java.util.List;

/**
 * Contrôleur REST pour gérer les équipes de football.
 * Fournit des endpoints pour consulter, ajouter, rechercher et supprimer des équipes.
 */
@RestController
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;
    @Autowired
    private EquipeMapper equipeMapper;

    @Autowired
    private EquipeRepository equipeRepository;

    public EquipeController(EquipeService equipeService, EquipeMapper equipeMapper) {
        this.equipeService = equipeService;
        this.equipeMapper = equipeMapper;
    }

    /**
     * Récupère toutes les équipes avec pagination et tri.
     *
     * @param page numéro de page (défaut : 0)
     * @param size taille de la page (défaut : 5)
     * @param sort tableau contenant le champ et la direction du tri (ex : ["name","asc"])
     * @return page d’équipes
     */
    @GetMapping
    public Page<Equipe> getEquipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return equipeService.getAllEquipes(pageable);
    }


    /**
     * Recherche les équipes selon le nom et/ou l’intervalle de budget.
     *
     * @param name nom partiel (optionnel)
     * @param minBudget budget min (optionnel)
     * @param maxBudget budget max (optionnel)
     * @return liste filtrée des équipes
     */
    @GetMapping("/search")
    public List<EquipeDto> searchEquipe(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) Double maxBudget) {
        return equipeService.searchEquipes(name, minBudget, maxBudget);
    }


    /**
     * Ajoute une nouvelle équipe (avec ou sans joueurs).
     *
     * @param dto données de l’équipe à créer
     * @return l’équipe créée (DTO) avec statut 201
     */
    @PostMapping
    public ResponseEntity<EquipeDto> addEquipe(@Valid @RequestBody EquipeDto dto) {
        Equipe equipe = equipeMapper.toEntity(dto);
        Equipe saved = equipeService.addEquipe(equipe);
        EquipeDto savedDto = equipeMapper.toDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    /**
     * Supprime une équipe par son identifiant.
     *
     * @param id identifiant de l’équipe
     * @return réponse vide avec statut 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipe(@PathVariable Long id) {
        equipeService.deleteEquipe(id);
        return ResponseEntity.noContent().build();
    }


}
