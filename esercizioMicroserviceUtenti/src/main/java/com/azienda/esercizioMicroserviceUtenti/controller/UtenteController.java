package com.azienda.esercizioMicroserviceUtenti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azienda.esercizioMicroserviceUtenti.model.Utente;
import com.azienda.esercizioMicroserviceUtenti.service.UtenteService;

@RestController
@RequestMapping(path = "/rest/utente", produces = "application/json")
@CrossOrigin(origins = "*")
public class UtenteController {
	
	@Autowired
	UtenteService us;

	
	@PostMapping("/login")
    public ResponseEntity<Utente> login(
            @RequestHeader(value = "username", required = false) String username,
            @RequestHeader(value = "password", required = false) String password) {
        try {
            // 400 
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Utente utente = us.findByUsername(username);

            // 401 
            if (utente == null || !password.equals(utente.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 200 
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            // 500 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@PostMapping("/userAdmin")
    public ResponseEntity<Utente> userAdmin(
            @RequestHeader(value = "username", required = false) String username,
            @RequestHeader(value = "password", required = false) String password) {
        try {
            // 400 
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Utente utente = us.findByUsername(username);

            // 401 
            if (utente == null || !password.equals(utente.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 403
            if (!"admin".equalsIgnoreCase(utente.getRuolo())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            // 200 
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            // 500 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	

}
