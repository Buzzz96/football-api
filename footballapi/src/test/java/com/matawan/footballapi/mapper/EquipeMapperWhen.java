package com.matawan.footballapi.mapper;

import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.entity.Equipe;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JGivenStage
public class EquipeMapperWhen extends Stage<EquipeMapperWhen> {

    @ExpectedScenarioState
    Equipe equipe;

    @ProvidedScenarioState
    EquipeDto equipeDto;

    private final EquipeMapper equipeMapper = Mappers.getMapper(EquipeMapper.class);

    @As("on convertit l'entité en DTO")
    public EquipeMapperWhen leMappingEstEffectue() {
        equipeDto = equipeMapper.toDto(equipe);
        return self();
    }

    @As("on effectue le mapping de l'équipe en DTO")
    public EquipeMapperWhen mappingEquipe() {
        equipeDto = equipeMapper.toDto(equipe);
        return self();
    }


}
