package com.matawan.footballapi.mapper;

import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.entity.Equipe;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Étape When pour déclencher le mapping d'une entité Equipe vers son DTO.
 */
@Component
public class EquipeMapperWhen extends Stage<EquipeMapperWhen> {

    @ExpectedScenarioState
    Equipe equipe;

    @ProvidedScenarioState
    EquipeDto equipeDto;

    private final EquipeMapper equipeMapper = Mappers.getMapper(EquipeMapper.class);

    /**
     * Effectue le mapping de l'entité vers le DTO (version 1).
     */
    @As("on convertit l'entité en DTO")
    public EquipeMapperWhen leMappingEstEffectue() {
        equipeDto = equipeMapper.toDto(equipe);
        return self();
    }

    /**
     * Effectue le mapping de l'entité vers le DTO (version alternative).
     */
    @As("on effectue le mapping de l'équipe en DTO")
    public EquipeMapperWhen mappingEquipe() {
        equipeDto = equipeMapper.toDto(equipe);
        return self();
    }


}
