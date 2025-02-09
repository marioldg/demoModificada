package com.example.demo.INVITADO;

import com.example.demo.ADMING.NuevoTorneo;
import com.example.demo.ENTRENADOR.ExportarCarnetXML;
import com.example.demo.ADMINT.ExportarDatosTorneo;
import com.example.demo.ADMINT.Inscribir;
import com.example.demo.ADMINT.Pelear;
import com.example.demo.service.CarnetService;
import com.example.demo.service.CombateService;
import com.example.demo.service.EntrenadorService;
import com.example.demo.service.TorneoService;

import java.sql.SQLException;
import java.util.Scanner;


public class Login {

	TorneoService service;

	CombateService combateService;

    EntrenadorService entrenadorService;

    CarnetService carnetService;

	public Login(TorneoService service,CombateService combateService,EntrenadorService entrenadorService,CarnetService carnetService) {
		this.service = service;
		this.combateService = combateService;
        this.entrenadorService = entrenadorService;
        this.carnetService = carnetService;
	}

	public  void inicioSesion() throws SQLException {

	Scanner sc = new Scanner(System.in);
    System.out.println("INICIO DE SESION");
    System.out.print("Nombre de usuario: ");
    String usu = sc.next();
    System.out.print("Contraseña: " );
    String contra = sc.next();

    MetodosLogin metodosLogin = new MetodosLogin();
    if (MetodosLogin.validarCredenciales("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt",usu,contra)){
        String rol = MetodosLogin.obtenerPerfilPorCredenciales("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt",usu,contra);
        while (true) {
            switch (rol) {
                case "AdminGeneral":

                    System.out.println("BIENVENIDO, ERES EL ADMIN GENERAL: ");
                    System.out.println("1- Crear un nuevo Torneo\n" +
                                       "2- Salir");

                    int num = sc.nextInt();
                    switch (num) {
                        case 1:
                        	NuevoTorneo crearTorneo = new NuevoTorneo(service,combateService);
                            crearTorneo.crearTorneo();
                            break;
                        case 2:
                            return;
                        default:
                            System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                            break;
                    }
                    break;

                case "AdminTorneo":
                    String data = MetodosLogin.obtenerIdPorCredenciales("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt", usu, contra);
                    System.out.println("BIENVENIDO, ERES EL ADMIN DEL TORNEO: ");
                    System.out.println("1- Exportar Datos de Torneo\n" +
                                       "2- Inscribir\n" +
                                        "3- Pelear\n"+
                                       "4- Salir");
                    int optionAT = sc.nextInt();

                    switch (optionAT) {
                        case 1:
                            ExportarDatosTorneo exportarDatosTorneo = new ExportarDatosTorneo(service,entrenadorService,combateService);
                            exportarDatosTorneo.exportarTorneo(data);
                            break;
                        case 2:
                            Inscribir inscribir = new Inscribir(combateService ,carnetService,service,entrenadorService);
                            inscribir.inscribir(data);
                            break;
                        case 3:
                            Pelear pelear = new Pelear(combateService,service,carnetService,entrenadorService);
                            pelear.pelear(data);
                            break;
                        case 4:
                            return;
                        default:
                            System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                            break;
                    }
                    break;

                case "Entrenador":
                    String id = MetodosLogin.obtenerIdPorCredenciales("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt", usu, contra);
                    System.out.println("BIENVENIDO, ERES EL ENTRENADOR: ");
                    System.out.println("1- Exportar tu Carnet en XML\n" +
                                       "2- Salir");
                    int optionE = sc.nextInt();
                    switch (optionE) {
                        case 1:
                            ExportarCarnetXML exportarCarnetXML = new ExportarCarnetXML(entrenadorService,carnetService,service,combateService);
                            exportarCarnetXML.exportarCarnet(id);
                            break;
                        case 2:
                            return;
                        default:
                            System.out.println("ERROR : Opción no válida...");
                            break;
                    }
                    break;

                default:
                    System.out.println("ERROR : Rol no identificado...");
                    return;
            }
        }


    }else{
        System.out.println("ERROR: Credenciales incorrectas...");
    }
	}
}
