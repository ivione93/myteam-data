package com.ivione93.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resultado")
public class Resultado {
	
	@Id
	@Column(name = "resultado_id")
	private String idResultado;
	
	@Column(name = "licencia")
	private String licencia;
	
	@Column(name = "prueba")
	private String prueba;
	
	@Column(name = "marca")
	private String marca;
	
	@Column(name = "fecha")
	private Instant fecha;
	
	@Column(name = "lugar")
	private String lugar;

	public Resultado() {}

	public String getIdResultado() {
		return idResultado;
	}

	public void setIdResultado(String idResultado) {
		this.idResultado = idResultado;
	}

	public String getLicencia() {
		return licencia;
	}

	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Instant getFecha() {
		return fecha;
	}

	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	@Override
	public String toString() {
		return "Resultado [licencia=" + licencia + ", prueba=" + prueba + ", marca=" + marca + ", fecha=" + fecha
				+ ", lugar=" + lugar + "]";
	}
	
}
