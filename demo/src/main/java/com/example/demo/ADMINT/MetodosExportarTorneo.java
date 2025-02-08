package com.example.demo.ADMINT;

import com.example.demo.clasesBase.Combate;
import com.example.demo.clasesBase.Entrenador;
import com.example.demo.clasesBase.Torneo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MetodosExportarTorneo {

    public static void exportarFicheroTorneo(String nombreArchivo, Torneo torneo, List<Entrenador> entrenadores, List<Combate> combates) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write("DATOS TORNEO: " + torneo.toString());
            writer.newLine();

            if (!entrenadores.isEmpty()) {
                writer.write("DATOS ENTRENADORES :");
                writer.newLine();
                for (Entrenador entrenador : entrenadores) {
                    writer.write(entrenador.toString());
                    writer.newLine();
                }
            }

            if (!combates.isEmpty()) {
                writer.write("DATOS COMBATES:");
                writer.newLine();
                for (Combate combate : combates) {
                    writer.write(combate.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
