package com.ivione93.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ivione93.dto.ResultadoDto;
import com.ivione93.entity.Resultado;

@Mapper(componentModel = "cdi")
public interface ResultadoEntityToDtoMapper {

	ResultadoDto toDto(Resultado e);
	Resultado toEntity(ResultadoDto d);
	List<ResultadoDto> toDto(List<Resultado> eList);
	List<Resultado> toEntity(List<ResultadoDto> dList);
	
}
