package com.ivione93.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	Boolean checkAthlete = true;
	Integer lineNumber = 1;
	Atleta atleta;
	
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

	public AtletaDto getAthleteByLicencia(String licencia) {
		log.infof("Call service getAthleteByLicencia with parameters: { licencia: %s }", licencia);
	
		Atleta atleta = atletaRepository.getAthleteByLicencia(licencia);
		AtletaDto result = atletaEntityToDtoMapper.toDto(atleta);
		
		return result;
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
	
	public void startProcessCSV() {
		log.info("Call service startProcessCSV");
		
		BufferedReader reader = null;
		String csvSplit = ";";
		
		try {
			reader = new BufferedReader(new FileReader("../csv/atletas.csv"));
			String line = reader.readLine();
			while (line != null) {
				List<String> data = Arrays.asList(line.split(csvSplit));

				createAthleteCsv(data);
				
				atleta = new Atleta();
				line = reader.readLine();
			}
		} catch (IOException e) {
			log.error("Error processing file");
		} finally {
		    if (reader != null) {
		        try {
		        	reader.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	
	@Transactional
	public void createAthleteCsv(List<String> data) {
		atleta = new Atleta();
		
		if(!data.get(0).isEmpty()) {
			String licencia = data.get(0);
			atleta.setLicencia(licencia);
			
			if(!data.get(1).isEmpty()) {
				String nombre = data.get(1);
				atleta.setNombre(nombre);
			}
			if(!data.get(2).isEmpty()) {
				String apellidos = data.get(2);
				atleta.setApellidos(apellidos);
			}
			if(!data.get(3).isEmpty()) {
				String ciudad = data.get(3);
				atleta.setCiudad(ciudad);
			}
			if(!data.get(4).isEmpty()) {
				String sexo = data.get(4);
				atleta.setSexo(sexo);
			}
			if(!data.get(5).isEmpty()) {
				String categoria = data.get(5);
				atleta.setCategoria(categoria);
			}
			if(atleta != null) {
				atletaRepository.persist(atleta);
				log.info("Alta de: " + atleta.toString());
			}
		} else {
			log.error("Licencia vacía");
		}
	}
}
