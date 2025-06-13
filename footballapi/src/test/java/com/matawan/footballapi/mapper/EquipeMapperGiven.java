package com.matawan.footballapi.mapper;

import com.matawan.footballapi.entity.Equipe;
import com.matawan.footballapi.entity.Joueur;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@JGivenStage
public class EquipeMapperGiven extends Stage<EquipeMapperGiven> {

    @ProvidedScenarioState
    Equipe equipe;

    @As("une équipe avec nom, budget, acronyme et un joueur existe")
    public EquipeMapperGiven uneEquipeExiste() {
        Joueur joueur = new Joueur();
        joueur.setId(1L);
        joueur.setName("Mbappé");
        joueur.setPosition("Attaquant");

        equipe = new Equipe();
        equipe.setId(10L);
        equipe.setName("Paris Saint-Germain");
        equipe.setAcronym("PSG");
        equipe.setBudget(5000000.0);
        equipe.setJoueurs(List.of(joueur));

        return self();
    }



}
