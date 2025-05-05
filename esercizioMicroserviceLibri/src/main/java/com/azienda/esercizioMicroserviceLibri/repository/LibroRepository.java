package com.azienda.esercizioMicroserviceLibri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.azienda.esercizioMicroserviceLibri.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer>{
	
	@Query("SELECT l FROM Libro l WHERE l.prezzoLibro <= :prezzoLibro")
    public List<Libro> findByPrezzoMinoreDi(@Param("prezzoLibro") Float prezzo);
	
	@Query("SELECT l FROM Libro l WHERE l.titoloLibro LIKE :titoloLibro")
	public Libro findByTitle (@Param("titoloLibro") String titoloLibro);
	
	@Query("SELECT l FROM Libro l WHERE l.autoreLibro LIKE :autoreLibro")
	public Libro findByAutor (@Param("autoreLibro") String autoreLibro);
	
	@Modifying
    @Query("DELETE FROM Libro l WHERE l.prezzoLibro < :prezzoLibro")
    public void deleteByPrezzoMinoreDi(@Param("prezzoLibro") Float prezzoLibro);

}
