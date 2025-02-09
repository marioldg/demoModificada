package com.example.demo.principal;

import com.example.demo.INVITADO.Login;
import com.example.demo.INVITADO.NuevoEntrenador;
import com.example.demo.service.CarnetService;
import com.example.demo.service.CombateService;
import com.example.demo.service.EntrenadorService;
import com.example.demo.service.TorneoService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Scanner;

@Service
public class Principal {

	 	
	     TorneoService torneoService;

	    
	     EntrenadorService entrenadorService;
	     
	     CombateService combateRepository;
	     
	     CarnetService carnetService;
	     

	    
	    
    
    public Principal(TorneoService torneoService, EntrenadorService entrenadorService, CombateService combateRepository, CarnetService carnetService) {
			super();
			this.torneoService = torneoService;
			this.entrenadorService = entrenadorService;
			this.combateRepository = combateRepository;
			this.carnetService = carnetService;
			
		}

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        do {
            System.out.println("BIENVENIDO A POKEMON ");
            System.out.println("***********************");
            System.out.println("MENU DE INVITADO: ");
            System.out.println(
                    "1- Iniciar Sesion\n" +
                    "2- Crear Entrenador\n" +
                    "3- Salir");

            if (!sc.hasNextInt()) {
                System.out.println("ERROR: Entrada inválida...");
                sc.next();
                continue;
            }

            int opcion = sc.nextInt();


            switch (opcion) {
                case 1:
                    Login login = new Login(torneoService, combateRepository, entrenadorService, carnetService);
                    try {
                        login.inicioSesion();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    NuevoEntrenador crearEntrenador = new NuevoEntrenador(torneoService, entrenadorService, carnetService);
                    try {
                        crearEntrenador.crearEntrenador();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    salir = true;
                    break;
                default:
                    System.out.println("ERROR : Opción no válida...");
            }
        } while (!salir);
    }
}

