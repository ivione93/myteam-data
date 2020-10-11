package com.ivione93.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.ivione93.dto.AtletaDto;
import com.ivione93.dto.ResultadoDto;
import com.ivione93.entity.Atleta;
import com.ivione93.entity.Resultado;
import com.ivione93.mapper.AtletaEntityToDtoMapper;
import com.ivione93.mapper.ResultadoEntityToDtoMapper;
import com.ivione93.repository.AtletaRepository;
import com.ivione93.repository.ResultadoRepository;

@RequestScoped
public class TeamService {
	
	private static final Logger log = Logger.getLogger(TeamService.class);
	
	@Inject
	AtletaRepository atletaRepository;
	
	@Inject
	ResultadoRepository resultadoRepository;
	
	@Inject
	AtletaEntityToDtoMapper atletaEntityToDtoMapper;
	
	@Inject
	ResultadoEntityToDtoMapper resultadoEntityToDtoMapper;
	
	@Transactional
	public Response createAthlete(Atleta payload) {
		log.info("Call service createAthlete");
		
		Atleta atleta = atletaRepository.getAthleteByLicencia(payload.getLicencia());
		if(atleta == null) {
			atletaRepository.persist(payload);
		} else {
			log.infof("Already exists an athlete with this licencia: %s", payload.getLicencia());
			return Response.status(Response.Status.CONFLICT).build();
		}
		
		return Response.status(Response.Status.CREATED).build();
	}
	
	@Transactional
	public Response addResult(Resultado payload) {
		log.info("Call service addResult");
		
		Atleta atleta = atletaRepository.getAthleteByLicencia(payload.getLicencia());
		if(atleta == null) {
			log.infof("Athlete with licence: %s not exist", payload.getLicencia());
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			resultadoRepository.persist(payload);
		}
		
		return Response.status(Response.Status.CREATED).build();
	}

	public List<AtletaDto> getAllAthletes() {
		log.info("Call service getAllAthletes");
		
		List<AtletaDto> resultList = new ArrayList<AtletaDto>();
		List<Atleta> listAtletas = atletaRepository.getAllAthletes();
		
		resultList = atletaEntityToDtoMapper.toDto(listAtletas);
		return resultList;
	}

	public Atleta getAthleteByLicencia(String licencia) {
		log.infof("Call service getAthleteByLicencia with parameters: { licencia: %s }", licencia);
		
		return atletaRepository.getAthleteByLicencia(licencia);
	}
	
	public List<ResultadoDto> getAthleteResults(String licencia) throws WebApplicationException {
		log.infof("Call service getAthleteResults with parameters: { licencia: %s }", licencia);
		List<ResultadoDto> resultList = new ArrayList<ResultadoDto>();
		List<Resultado> resultados = null;
		Atleta atleta = atletaRepository.getAthleteByLicencia(licencia);
		if(atleta == null ) {
			log.infof("Not exists an athlete with licencia: %s", licencia);
		} else {
			resultados = resultadoRepository.getResultadosByLicencia(licencia);
			resultList = resultadoEntityToDtoMapper.toDto(resultados);
			
			if(resultados == null || resultados.size() < 1) {
				log.infof("The athlete with licencia { %s } has no results", licencia);
			}
		}
		
		return resultList;
	}
	
}
