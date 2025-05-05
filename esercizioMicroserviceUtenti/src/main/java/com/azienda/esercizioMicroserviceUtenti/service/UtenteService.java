package com.azienda.esercizioMicroserviceUtenti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azienda.esercizioMicroserviceUtenti.model.Ruolo;
import com.azienda.esercizioMicroserviceUtenti.model.Utente;
import com.azienda.esercizioMicroserviceUtenti.repository.RuoloRepository;
import com.azienda.esercizioMicroserviceUtenti.repository.UtenteRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class UtenteService {
	
	@Autowired
	RuoloRepository ruoloRepository;
	@Autowired
	UtenteRepository utenteRepository;
	
	
	public void createAdminBase () {
		Ruolo admin = new Ruolo("admin");
		ruoloRepository.save(admin);
		Utente utenteAdmin = new Utente("admin", "admin", admin);
		utenteRepository.save(utenteAdmin);
	}
	
	public void createUserBase () {
		Ruolo user = new Ruolo("user");
		ruoloRepository.save(user);
		Utente utenteUser = new Utente("user", "user", user);
		utenteRepository.save(utenteUser);
	}
	
	public Utente findByUsername (String username) {
		return utenteRepository.findByUsername(username);
	}
	
	

	
}
