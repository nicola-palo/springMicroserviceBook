package com.azienda.esercizioMicroserviceUtenti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.azienda.esercizioMicroserviceUtenti.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer>{
	
	@Query("SELECT u FROM Utente u WHERE u.username = :username")
	public Utente findByUsername(@Param("username") String username);


}
