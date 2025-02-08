package com.example.demo.ADMINT;

import com.example.demo.clasesBase.Combate;
import com.example.demo.clasesBase.Entrenador;
import com.example.demo.clasesBase.Torneo;
import com.example.demo.service.CombateService;
import com.example.demo.service.EntrenadorService;
import com.example.demo.service.TorneoService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ExportarDatosTorneo {

    TorneoService torneoService;
    EntrenadorService entrenadorService;
    CombateService combateService;

    public ExportarDatosTorneo(TorneoService torneoService,EntrenadorService entrenadorService,CombateService combateService){
        this.torneoService = torneoService;
        this.entrenadorService = entrenadorService;
        this.combateService = combateService;
    }

    public void exportarTorneo(String id) throws SQLException {
        Scanner sc = new Scanner(System.in);

        long idTorneo = Long.parseLong(id);

        Torneo torneo = torneoService.buscarTorneoPorId(idTorneo);


        System.out.println("Los datos del torneo que quieres exportar son los siguientes:");
        System.out.println("--------------------------------");
        System.out.println(torneo.toString());
        System.out.println("--------------------------------");

        Set<Entrenador> entrenadorSet = torneo.getEntrenadores();
        List<Entrenador> listaEntrenadores = new ArrayList<>(entrenadorSet);
        Set<Combate> combates = torneo.getCombates();
        List<Combate> listaCombates = new ArrayList<>(combates);

        System.out.println("Los datos de los entrenadores que quieres exportar son los siguientes:");
        System.out.println("--------------------------------");

        if(entrenadorSet.isEmpty()){
            System.out.println("No hay entrenadores en el Torneo ");
        }else {
            System.out.println(entrenadorSet);
        }
        System.out.println("--------------------------------");

        System.out.println("Los datos de los combates que quieres exportar son los siguientes:");
        System.out.println("--------------------------------");

        if(listaCombates.isEmpty()){
            System.out.println("No hay combates en el Torneo");
        }else {
            System.out.println(combates);
        }
        System.out.println("--------------------------------");


        System.out.print("Quieres guardarlo en un fichero .txt: ");
        String siNo = sc.next();
        if(siNo.equalsIgnoreCase("si")|| siNo.equalsIgnoreCase("s")){
            MetodosExportarTorneo metodosExportarTorneo = new MetodosExportarTorneo();
            MetodosExportarTorneo.escribirEnArchivo("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\DocsExportados\\"+torneo.getNombre().toUpperCase()+".txt",torneo,listaEntrenadores,listaCombates);
        }else{
            System.out.println("No se exportara a un fichero txt.");
        }



    }
}
