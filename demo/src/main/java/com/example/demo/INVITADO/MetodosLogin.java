package com.example.demo.INVITADO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MetodosLogin {

    public static boolean validarCredenciales(String nombreFichero, String user, String pass) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\s+"); // Permite múltiples espacios

                if (partes.length >= 3) {
                    String nombre = partes[0];
                    String contraseña = partes[1];

                    if (nombre.equalsIgnoreCase(user) && contraseña.equals(pass)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }

    public static String obtenerPerfilPorCredenciales(String nombreFichero, String user, String pass) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\s+");

                if (partes.length >= 3) {
                    String nombre = partes[0];
                    String contraseña = partes[1];
                    String perfil = partes[2];

                    if (nombre.equalsIgnoreCase(user) && contraseña.equals(pass)) {
                        return perfil;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return null; // Si no se encuentra, retorna null
    }

    public static String obtenerIdPorCredenciales(String nombreFichero, String user, String pass) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\s+");

                if (partes.length >= 4) {
                    String nombre = partes[0];
                    String contraseña = partes[1];
                    String perfil = partes[2];
                    String id = partes[3];

                    if (nombre.equalsIgnoreCase(user) && contraseña.equals(pass)) {
                        return id;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return null;
    }
}
