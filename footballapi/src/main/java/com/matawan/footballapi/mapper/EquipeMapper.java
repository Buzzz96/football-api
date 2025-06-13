package com.matawan.footballapi.mapper;

import com.matawan.footballapi.dto.EquipeDto;
import com.matawan.footballapi.dto.JoueurDto;
import com.matawan.footballapi.entity.Equipe;
import com.matawan.footballapi.entity.Joueur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipeMapper {

    EquipeMapper INSTANCE = Mappers.getMapper(EquipeMapper.class);

    @Mapping(target = "id", ignore = true)
    Equipe toEntity(EquipeDto dto);

    EquipeDto toDto(Equipe entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "equipe", ignore = true)
    Joueur toEntity(JoueurDto dto);



    List<Joueur> toJoueurs(List<JoueurDto> dtos);

    List<JoueurDto> toJoueurDtos(List<Joueur> entities);
}
