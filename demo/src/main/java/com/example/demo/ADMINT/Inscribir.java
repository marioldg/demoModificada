package com.example.demo.ADMINT;

import com.example.demo.clasesBase.Combate;
import com.example.demo.clasesBase.Entrenador;
import com.example.demo.clasesBase.Torneo;
import com.example.demo.service.CarnetService;
import com.example.demo.service.CombateService;
import com.example.demo.service.EntrenadorService;
import com.example.demo.service.TorneoService;

import java.util.*;

public class Inscribir {

    CarnetService carnetService;
    CombateService combateService;
    TorneoService torneoService;
    EntrenadorService entrenadorService;

    public Inscribir(CombateService combateService, CarnetService carnetService, TorneoService torneoService, EntrenadorService entrenadorService) {
        this.carnetService = carnetService;
        this.combateService = combateService;
        this.torneoService = torneoService;
        this.entrenadorService = entrenadorService;
    }

    public void inscribir(String id) {
        Scanner sc = new Scanner(System.in);
        long id_Tor = Long.parseLong(id);
        Torneo torneo = torneoService.buscarTorneoPorId(id_Tor);
        System.out.println("INFORMACION TORNEO:");
        System.out.println(torneo.toString());

        Set<Combate> combates = torneo.getCombates();
        List<Combate> listaCombates = new ArrayList<>(combates);

        Set<Entrenador> entrenadorSet = torneo.getEntrenadores();
        List<Entrenador> listaEntrenadores = new ArrayList<>(entrenadorSet);

        if (listaCombates.get(1).getGanador() != null) {
            System.out.println("ERROR : Torneo finalizado...");
            return;
        }

        if (listaEntrenadores.size() < 3) {
            System.out.println("ERROR : Torneo incompleto...");
        }

        if (listaEntrenadores.isEmpty() || listaEntrenadores.size() < 2) {
            System.out.println("ERROR : Faltan entrenadores...");
            return;
        }

        System.out.println("ENNTRENADORES DISPONIBLES:");
        for (int i = 0; i < listaEntrenadores.size(); i++) {
            System.out.println((i + 1) + ". " + listaEntrenadores.get(i).getNombre());
        }

        System.out.print("Nombre entrenador 1: ");
        String nombreEntrenador1 = sc.nextLine();
        System.out.print("Nombre entrenador 2: ");
        String nombreEntrenador2 = sc.nextLine();

        Entrenador entrenador1 = listaEntrenadores.stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombreEntrenador1))
                .findFirst()
                .orElse(null);

        Entrenador entrenador2 = listaEntrenadores.stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombreEntrenador2))
                .findFirst()
                .orElse(null);

        if (entrenador1 == null || entrenador2 == null) {
            System.out.println("ERROR : Alguno de los entrenadores no estan en el torneo...");
            return;
        }

        if (entrenador1.getId().equals(entrenador2.getId())) {
            System.out.println("ERROR : Un entrenador no puede pelear contra si mismo...");
            return;
        }

        boolean combateRepetido = listaCombates.stream()
                .anyMatch(combate -> {
                    Long id1 = combate.getEntrenador1() != null ? combate.getEntrenador1().getId() : null;
                    Long id2 = combate.getEntrenador2() != null ? combate.getEntrenador2().getId() : null;
                    return (id1 != null && id2 != null) &&
                            ((id1.equals(entrenador1.getId()) && id2.equals(entrenador2.getId())) ||
                                    (id1.equals(entrenador2.getId()) && id2.equals(entrenador1.getId())));
                });

        if (combateRepetido) {
            System.out.println("ERROR: Entradores ya asignados a un combate juntos...");
            return;
        }

        Optional<Combate> combateDisponible = listaCombates.stream()
                .filter(combate -> combate.getEntrenador1() == null)
                .findFirst();

        if (combateDisponible.isPresent()) {
            Combate combate = combateDisponible.get();
            combate.setEntrenador1(entrenador1);
            combate.setEntrenador2(entrenador2);
            combateService.guardarCombate(combate);
            System.out.println("ENTRENADORES ASIGNADOS A UN COMBATE!!.");
        }
    }
}
