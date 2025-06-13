package com.matawan.footballapi.mapper;

import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.dto.JoueurDto;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.assertj.core.api.Assertions;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Component
@JGivenStage
public class EquipeMapperThen extends Stage<EquipeMapperThen> {

    @ExpectedScenarioState
    EquipeDto equipeDto;

    @As("le DTO est correctement rempli")
    public EquipeMapperThen leResultatEstValide() {
        Assertions.assertThat(equipeDto).isNotNull();
        Assertions.assertThat(equipeDto.getName()).isEqualTo("Paris Saint-Germain");
        Assertions.assertThat(equipeDto.getAcronym()).isEqualTo("PSG");
        Assertions.assertThat(equipeDto.getBudget()).isEqualTo(5000000.0);
        Assertions.assertThat(equipeDto.getJoueurs()).hasSize(1);

        JoueurDto joueurDto = equipeDto.getJoueurs().get(0);
        Assertions.assertThat(joueurDto.getName()).isEqualTo("Mbappé");
        Assertions.assertThat(joueurDto.getPosition()).isEqualTo("Attaquant");

        return self();
    }

    @As("le DTO contient des informations erronées")
    public EquipeMapperThen resultatInvalide() {
        assertThat(equipeDto).isNotNull();
        assertThat(equipeDto.getName()).isEqualTo("Real Madrid");
        assertThat(equipeDto.getAcronym()).isEqualTo("RMA");
        assertThat(equipeDto.getBudget()).isEqualTo(10000000.0);

        JoueurDto joueurDto = equipeDto.getJoueurs().get(0);
        assertThat(joueurDto.getName()).isEqualTo("Benzema");
        assertThat(joueurDto.getPosition()).isEqualTo("Avant-centre");

//        System.out.println("On prétend que l’équipe s'appelle Real Madrid (mais on ne vérifie pas)");
        return self();
    }


}
