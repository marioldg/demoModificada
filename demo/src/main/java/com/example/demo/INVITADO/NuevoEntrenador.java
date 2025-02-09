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
        this.entrenadorService = entrenadorService;
        this.carnetService = carnetService;
    }

    public void crearEntrenador() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean bol = false;
        String user = null;
        String nacionalidad = null;
        String pass = null;

        do {
            System.out.println("CREACION DE ENTRENADOR: ");
            System.out.print("Nombre del entrenador: ");
            user = sc.next();
            System.out.print("Contraseña del entrenador: ");
            pass = sc.next();
            System.out.println("Nacionalidades disponibles:");
            List<String> listaPaises = NuevoEntrenadorMetodos.cargarPaises("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\paises.xml");
            System.out.println(listaPaises);
            System.out.print("Elige una nacionalidad: ");
            nacionalidad = sc.next();
            boolean paisValido = false;

            do {
                if (NuevoEntrenadorMetodos.validarPais(nacionalidad, listaPaises)) {
                    paisValido = true;
                } else {
                    System.out.println("ERROR : Nacionalidad incorrecta...");
                    System.out.println(listaPaises);
                    System.out.print("Elige una nacionalidad: ");
                    nacionalidad = sc.next();
                }
            } while (!paisValido);

            System.out.println("Usuario: " + user + "\nContraseña: " + pass + "\nNacionalidad: " + nacionalidad);
            System.out.println("1: Continuar || 2: Cancelar");
            int opcion = sc.nextInt();

            if (opcion == 1) {
                bol = true;
            } else {
                System.out.println("Operación cancelada,volviendo...");
                return;
            }
        } while (!bol);

        NuevoEntrenadorMetodos entrenador = new NuevoEntrenadorMetodos();
        long id = entrenador.obtenerUltimoIdEntrenador("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt", "Entrenador");
        if (id == 0) {
            id = 1;
        }
        id += 1;

        if (!NuevoEntrenadorMetodos.validarCredenciales("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt", user, pass, "Entrenador")) {
            System.out.print("Selecciona un torneo: ");
            System.out.println(NuevoEntrenadorMetodos.obtenerNombreEntrenador("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat"));
            String torneo = sc.next();

            if (NuevoEntrenadorMetodos.torneoExiste("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat", torneo)) {
                long idTorneo = entrenador.obtenerIdTorneo("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat", torneo);
                Torneo torneoBD = torneoService.buscarTorneoPorId(idTorneo);
                Set<Entrenador> entrenadorSet = torneoBD.getEntrenadores();

                if (entrenadorSet.size() < 3) {
                    Entrenador entrenador1 = new Entrenador();
                    entrenador1.setNombre(user);
                    entrenador1.setNacionalidad(nacionalidad);
                    entrenador1.agregarTorneo(torneoBD);
                    Carnet carnet = new Carnet();
                    carnet.setEntrenador(entrenador1);
                    carnet.setIdEntrenador(entrenador1.getId());
                    carnet.setFechaExpedicion(LocalDate.now());
                    carnet.setNumVictorias(0);
                    entrenador1.setCarnet(carnet);
                    carnetService.guardarCarnet(carnet);

                    NuevoEntrenadorMetodos metodosCrearEntrenador = new NuevoEntrenadorMetodos();
                    metodosCrearEntrenador.agregarEntrenadorTXT("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt", entrenador1, pass);

                    System.out.println("ENTRENADOR CREADO!!");
                } else {
                    System.out.println("ERROR : No se pueden crear más entrenadores para este torneo...");
                }
            } else {
                System.out.println("ERROR : El torneo no existe...");
            }
        } else {
            System.out.println("ERROR : Usuario ya existente...");
        }
    }
}
