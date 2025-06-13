package com.matawan.footballapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.dto.JoueurDto;
import com.matawan.footballapi.entity.Equipe;
import com.matawan.footballapi.entity.Joueur;
import com.matawan.footballapi.repository.EquipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EquipeRepository equipeRepository;

    @BeforeEach
    void cleanDatabase() {
        equipeRepository.deleteAll();
    }


    @Test
    void testAjoutEquipeAvecJoueurs_valide() throws Exception {
        // Construction du DTO
        JoueurDto joueur1 = new JoueurDto();
        joueur1.setName("Thuram");
        joueur1.setPosition("Milieu");

        JoueurDto joueur2 = new JoueurDto();
        joueur2.setName("Laborde");
        joueur2.setPosition("Attaquant");

        EquipeDto equipeDto = new EquipeDto();
        equipeDto.setName("OGC Nice");
        equipeDto.setAcronym("OGCN");
        equipeDto.setBudget(4500000.0);
        equipeDto.setJoueurs(List.of(joueur1, joueur2));

        String json = objectMapper.writeValueAsString(equipeDto);

        mockMvc.perform(post("/equipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("OGC Nice"))
                .andExpect(jsonPath("$.joueurs.length()").value(2));
    }

    @Test
    void testGetEquipes_returnsListAndStatus200() throws Exception {
        Joueur joueur1 = new Joueur();
        joueur1.setName("Jean Dupont");
        joueur1.setPosition("Attaquant");

        Joueur joueur2 = new Joueur();
        joueur2.setName("Paul Martin");
        joueur2.setPosition("Défenseur");

        Equipe equipe = new Equipe();
        equipe.setName("Olympique Lyonnais");
        equipe.setAcronym("OL");
        equipe.setBudget(8000000.0);
        equipe.setJoueurs(List.of(joueur1, joueur2));
        joueur1.setEquipe(equipe);
        joueur2.setEquipe(equipe);

        equipeRepository.save(equipe);

        // Requête GET
        mockMvc.perform(get("/equipes"))
                .andExpect(jsonPath("$.content[0].name").value("Olympique Lyonnais"))
                .andExpect(jsonPath("$.content[0].acronym").value("OL"))
                .andExpect(jsonPath("$.content[0].budget").value(8000000.0))
                .andExpect(jsonPath("$.content[0].joueurs.length()").value(2))
                .andExpect(jsonPath("$.content[0].joueurs[0].name").value("Jean Dupont"));
    }

}
