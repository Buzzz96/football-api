package com.matawan.footballapi.service;

import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.entity.Equipe;
import com.matawan.footballapi.entity.Joueur;
import com.matawan.footballapi.mapper.EquipeMapper;
import com.matawan.footballapi.repository.EquipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EquipeServiceTest {

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private EquipeMapper equipeMapper;

    @InjectMocks
    private EquipeService equipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEquipe_assignsEquipeToJoueurs() {
        Equipe equipe = new Equipe();
        Joueur joueur = new Joueur();
        equipe.setJoueurs(List.of(joueur));

        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe saved = equipeService.addEquipe(equipe);

        assertThat(saved.getJoueurs().get(0).getEquipe()).isEqualTo(equipe);
        verify(equipeRepository).save(equipe);
    }

    @Test
    void testGetAllEquipes_returnsPagedResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Equipe> page = new PageImpl<>(Collections.emptyList());
        when(equipeRepository.findAll(pageable)).thenReturn(page);

        Page<Equipe> result = equipeService.getAllEquipes(pageable);

        assertThat(result).isEqualTo(page);
        verify(equipeRepository).findAll(pageable);
    }

    @Test
    void testSearchEquipes_withNameAndBudget_callsCorrectRepoMethod() {
        String name = "Nice";
        double min = 1.0;
        double max = 5.0;
        Equipe equipe = new Equipe();
        EquipeDto dto = new EquipeDto();
        when(equipeRepository.findByNameContainingIgnoreCaseAndBudgetBetween(name, min, max))
                .thenReturn(List.of(equipe));
        when(equipeMapper.toDto(equipe)).thenReturn(dto);

        List<EquipeDto> result = equipeService.searchEquipes(name, min, max);

        assertThat(result).containsExactly(dto);
    }
}
