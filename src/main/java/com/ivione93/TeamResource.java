package com.ivione93;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.ivione93.dto.AtletaDto;
import com.ivione93.dto.ResultadoDto;
import com.ivione93.entity.Atleta;
import com.ivione93.entity.Resultado;
import com.ivione93.service.TeamService;

@RequestScoped
@Path("/myteam-data")
public class TeamResource {
	
	private static final Logger log = Logger.getLogger(TeamResource.class);
	
	@Inject
	TeamService teamService;
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/atletas")
    public Response createAthlete(Atleta payload) {
    	log.info("Call to createAthlete: " + payload);
    	
    	try {
    		return teamService.createAthlete(payload);
    	} catch (Exception e) {
			log.info("Error in athlete creation");
			return Response.serverError().build();
		}
    }
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/atletas/{licencia}/result")
    public Response addResult(Resultado payload) {
		log.info("Call to addResult: " + payload);
    	
    	try {
    		return teamService.addResult(payload);
    	} catch (Exception e) {
			log.info("Error in result addition");
			return Response.serverError().build();
		}
	}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/atletas")
    public Response getAllAthletes() {
    	log.info("Call to getAllAthletes method");
    	
    	List<AtletaDto> atletas = teamService.getAllAthletes();
    	
    	return Response.ok(atletas).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/atletas/{licencia}")
    public Response getAthleteByLicencia(@PathParam("licencia") String licencia) {
    	log.infof("Call to getAthleteByLicencia with parameters: { licencia: %s }", licencia);
    	
    	AtletaDto atleta = teamService.getAthleteByLicencia(licencia);
    	
    	return Response.ok(atleta).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/atletas/{licencia}/results")
    public Response getAthleteResults(@PathParam("licencia") String licencia) {
    	log.infof("Call to getAthleteResults with parameters: { licencia: %s }", licencia);
    	
    	List<ResultadoDto> resultados = teamService.getAthleteResults(licencia);
    	if(resultados == null || resultados.size() < 1) {
    		return Response.status(Status.NOT_FOUND).build();
    	}
    	
    	return Response.ok(resultados).build();
    }
}