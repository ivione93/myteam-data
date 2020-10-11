package com.ivione93.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ivione93.dto.AtletaDto;
import com.ivione93.entity.Atleta;

@Mapper(componentModel = "cdi")
public interface AtletaEntityToDtoMapper {

	AtletaDto toDto(Atleta e);
	Atleta toEntity(AtletaDto d);
	List<AtletaDto> toDto(List<Atleta> eList);
	List<Atleta> toEntity(List<AtletaDto> dList);
	
}
