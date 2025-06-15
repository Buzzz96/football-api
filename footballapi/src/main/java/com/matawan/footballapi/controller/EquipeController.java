package com.matawan.footballapi.controller;

import com.matawan.footballapi.entity.Equipe;
import com.matawan.footballapi.repository.EquipeRepository;
import com.matawan.footballapi.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.mapper.EquipeMapper;
import java.util.List;


@RestController
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;
    @Autowired
    private EquipeMapper equipeMapper;

    @Autowired
    private EquipeRepository equipeRepository;

    public EquipeController(EquipeService equipeService, EquipeMapper equipeMapper) {
        this.equipeService = equipeService;
        this.equipeMapper = equipeMapper;
    }


    @GetMapping
    public Page<Equipe> getEquipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return equipeService.getAllEquipes(pageable);
    }

    @GetMapping("/search")
    public List<EquipeDto> searchEquipe(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) Double maxBudget) {
        return equipeService.searchEquipes(name, minBudget, maxBudget);
    }


    // POST /equipes
    @PostMapping
    public ResponseEntity<EquipeDto> addEquipe(@Valid @RequestBody EquipeDto dto) {
        Equipe equipe = equipeMapper.toEntity(dto);
        Equipe saved = equipeService.addEquipe(equipe);
        EquipeDto savedDto = equipeMapper.toDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // DELETE /equipes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipe(@PathVariable Long id) {
        equipeService.deleteEquipe(id);
        return ResponseEntity.noContent().build();
    }


}
