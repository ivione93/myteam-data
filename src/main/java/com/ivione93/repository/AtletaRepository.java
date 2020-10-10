package com.ivione93.repository;

import java.util.List;

import javax.inject.Singleton;

import com.ivione93.entity.Atleta;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class AtletaRepository implements PanacheRepositoryBase<Atleta, String> {
	
	public List<Atleta> getAllAthletes() {
		return findAll().list();
	}
	
	public Atleta getAthleteByLicencia(String licencia) {
		return find("licencia = ?1", licencia).firstResult();
	}

}
