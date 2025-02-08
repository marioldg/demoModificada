package com.example.demo.ADMING;

import com.example.demo.clasesBase.Combate;
import com.example.demo.clasesBase.Torneo;
import com.example.demo.service.CombateService;
import com.example.demo.service.TorneoService;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class NuevoTorneo {


    TorneoService torneoService;
    CombateService combateService;

    public NuevoTorneo(TorneoService service, CombateService combateService) {
        this.torneoService = service;
        this.combateService = combateService;
    }

    public  void crearTorneo() throws SQLException {

        Scanner sc = new Scanner(System.in);
        System.out.println("CREACION DE TORNEO ");
        System.out.print("Nombre del torneo: ");
        String nombreTor = sc.next();
        System.out.print("Localidad del torneo: ");
        String localidad = sc.next();

        NuevoTorneoMetodos metodosCrearTorneo = new NuevoTorneoMetodos();

        if (!metodosCrearTorneo.existeTorneo("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat",nombreTor,localidad)){
            boolean val = false;
            String userADT = null;
            String passADT = null;
            do {
                System.out.println("CREACION DEL ADMINISTRADOR DEL TORNEO");
                System.out.print("Nombre del Administrador del Torneo: ");
                userADT = sc.next();
                System.out.print("Contraseña del Administrador del Torneo: ");
                passADT = sc.next();
                System.out.println("El usuario es: "+userADT+
                        "\nEl contraseña es: "+passADT);
                System.out.println("1 : continuar ||  2 : cancelar");
                int validar = sc.nextInt();

                if (validar == 1){
                    val = true;
                } else if (validar == 2) {
                    System.out.println("Proceso cancelado.Saliendo...");
                    return;
                }
            }while (!val);


            int code = metodosCrearTorneo.buscarIdTorneo("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat");

            code +=1;

            if (val){

                NuevoTorneoMetodos admin = new NuevoTorneoMetodos(userADT,passADT,"AT",code);

                if (!admin.existeUser("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt","AT")){

                    Torneo torneo = new Torneo();
                    torneo.setNombre(nombreTor);
                    torneo.setPuntosVictoria(10000);
                    torneo.setCodRegion(localidad.charAt(0));

                    torneoService.guardarTorneo(torneo);
                    Torneo torneoCreado = torneoService.buscarTorneoPorId(torneo.getId());

                    metodosCrearTorneo.guardarAT("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\torneo.dat",torneoCreado,userADT);
                    admin.guardarAT("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\files\\credenciales.txt","AT",torneoCreado.getId());
                    System.out.println("TORNEO CREADO!!");

                    System.out.println("CREACION DE COMBATES");

                    LocalDate[] fechas = new LocalDate[3];
                    boolean fechaValida;

                    for (int i = 0; i < 3; i++) {
                        fechaValida = false;

                        while (!fechaValida) {
                            System.out.print("Fecha del combate " + (i + 1) + " (YYYY-MM-DD): ");
                            String date = sc.next();

                            try {
                                fechas[i] = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                                fechaValida = true;
                            } catch (DateTimeParseException e) {
                                System.out.println("ERROR: Formato de fecha incorrecto... Inténtalo de nuevo.");
                            }
                        }
                    }

                    for(int i =0; i<fechas.length;i++) {

                        Combate combate = new Combate();
                        combate.setTorneo(torneoCreado);
                        combate.setFecha(fechas[i]);
                        combateService.guardarCombate(combate);
                    }
                    System.out.println("COMBATES GUARDADOS!!");
                }else {
                    System.out.println("ERROR: Administrador de Torneo existente...Torneo no creado");
                }
            }

        }else {
            System.out.println("ERROR : El Torneo ya existe.");
        }
    }
}
