package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import model.Alumnos;

/**
 * Controlador para gestión de Alumnos en archivos DAT (texto) Implementa CRUD
 * completo y operaciones de búsqueda/filtrado
 */
public class AlumnoController {

	private static final Path DATA_PATH = Paths.get("alumnos.dat");
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	/**
	 * Lee todos los alumnos del archivo DAT
	 */
	public List<Alumnos> leerTodos() {
		List<Alumnos> alumnos = new ArrayList<>();
		try {
			if (!Files.exists(DATA_PATH)) {
				System.out.println("⚠ Archivo no existe, se creará al agregar datos.");
				return alumnos;
			}

			List<String> lines = Files.readAllLines(DATA_PATH, StandardCharsets.UTF_8);
			for (String line : lines) {
				if (line.trim().isEmpty())
					continue;
				String[] parts = line.split(";");
				if (parts.length >= 3) {
					String nombre = parts[0];
					int edad = Integer.parseInt(parts[1]);
					Date fechaNacimiento = SDF.parse(parts[2]);
					alumnos.add(new Alumnos(nombre, edad, fechaNacimiento));
				}
			}
			System.out.println("✓ Se encontraron " + alumnos.size() + " alumnos");
		} catch (Exception e) {
			System.err.println("✗ Error al leer datos: " + e.getMessage());
		}
		return alumnos;
	}

	/**
	 * Busca un alumno por nombre exacto
	 */
	public Alumnos buscarPorNombre(String nombre) {
		List<Alumnos> todos = leerTodos();
		for (Alumnos a : todos) {
			if (a.getNombre().equalsIgnoreCase(nombre)) {
				System.out.println("✓ Alumno encontrado: " + formatAlumno(a));
				return a;
			}
		}
		System.out.println("✗ No se encontró alumno con nombre: " + nombre);
		return null;
	}

	/**
	 * Busca alumnos por coincidencia parcial en el nombre
	 */
	public List<Alumnos> buscarPorNombreParcial(String nombreParcial) {
		List<Alumnos> resultados = new ArrayList<>();
		List<Alumnos> todos = leerTodos();
		String busqueda = nombreParcial.toLowerCase();

		for (Alumnos a : todos) {
			if (a.getNombre().toLowerCase().contains(busqueda)) {
				resultados.add(a);
			}
		}

		if (resultados.isEmpty()) {
			System.out.println("✗ No se encontraron alumnos con: " + nombreParcial);
		} else {
			System.out.println("✓ Se encontraron " + resultados.size() + " alumno(s)");
		}
		return resultados;
	}

	/**
	 * Filtra alumnos por rango de edad
	 */
	public List<Alumnos> filtrarPorEdad(int edadMin, int edadMax) {
		List<Alumnos> resultados = new ArrayList<>();
		List<Alumnos> todos = leerTodos();

		for (Alumnos a : todos) {
			if (a.getEdad() >= edadMin && a.getEdad() <= edadMax) {
				resultados.add(a);
			}
		}

		if (resultados.isEmpty()) {
			System.out.println("✗ No hay alumnos entre " + edadMin + " y " + edadMax + " años");
		} else {
			System.out.println("✓ Se encontraron " + resultados.size() + " alumno(s)");
		}
		return resultados;
	}

