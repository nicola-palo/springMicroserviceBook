package com.azienda.esercizioMicroserviceLibri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azienda.esercizioMicroserviceLibri.model.Libro;
import com.azienda.esercizioMicroserviceLibri.repository.LibroRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class LibroService {

	@Autowired
	LibroRepository libroRepository;
	
	public List<Libro> getAll() {
		return libroRepository.findAll();
	}
	
	public void insert(Libro lib) {
		libroRepository.save(lib);
	}
	
	public Libro findById (Integer id) {
		return libroRepository.findById(id).orElse(null);
	}
	
	public Libro findByTitle (String titolo) {
		return libroRepository.findByTitle(titolo);
	}
	
	public Libro findByAutor (String titolo) {
		return libroRepository.findByAutor(titolo);
	}
	
	public List<Libro> findByPrezzoMinoreDi(Float prezzo) {
        return libroRepository.findByPrezzoMinoreDi(prezzo); 
    }
	
	public void insertLibri(List<Libro> libri) {
        libroRepository.saveAll(libri);  
    }
	
	public void update(Libro lib) {
		if (lib.getId() != null)
		libroRepository.save(lib);
	}
	
	public void delete(Integer id) {
		libroRepository.deleteById(id);
	}
	
	public void deleteByPrezzoMinoreDi(Float prezzoLibro) {
		libroRepository.deleteByPrezzoMinoreDi(prezzoLibro);
	}
}
