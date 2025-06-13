package com.matawan.footballapi.mapper;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

public class EquipeMapperTest extends ScenarioTest<
        EquipeMapperGiven,
        EquipeMapperWhen,
        EquipeMapperThen> {

    @Test
    @As("Le mapping d'une équipe en DTO doit produire un résultat correct")
    void testMappingEquipeVersDto() {
        given().uneEquipeExiste();
        when().leMappingEstEffectue();
        then().leResultatEstValide();
    }
    @Test
    @As("Le mapping échoue si les données attendues sont incorrectes")
    void testMappingAvecAssertionsFausses() {
        given().uneEquipeExiste();
        when().mappingEquipe();
        then().resultatInvalide();
    }



}
