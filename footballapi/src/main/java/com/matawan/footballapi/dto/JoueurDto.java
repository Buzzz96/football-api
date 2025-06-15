package com.matawan.footballapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO pour la création d’un joueur.
 * Contient uniquement les champs nécessaires à la saisie.
 */
@Data
public class JoueurDto {
    @NotBlank(message = "Le nom du joueur est obligatoire")
    public String name;

    @NotBlank(message = "La position est obligatoire")
    public String position;
}
