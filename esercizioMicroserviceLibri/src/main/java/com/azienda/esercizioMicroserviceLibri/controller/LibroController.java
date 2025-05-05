package com.azienda.esercizioMicroserviceLibri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


import com.azienda.esercizioMicroserviceLibri.model.Libro;
import com.azienda.esercizioMicroserviceLibri.service.LibroService;

@RestController
@RequestMapping(path = "/rest/libri", produces = "application/json")
@CrossOrigin(origins = "*")
public class LibroController {

	private static final String AUTH_SERVICE_URL = "http://localhost:8080/rest/utente/login";
	private static final String ADMIN_SERVICE_URL = "http://localhost:8080/rest/utente/userAdmin";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LibroService ls;

	private void isValidUser(String username, String password) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("username", username);
			headers.set("password", password);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(
					AUTH_SERVICE_URL,
					HttpMethod.POST,
					entity,
					String.class
					);

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new ResponseStatusException(response.getStatusCode(), "Autenticazione fallita");
			}

		} catch (HttpClientErrorException e) {

			throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante la validazione utente");
		}
	}


	private void isAdmin(String username, String password) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("username", username);
			headers.set("password", password);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(
					ADMIN_SERVICE_URL,
					HttpMethod.POST,
					entity,
					String.class
					);

			if (response.getStatusCode() != HttpStatus.OK) {
				throw new ResponseStatusException(response.getStatusCode(), "Autenticazione fallita");
			}

		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED ||
		            e.getStatusCode() == HttpStatus.FORBIDDEN) {
				throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
			}

			throw new ResponseStatusException(e.getStatusCode(), e.getResponseBodyAsString());
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante la validazione utente");
		}
	}





	@GetMapping("/getAll")
	public List<Libro> getLista(@RequestHeader("username") String username, @RequestHeader("password") String password) {
		isValidUser(username, password);  
		return ls.getAll();
	}


	@GetMapping("/getById/{id}")
	public ResponseEntity<Libro> getById(@RequestHeader("username") String username, @RequestHeader("password") String password, @PathVariable("id") Integer id) {
		try {
			isValidUser(username, password);

			Libro lib = ls.findById(id);

			if (lib != null) {
				return new ResponseEntity<Libro>(lib, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByTitolo/{titolo}")
	public ResponseEntity<Libro> getByTitolo(@RequestHeader("username") String username, @RequestHeader("password") String password, @PathVariable("titolo") String titolo) {
		try {
			isValidUser(username, password);
			Libro lib = ls.findByTitle(titolo);

			if (lib != null) {
				return new ResponseEntity<Libro>(lib, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByAutore/{autore}")
	public ResponseEntity<Libro> getAllByNome(@RequestHeader("username") String username, @RequestHeader("password") String password,@PathVariable("autore") String autore) {
		try {
			isValidUser(username, password);
			Libro lib = ls.findByAutor(autore);

			if (lib != null) {
				return new ResponseEntity<Libro>(lib, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByPrezzoMinoreDi/{prezzo}")
	public ResponseEntity<List<Libro>> getByPrezzoMinoreDi(@RequestHeader("username") String username, @RequestHeader("password") String password,@PathVariable("prezzo") Float prezzo) {
		try {
			isValidUser(username, password);
			List<Libro> libri = ls.findByPrezzoMinoreDi(prezzo);

			if (libri.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
			}

			return ResponseEntity.ok(libri); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
		}
	}

	@PostMapping(path="/inserisci", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void inserisci(@RequestHeader("username") String username, @RequestHeader("password") String password,@RequestBody Libro lib) {

		isAdmin(username, password);

		ls.insert(lib);
	}

	@PostMapping(path="/inserisciLibri", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void inserisciLibri(@RequestHeader("username") String username, @RequestHeader("password") String password,@RequestBody List<Libro> libri) {

		isAdmin(username, password);

		ls.insertLibri(libri);
	}

	@PutMapping(path="/aggiornamentoCompleto/{id}", consumes = "application/json")
	public ResponseEntity<Libro> aggiornamentoTotale(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody Libro lib, @PathVariable("id") Integer id) {
		try {
			isAdmin(username, password);

			Libro lib1 = ls.findById(id);

			if (lib1.getId().equals(id)) {

				lib1.setTitoloLibro(lib.getTitoloLibro());
				lib1.setAutoreLibro(lib.getAutoreLibro());
				lib1.setPrezzoLibro(lib.getPrezzoLibro());
				ls.update(lib1);
				return new ResponseEntity<>(lib1, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Libro> (HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping(path="/aggiornamentoParziale/{id}", consumes = "application/json")
	public ResponseEntity<Libro> aggiornamentoParziale(@RequestHeader("username") String username, @RequestHeader("password") String password,@RequestBody Libro lib, @PathVariable("id") Integer id) {
		try {
			isAdmin(username, password);

			Libro libro = ls.findById(id);

			if (libro.getId().equals(id)) {

				if (lib.getTitoloLibro() != null) {
					libro.setTitoloLibro(lib.getTitoloLibro());
				}
				if (lib.getAutoreLibro() != null) {
					libro.setAutoreLibro(lib.getAutoreLibro());
				}
				if (lib.getPrezzoLibro() != null) {
					libro.setPrezzoLibro(lib.getPrezzoLibro());
				}
				ls.update(lib);
				return new ResponseEntity<>(libro, HttpStatus.OK);
			}


			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Libro> delete(@RequestHeader("username") String username, @RequestHeader("password") String password,@PathVariable("id") Integer id) {
		try {
			isAdmin(username, password);

			ls.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteByPrezzoMinoreDi/{prezzoLibro}")
	public ResponseEntity<Libro> deleteByPrezzoMinoreDi(@RequestHeader("username") String username, @RequestHeader("password") String password,@PathVariable("prezzoLibro") Float prezzoLibro) {
		try {
			isAdmin(username, password);

			ls.deleteByPrezzoMinoreDi(prezzoLibro);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}


}