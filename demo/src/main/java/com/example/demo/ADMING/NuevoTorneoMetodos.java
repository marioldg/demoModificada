package com.example.demo.ADMING;

import com.example.demo.clasesBase.Torneo;

import java.io.*;

public class NuevoTorneoMetodos {

    private String nombre;
    private String contraseña;
    private String perfil;
    private int id;

    /**
     * Constructor con parámetros para inicializar un administrador.
     */
    public NuevoTorneoMetodos(String nombre, String contraseña, String perfil, int id) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.perfil = perfil;
        this.id = id;
    }

    /**
     * Constructor vacío.
     */
    public NuevoTorneoMetodos() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Busca el último ID de torneo en un fichero.
     * @param nombreFichero Ruta del archivo.
     * @return Último ID encontrado.
     */
    public int buscarIdTorneo(String nombreFichero) {
        int ult = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length == 5) {
                    ult = Integer.parseInt(partes[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return ult;
    }

    /**
     * Verifica si un torneo existe en el archivo.
     * @param nombreFichero Ruta del archivo.
     * @param nombre Nombre del torneo.
     * @param cod Código de la región.
     * @return true si el torneo existe, false si no.
     */
    public boolean existeTorneo(String nombreFichero, String nombre, String cod) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(nombre) && line.contains(cod)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }

    /**
     * Comprueba si un torneo ya está registrado en el fichero.
     * @param nombreFichero Ruta del archivo.
     * @param torneo Objeto torneo a verificar.
     * @return true si ya está registrado, false en caso contrario.
     */
    public boolean torneoRegistrado(String nombreFichero, Torneo torneo) {
        String code = String.valueOf(torneo.getCodRegion());
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(torneo.getNombre()) && line.contains(code)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifica si un usuario existe en el archivo.
     * @param nombreFichero Ruta del archivo.
     * @param perfil Tipo de perfil del usuario.
     * @return true si el usuario existe, false si no.
     */
    public boolean existeUser(String nombreFichero, String perfil) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(this.nombre) && line.contains(perfil)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
        return false;
    }

    /**
     * Guarda un usuario administrador de torneo si no existe.
     * @param nombreFichero Ruta del archivo.
     * @param perfil Tipo de perfil del usuario.
     * @param id ID del torneo asociado.
     */
    public void guardarAT(String nombreFichero, String perfil, long id) {
        if (!existeUser(nombreFichero, perfil)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
                writer.write(this.nombre + " " + this.contraseña + " " + this.perfil + " " + id);
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error al escribir en el fichero: " + e.getMessage());
            }
        } else {
            System.out.println("El administrador ya está creado, no puede duplicarse.");
        }
    }

    /**
     * Guarda un torneo en el archivo si no está registrado.
     * @param nombreFichero Ruta del archivo.
     * @param torneo Objeto torneo a guardar.
     * @param nombre Nombre del administrador del torneo.
     */
    public void guardarAT(String nombreFichero, Torneo torneo, String nombre) {
        if (!torneoRegistrado(nombreFichero, torneo)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
                writer.write(torneo.getId() + " " + torneo.getNombre() + " " + torneo.getCodRegion() + " " + torneo.getPuntosVictoria() + " " + nombre);
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error al escribir en el fichero: " + e.getMessage());
            }
        }
    }
}
