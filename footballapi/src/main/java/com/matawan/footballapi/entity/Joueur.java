package com.matawan.footballapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

/**
 * Entité représentant un joueur de football.
 * Chaque joueur a un nom, une position et appartient à une équipe.
 */

@Entity
@Getter
@Setter
public class Joueur {

    /**
     * Identifiant unique du joueur (généré automatiquement).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom complet du joueur.
     */
    private String name;

    /**
     * Position du joueur sur le terrain (ex. : Attaquant, Défenseur, etc.).
     */
    private String position;

    /**
     * L'équipe à laquelle le joueur est rattaché.
            * Relation ManyToOne : plusieurs joueurs peuvent appartenir à une même équipe.
     */
    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonBackReference
    private Equipe equipe;


}


