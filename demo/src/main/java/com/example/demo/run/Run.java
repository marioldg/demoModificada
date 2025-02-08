package com.example.demo.run;

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
public class Run {

	 	
	     TorneoService torneoService;

	    
	     EntrenadorService entrenadorService;
	     
	     CombateService combateRepository;
	     
	     CarnetService carnetService;
	     

	    
	    
    
    public Run(TorneoService torneoService, EntrenadorService entrenadorService, CombateService combateRepository, CarnetService carnetService) {
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
            System.out.println("--------------------------------");
            System.out.println("Menu Invitado");
            System.out.println("--------------------------------");
            System.out.println(
                    "1- Iniciar Sesion\n" +
                    "2- Crear Entrenador\n" +
                    "3- Salir\n"+
                            "--------------------------------\n"+
                            "Que quieres hacer: Indica un numero");
            System.out.println("--------------------------------");

            if (!sc.hasNextInt()) { // Si no es un número, limpiar entrada y volver a preguntar
                System.out.println("Entrada inválida. Por favor, ingresa un número válido.");
                sc.next(); // Descarta la entrada incorrecta
                continue; // Reinicia el bucle
            }

            int opcion = sc.nextInt();
            System.out.println("--------------------------------");

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
                    System.out.println("--------------------------------");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (!salir);
    }
}

