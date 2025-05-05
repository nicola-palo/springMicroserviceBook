package com.azienda.esercizioMicroserviceLibri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import com.azienda.esercizioMicroserviceLibri.model.Libro;
import com.azienda.esercizioMicroserviceLibri.service.LibroService;

@SpringBootApplication(scanBasePackages = {"com.azienda.esercizioMicroserviceLibri.service", "com.azienda.esercizioMicroserviceLibri.controller"})
@EnableJpaRepositories(basePackages = {"com.azienda.esercizioMicroserviceLibri.repository"})
@EntityScan(basePackages = {"com.azienda.esercizioMicroserviceLibri.model"})
public class EsercizioMicroserviceLibriApplication {

	public static void main(String[] args) {
		
		
		try {
			ConfigurableApplicationContext context = SpringApplication.run(EsercizioMicroserviceLibriApplication.class, args);
			LibroService ls = (LibroService) context.getBean("libroService");
			
			Libro libro = new Libro("ciao", "ciaociao", 11.29f);
			Libro libro1 = new Libro("1111", "c1111o", 22.29f);
			Libro libro2 = new Libro("2222", "c2222o", 33.29f);
			Libro libro3 = new Libro("3333", "c3333o", 44.29f);
			ls.insert(libro);
			ls.insert(libro1);
			ls.insert(libro2);
			ls.insert(libro3);
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
