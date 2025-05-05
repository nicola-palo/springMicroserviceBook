package com.azienda.esercizioMicroserviceLibri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String titoloLibro;
	private String autoreLibro;
	private Float prezzoLibro;
	
	
	public Libro() {
		super();
	}
	public Libro(String titoloLibro, String autoreLibro, Float prezzoLibro) {
		super();
		this.titoloLibro = titoloLibro;
		this.autoreLibro = autoreLibro;
		this.prezzoLibro = prezzoLibro;
	}
	public String getTitoloLibro() {
		return titoloLibro;
	}
	public void setTitoloLibro(String titoloLibro) {
		this.titoloLibro = titoloLibro;
	}
	public String getAutoreLibro() {
		return autoreLibro;
	}
	public void setAutoreLibro(String autoreLibro) {
		this.autoreLibro = autoreLibro;
	}
	public Float getPrezzoLibro() {
		return prezzoLibro;
	}
	public void setPrezzoLibro(Float prezzoLibro) {
		this.prezzoLibro = prezzoLibro;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Libro [id=" + id + ", titoloLibro=" + titoloLibro + ", autoreLibro=" + autoreLibro + ", prezzoLibro="
				+ prezzoLibro + "]";
	}
	
	
	
}
