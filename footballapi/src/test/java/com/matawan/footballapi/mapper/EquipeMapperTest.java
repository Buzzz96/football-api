package com.matawan.footballapi.mapper;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

/**
 * Tests JGiven pour valider le mapping entre l'entité Equipe et son DTO.
 * Utilise une structure Given–When–Then lisible et claire.
 */
public class EquipeMapperTest extends ScenarioTest<
        EquipeMapperGiven,
        EquipeMapperWhen,
        EquipeMapperThen> {

    /**
     * Vérifie que le mapping d'une entité valide vers un DTO est correct.
     */
    @Test
    @As("Le mapping d'une équipe en DTO doit produire un résultat correct")
    void testMappingEquipeVersDto() {
        given().uneEquipeExiste();
        when().leMappingEstEffectue();
        then().leResultatEstValide();
    }

    /**
     * Test illustratif volontairement faux : les assertions sont volontairement erronées.
     * Sert à tester le comportement en cas de mismatch entre entité attendue et reçue.
     */
    @Test
    @As("Le mapping échoue si les données attendues sont incorrectes")
    void testMappingAvecAssertionsFausses() {
        given().uneEquipeExiste();
        when().mappingEquipe();
        then().resultatInvalide();
    }



}
