package com.azienda.esercizioMicroserviceUtenti;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.azienda.esercizioMicroserviceUtenti.service.UtenteService;

@SpringBootApplication(scanBasePackages = {"com.azienda.esercizioMicroserviceUtenti.service", "com.azienda.esercizioMicroserviceUtenti.controller"})
@EnableJpaRepositories(basePackages = {"com.azienda.esercizioMicroserviceUtenti.repository"})
@EntityScan(basePackages = {"com.azienda.esercizioMicroserviceUtenti.model"})
public class EsercizioMicroserviceUtentiApplication {

	public static void main(String[] args) {
		
		
		try {
			
			ConfigurableApplicationContext context = SpringApplication.run(EsercizioMicroserviceUtentiApplication.class, args);
			UtenteService us = (UtenteService) context.getBean("utenteService");
			
			us.createAdminBase();
			us.createUserBase();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
