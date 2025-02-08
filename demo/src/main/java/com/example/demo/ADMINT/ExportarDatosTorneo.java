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

    public ExportarDatosTorneo(TorneoService torneoService, EntrenadorService entrenadorService, CombateService combateService) {
        this.torneoService = torneoService;
        this.entrenadorService = entrenadorService;
        this.combateService = combateService;
    }

    public void exportarTorneo(String id) throws SQLException {
        Scanner sc = new Scanner(System.in);

        long idTorneo = Long.parseLong(id);
        Torneo torneo = torneoService.buscarTorneoPorId(idTorneo);

        System.out.println("EXPORTAR TORNEO ");
        System.out.println("DATOS TORNEO:");
        System.out.println(torneo.toString());

        Set<Entrenador> entrenadorSet = torneo.getEntrenadores();
        List<Entrenador> listaEntrenadores = new ArrayList<>(entrenadorSet);
        Set<Combate> combates = torneo.getCombates();
        List<Combate> listaCombates = new ArrayList<>(combates);

        System.out.println("DATOS ENTRENADORES:");
        if (entrenadorSet.isEmpty()) {
            System.out.println("ERROR: No hay entrenadores en el torneo");
        } else {
            System.out.println(entrenadorSet);
        }

        System.out.println("DATOS COMBATES:");
        if (listaCombates.isEmpty()) {
            System.out.println("ERROR: No hay combates en el torneo");
        } else {
            System.out.println(combates);
        }

        System.out.print("1 : guardar en txt || 2 : no guardar: ");
        int opcion = sc.nextInt();
        if (opcion == 1) {
            MetodosExportarTorneo metodosExportarTorneo = new MetodosExportarTorneo();
            MetodosExportarTorneo.exportarFicheroTorneo("C:\\Users\\USER\\Desktop\\DAM2\\demo\\src\\main\\java\\com\\example\\demo\\DocsExportados\\"
                    + torneo.getNombre().toUpperCase() + ".txt", torneo, listaEntrenadores, listaCombates);
            System.out.println("TORNEO GUARDADO CORRECTAMENTE!!");
        } else if (opcion == 2) {
            System.out.println("No se exportara a un fichero txt.");
        } else {
            System.out.println("ERROR: Opción no válida.. No se exportara a un fichero txt.");
        }
    }
}
