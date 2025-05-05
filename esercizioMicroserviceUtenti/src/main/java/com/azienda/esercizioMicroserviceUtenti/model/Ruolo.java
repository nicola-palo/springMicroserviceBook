package com.azienda.esercizioMicroserviceUtenti.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Ruolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeRuolo;
	
	@OneToMany(mappedBy = "ruolo")
	List<Utente> utenti;
	
	public Ruolo() {
		super();
	}
	
	
	
	public Ruolo(String nomeRuolo) {
		super();
		this.nomeRuolo = nomeRuolo;
	}



	public Ruolo(String nomeRuolo, List<Utente> utenti) {
		super();
		this.nomeRuolo = nomeRuolo;
		this.utenti = utenti;
	}

	public String getNomeRuolo() {
		return nomeRuolo;
	}
	public void setNomeRuolo(String nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
	}
	
	@Override
	public String toString() {
		return "Ruolo [id=" + id + ", nomeRuolo=" + nomeRuolo + "]";
	}
	
	
	
	
}
