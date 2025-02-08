package com.example.demo.INVITADO;

import com.example.demo.clasesBase.*;
import com.example.demo.service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class NuevoEntrenador {
	
	EntrenadorService entrenadorService;
	

	
	TorneoService torneoService;
	
	CarnetService carnetService;
	
	public NuevoEntrenador(TorneoService torneoService, EntrenadorService entrenadorService, CarnetService carnetService) {
		this.torneoService = torneoService;
		this.entrenadorService =  entrenadorService;
	
		this.carnetService = carnetService;
	}

	public void crearEntrenador() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean pepe = false;
        String nombre =null;
        String nacionalidad = null;
        String contra = null;
        
        do {
            System.out.print("Dame nombre como nuevo Entrenador: ");
            nombre = sc.next();
            System.out.println("--------------------------------");
            System.out.print("Dame una contraseña para el nuevo Entrenador: ");
            contra = sc.next();
            System.out.println("--------------------------------");
            System.out.println("Para las nacionalidaes tienes las siguientes: ");
            System.out.println("--------------------------------");
            List<String> listaPaises = NuevoEntrenadorMetodos.cargarNombresDePaises("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\paises.xml");
            System.out.println(listaPaises);
            System.out.println("--------------------------------");
            System.out.print("Dame una nacionalidad: ");
            nacionalidad = sc.next();
            boolean meme = false;
            do {
                if (NuevoEntrenadorMetodos.verificarPais(nacionalidad,listaPaises)){
                    meme= true;
                }else {
                    System.out.println("--------------------------------");
                    System.out.println("La nacionalidad que has introducido esta mal. Vuelva a introducir.");
                    System.out.println("--------------------------------");
                    System.out.println(listaPaises);
                    System.out.println("--------------------------------");
                    System.out.print("Dame una nacionalidad: ");
                    nacionalidad = sc.next();
                }
            }while (!meme);

            System.out.println("--------------------------------");
            System.out.println("El usuario es: "+nombre+"\nLa contraseña es: "+contra+
                    "\nLa nacinalidad es: "+nacionalidad);
            System.out.println("--------------------------------");
            System.out.println("Si o No");
            System.out.println("--------------------------------");
            String validar = sc.next();


            if (validar.equalsIgnoreCase("si")||validar.equalsIgnoreCase("s")){
                pepe = true;
            }
        }while (!pepe);
        System.out.println("--------------------------------");

       
            NuevoEntrenadorMetodos entrenador = new NuevoEntrenadorMetodos();
            long id = entrenador.obtenerUltimoIdEntrenador("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt","ET");
            if(id == 0){
                id = 1000;
            }
            id += 1;

            if (!NuevoEntrenadorMetodos.verificarCredenciales("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt",nombre,contra,"ET")){

                System.out.print("En que torneo te encuentras entre estos: ");
                System.out.println(NuevoEntrenadorMetodos.obtenerNombresDesdeArchivo("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat"));
                String torneo = sc.next();

                if (NuevoEntrenadorMetodos.buscarNombreTorneo("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat",torneo)){

                    System.out.println("--------------------------------");
                    System.out.println("Existe el torneo");
                    System.out.println("--------------------------------");
                    System.out.println("Vamos a crearte como entrenador.");
                    System.out.println("--------------------------------");



                    long a = entrenador.sacarIdTorneo("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat",torneo);
                    System.out.println("🧐 ID del torneo obtenido: " + a);
                    
                    Torneo torneoBD = torneoService.buscarTorneoPorId(a);
                    Set<Entrenador> entrenadorSet = torneoBD.getEntrenadores();

                    int numEntre = entrenadorSet.size();
                    if (numEntre < 3){
                        Entrenador entrenador1 = new Entrenador();
                        entrenador1.setNombre(nombre);
                        entrenador1.setNacionalidad(nacionalidad);
                        entrenador1.agregarTorneo(torneoBD);
                        Carnet carnet = new Carnet();
                        carnet.setEntrenador(entrenador1);
                        carnet.setIdEntrenador(entrenador1.getId());
                        carnet.setFechaExpedicion(LocalDate.now());
                        carnet.setNumVictorias(0);
                        carnet.setNumVictorias(0);
                        entrenador1.setCarnet(carnet);
                        carnetService.guardarCarnet(carnet);

                        NuevoEntrenadorMetodos metodosCrearEntrenador = new NuevoEntrenadorMetodos();
                        metodosCrearEntrenador.escribirAlFinalDelTxtEntrenador("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt",entrenador1,contra);


                        System.out.println("Se crea todo en la base de datos y en credenciales.txt");

                    }else {
                        System.out.println("No se puede crear mas entrenadores para este Torneo");

                    }

                    

                }else{
                    System.out.println("--------------------------------");
                    System.out.println("No existe el torneo.");
                    System.out.println("--------------------------------");
                    System.out.println("El entrenador no se ha creado.");
                }
            }else {
                System.out.println("El nombre del nuevo usuario como Entrenador ya esta en uso.");
                System.out.println("--------------------------------");
                System.out.println("El entrenador no se ha creado.");
            }
        }
	
}
