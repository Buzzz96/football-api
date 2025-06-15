package com.matawan.footballapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
/**
 * DTO pour la création ou la mise à jour d'une équipe.
 * Utilisé pour valider les données côté client.
 */
@Data
public class EquipeDto {

    @NotBlank(message = "Le nom est obligatoire")
    public String name;

    @NotBlank(message = "L'acronyme est obligatoire")
    public String acronym;

    @NotNull(message = "Le budget est obligatoire")
    @Positive(message = "Le budget doit être supérieur à 0")
    public Double budget;

    /**
     * Liste optionnelle de joueurs associés à l'équipe.
     */
    public List<JoueurDto> joueurs;
}
