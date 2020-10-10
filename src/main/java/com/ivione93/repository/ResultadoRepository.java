package com.ivione93.repository;

import java.util.List;

import javax.inject.Singleton;

import com.ivione93.entity.Resultado;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class ResultadoRepository implements PanacheRepositoryBase<Resultado, String> {
	
	public List<Resultado> getResultadosByLicencia(String licencia) {
		List<Resultado> res = find("licencia = ?1", licencia).list();
		return res;
	}

}
