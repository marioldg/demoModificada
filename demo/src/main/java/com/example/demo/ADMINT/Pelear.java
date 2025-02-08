package com.example.demo.ADMINT;

import com.example.demo.clasesBase.Carnet;
import com.example.demo.clasesBase.Combate;
import com.example.demo.clasesBase.Entrenador;
import com.example.demo.clasesBase.Torneo;
import com.example.demo.service.CarnetService;
import com.example.demo.service.CombateService;
import com.example.demo.service.EntrenadorService;
import com.example.demo.service.TorneoService;

import java.util.*;

public class Pelear {

    CarnetService carnetService;
    CombateService combateService;
    TorneoService torneoService;
    EntrenadorService entrenadorService;

    public Pelear(CombateService combateService, TorneoService torneoService, CarnetService carnetService, EntrenadorService entrenadorService) {
        this.combateService = combateService;
        this.torneoService = torneoService;
        this.entrenadorService = entrenadorService;
        this.carnetService = carnetService;
    }

    public void pelear(String id) {
        Scanner sc = new Scanner(System.in);
        long id_Tor = Long.parseLong(id);
        Torneo torneo = torneoService.buscarTorneoPorId(id_Tor);
        System.out.println("DATOS TORNEO: ");
        System.out.println(torneo.toString());

        Set<Combate> combates = torneo.getCombates();
        List<Combate> listaCombates = new ArrayList<>(combates);

        if (listaCombates.get(0).getGanador() != null) {
            System.out.println("ERROR: Ya han peleado...");
            return;
        }

        if (todosCombatesConEntrenadores(listaCombates)) {
            System.out.println("TORNEO COMPLETO!!.");


            Random random = new Random();
            for (Combate combate : listaCombates) {
                int ganadorAleatorio = random.nextInt(2);
                Entrenador ganador;
                if (ganadorAleatorio == 0) {
                    ganador = combate.getEntrenador1();
                    combate.setGanador(ganador.getId());
                    System.out.println("EN EL COMBATE " + combate.getId() + " EL GANADOR ES: " + ganador.getNombre());
                } else {
                    ganador = combate.getEntrenador2();
                    combate.setGanador(ganador.getId());
                    System.out.println("EN EL COMBATE " + combate.getId() + " EL GANADOR ES: " + ganador.getNombre());
                }
                Carnet carnetGanador = carnetService.buscarCarnetPorId(ganador.getId());
                int puntosTorneo = torneo.getPuntosVictoria();
                carnetGanador.setPuntos(carnetGanador.getPuntos() + puntosTorneo);
                carnetGanador.setNumVictorias(carnetGanador.getNumVictorias() + 1);
                carnetService.guardarCarnet(carnetGanador);
                combateService.guardarCombate(combate);
            }
        } else {
            System.out.println("ERROR : Algunos combates no tienen ambos entrenadores asignados...");
        }
    }

    public boolean todosCombatesConEntrenadores(List<Combate> combates) {
        for (Combate combate : combates) {
            if (combate.getEntrenador1() == null || combate.getEntrenador2() == null) {
                return false;
            }
        }
        return true;
    }
}
