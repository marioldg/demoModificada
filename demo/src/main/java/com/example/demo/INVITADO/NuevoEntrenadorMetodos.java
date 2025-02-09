package com.example.demo.INVITADO;

import com.example.demo.clasesBase.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NuevoEntrenadorMetodos {

	public static List<String> obtenerNombreEntrenador(String rutaArchivo) {
		List<String> nombres = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(" ");
				if (partes.length >= 4) {
					String nombre = partes[1];
					nombres.add(nombre);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
		return nombres;
	}

	public int obtenerUltimoIdEntrenador(String nombreFichero, String perfil) {
		int ultimoId = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				String[] partes = linea.split(" ");
				if (partes.length == 4 && partes[2].toUpperCase().equals(perfil)){
					ultimoId = Integer.parseInt(partes[3]);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el fichero: " + e.getMessage());
		}
		return ultimoId;
	}

	public static boolean torneoExiste(String nombreFichero, String nombreBuscado) {
		try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				String[] partes = linea.split(" ");
				if (partes.length == 5) {
					String nombre = partes[1];
					if (nombre.equals(nombreBuscado)) {
						return true;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el fichero: " + e.getMessage());
		}
		return false;
	}




	public int obtenerIdTorneo(String nombreFichero, String nombre) {
		int a = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				String[] partes = linea.split(" ");
				if (linea.contains(nombre)) {
					a = Integer.parseInt(partes[0]);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el fichero: " + e.getMessage());
		}
		return a;
	}



	public static List<String> cargarPaises(String nombreFichero) {
		List<String> nombresPaises = new ArrayList<>();
		try {
			File archivo = new File(nombreFichero);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse(archivo);

			documento.getDocumentElement().normalize();

			NodeList listaPaises = documento.getElementsByTagName("pais");

			for (int i = 0; i < listaPaises.getLength(); i++) {
				Element elementoPais = (Element) listaPaises.item(i);
				String nombrePais = elementoPais.getElementsByTagName("nombre").item(0).getTextContent();
				nombresPaises.add(nombrePais);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nombresPaises;
	}


	public static boolean validarPais(String pais, List<String> listaPaises) {
		String paisNormalizado = pais.toLowerCase();
		for (String nombrePais : listaPaises) {
			if (nombrePais.toLowerCase().equals(paisNormalizado)) {
				return true;
			}
		}
		return false;
	}


	public static boolean validarCredenciales(String rutaArchivo, String usuario, String contrasena, String et) {
		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(" ");
				if (partes.length >= 3) {
					String usuarioArchivo = partes[0];
					String contrasenaArchivo = partes[1];
					String etArchivo = partes[2];
					if (usuarioArchivo.equalsIgnoreCase(usuario) && contrasenaArchivo.equals(contrasena) && etArchivo.equals(et)) {
						return true;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		}
		return false;
	}


	public void agregarEntrenadorTXT(String nombreFichero, Entrenador entrenador, String contraseña) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero, true))) {
			writer.write(entrenador.getNombre() + " " + contraseña + " Entrenador " + entrenador.getId());
			writer.newLine();
		} catch (IOException e) {
			System.out.println("Error al escribir en el fichero: " + e.getMessage());
		}
	}
}
