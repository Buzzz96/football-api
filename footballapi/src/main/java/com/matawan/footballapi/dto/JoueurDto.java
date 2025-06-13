package com.matawan.footballapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class JoueurDto {
    @NotBlank(message = "Le nom du joueur est obligatoire")
    public String name;

    @NotBlank(message = "La position est obligatoire")
    public String position;
}
