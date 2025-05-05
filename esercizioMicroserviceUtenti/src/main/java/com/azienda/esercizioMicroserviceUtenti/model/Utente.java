package com.azienda.esercizioMicroserviceUtenti.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	
	@ManyToOne
	Ruolo ruolo;
	
	
	public Utente() {
		super();
	}
	
	


	public Utente(String username, String password, Ruolo ruolo) {
		super();
		this.username = username;
		this.password = password;
		this.ruolo = ruolo;
	}




	public Utente(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getRuolo() {
		return ruolo.getNomeRuolo();
	}




	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}




	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "Utente [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	

	
	
}