	/**
	 * Crea un nuevo alumno
	 */
	public boolean crear(Alumnos alumno) {
		try {
			List<Alumnos> todos = leerTodos();

			// Verificar si ya existe
			for (Alumnos a : todos) {
				if (a.getNombre().equalsIgnoreCase(alumno.getNombre())) {
					System.out.println("✗ Ya existe un alumno con ese nombre");
					return false;
				}
			}

			todos.add(alumno);
			guardarTodos(todos);
			System.out.println("✓ Alumno agregado: " + alumno.getNombre());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al crear alumno: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Actualiza un alumno existente por nombre
	 */
	public boolean actualizar(String nombreViejo, Alumnos alumnoNuevo) {
		try {
			List<Alumnos> todos = leerTodos();
			boolean encontrado = false;

			for (int i = 0; i < todos.size(); i++) {
				if (todos.get(i).getNombre().equalsIgnoreCase(nombreViejo)) {
					todos.set(i, alumnoNuevo);
					encontrado = true;
					break;
				}
			}

			if (encontrado) {
				guardarTodos(todos);
				System.out.println("✓ Alumno actualizado: " + nombreViejo + " → " + alumnoNuevo.getNombre());
				return true;
			} else {
				System.out.println("✗ No se encontró alumno con nombre: " + nombreViejo);
				return false;
			}
		} catch (Exception e) {
			System.err.println("✗ Error al actualizar: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Elimina un alumno por nombre
	 */
	public boolean eliminar(String nombre) {
		try {
			List<Alumnos> todos = leerTodos();
			Iterator<Alumnos> iterator = todos.iterator();
			boolean eliminado = false;

			while (iterator.hasNext()) {
				Alumnos a = iterator.next();
				if (a.getNombre().equalsIgnoreCase(nombre)) {
					iterator.remove();
					eliminado = true;
					break;
				}
			}

			if (eliminado) {
				guardarTodos(todos);
				System.out.println("✓ Alumno eliminado: " + nombre);
				return true;
			} else {
				System.out.println("✗ No se encontró alumno con nombre: " + nombre);
				return false;
			}
		} catch (Exception e) {
			System.err.println("✗ Error al eliminar: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exporta alumnos a CSV
	 */
	public boolean exportarCSV(String nombreArchivo) {
		try {
			List<Alumnos> alumnos = leerTodos();
			Path path = Paths.get(nombreArchivo);

			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
				writer.println("Nombre,Edad,FechaNacimiento");
				for (Alumnos a : alumnos) {
					writer.println(
							String.format("%s,%d,%s", a.getNombre(), a.getEdad(), SDF.format(a.getFechaInscripcion())));
				}
			}
			System.out.println("✓ Exportado a CSV: " + path.toAbsolutePath());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al exportar CSV: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exporta alumnos a XML
	 */
	public boolean exportarXML(String nombreArchivo) {
		try {
			List<Alumnos> alumnos = leerTodos();
			Path path = Paths.get(nombreArchivo);

			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
				writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				writer.println("<alumnos>");
				for (Alumnos a : alumnos) {
					writer.println("  <alumno>");
					writer.println("    <nombre>" + xmlEscape(a.getNombre()) + "</nombre>");
					writer.println("    <edad>" + a.getEdad() + "</edad>");
					writer.println(
							"    <fechaNacimiento>" + SDF.format(a.getFechaInscripcion()) + "</fechaNacimiento>");
					writer.println("  </alumno>");
				}
				writer.println("</alumnos>");
			}
			System.out.println("✓ Exportado a XML: " + path.toAbsolutePath());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al exportar XML: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exporta alumnos a DAT (copia del archivo actual)
	 */
	public boolean exportarDAT(String nombreArchivo) {
		try {
			Path destino = Paths.get(nombreArchivo);
			Files.copy(DATA_PATH, destino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			System.out.println("✓ Exportado a DAT: " + destino.toAbsolutePath());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al exportar DAT: " + e.getMessage());
			return false;
		}
	}

	// ==================== MÉTODOS AUXILIARES PRIVADOS ====================

	/**
	 * Guarda todos los alumnos al archivo DAT
	 */
	private void guardarTodos(List<Alumnos> alumnos) throws IOException {
		List<String> lines = new ArrayList<>();
		for (Alumnos a : alumnos) {
			lines.add(formatAlumno(a));
		}
		Files.write(DATA_PATH, lines, StandardCharsets.UTF_8);
	}

	/**
	 * Formatea un alumno para archivo DAT (formato: nombre;edad;dd/MM/yyyy)
	 */
	private String formatAlumno(Alumnos a) {
		return String.format("%s;%d;%s", a.getNombre(), a.getEdad(), SDF.format(a.getFechaInscripcion()));
	}

	/**
	 * Escapa caracteres especiales XML
	 */
	private String xmlEscape(String text) {
		if (text == null)
			return "";
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'",
				"&apos;");
	}
}
