package com.matawan.footballapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * Entité représentant une équipe de football.
 * Chaque équipe possède un nom, un acronyme, un budget et une liste de joueurs associés.
 */

@Entity
@Getter
@Setter
public class Equipe {
    /**
     * Identifiant unique de l'équipe (généré automatiquement).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nom complet de l'équipe (ex. : OGC Nice).
     */
    private String name;

    /**
     * Acronyme de l'équipe (ex. : OGCN).
     */
    private String acronym;

    /**
     * Budget annuel de l'équipe en millions d'euros.
     */
    private Double budget;

    /**
     * Liste des joueurs appartenant à cette équipe.
     * La relation est en cascade : la suppression d’une équipe entraîne celle de ses joueurs.
     */
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)

    @JsonManagedReference
    private List<Joueur> joueurs = new ArrayList<>();

}
