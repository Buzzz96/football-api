package com.matawan.footballapi.mapper;

import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.dto.JoueurDto;
import com.matawan.footballapi.entity.Equipe;
import com.matawan.footballapi.entity.Joueur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * Mapper MapStruct pour convertir entre les entités et les DTOs liés aux équipes et joueurs.
 */
@Mapper(componentModel = "spring")
public interface EquipeMapper {
    /**
     * Instance statique générée automatiquement par MapStruct (utilisée uniquement si non injectée).
     */
    EquipeMapper INSTANCE = Mappers.getMapper(EquipeMapper.class);
    /**
     * Convertit un DTO Equipe en entité (hors champ ID).
     */
    @Mapping(target = "id", ignore = true)
    Equipe toEntity(EquipeDto dto);

    /**
     * Convertit une entité Equipe en DTO.
     */
    EquipeDto toDto(Equipe entity);

    /**
     * Convertit un DTO Joueur en entité (sans lier à une équipe et sans ID).
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    Joueur toEntity(JoueurDto dto);

    /**
     * Convertit une liste de DTOs Joueur en liste d’entités.
     */
    List<Joueur> toJoueurs(List<JoueurDto> dtos);

    /**
     * Convertit une liste d’entités Joueur en liste de DTOs.
     */
    List<JoueurDto> toJoueurDtos(List<Joueur> entities);
}
