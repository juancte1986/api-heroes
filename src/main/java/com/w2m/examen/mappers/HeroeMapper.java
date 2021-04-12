package com.w2m.examen.mappers;

import org.mapstruct.Mapper;

import com.w2m.examen.dto.HeroeDTO;
import com.w2m.examen.models.Heroe;

@Mapper(componentModel = "spring", uses = {})
public interface HeroeMapper extends EntityMapper<HeroeDTO, Heroe>{
	
}
