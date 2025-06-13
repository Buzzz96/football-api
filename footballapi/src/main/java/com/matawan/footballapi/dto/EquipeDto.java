package com.matawan.footballapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EquipeDto {

    @NotBlank(message = "Le nom est obligatoire")
    public String name;

    @NotBlank(message = "L'acronyme est obligatoire")
    public String acronym;

    @NotNull(message = "Le budget est obligatoire")
    @Positive(message = "Le budget doit être supérieur à 0")
    public Double budget;

    public List<JoueurDto> joueurs;
}
