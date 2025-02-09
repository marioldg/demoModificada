package com.example.demo;

import com.example.demo.principal.Principal;
import com.example.demo.service.CarnetService;
import com.example.demo.service.CombateService;
import com.example.demo.service.EntrenadorService;
import com.example.demo.service.TorneoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	TorneoService service;

	@Autowired
	EntrenadorService entrenadorService;

	@Autowired
	CombateService combateService;


	@Autowired
	CarnetService carnetService;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Principal iniciarPrograma = new Principal(service,entrenadorService,combateService,carnetService);
		iniciarPrograma.mostrarMenu();

	}


}
