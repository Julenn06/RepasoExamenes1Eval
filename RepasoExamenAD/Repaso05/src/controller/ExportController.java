package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import model.Alumnos;
import model.Curso;

/**
 * ExportController - Exportación de datos a XML y DAT.
 * 
 * Funciones: 
 * 1. exportarAlumnosXML() - Exporta alumnos a formato XML estructurado
 * 2. exportarAlumnosDAT() - Exporta alumnos a formato DAT (texto plano con ;) 
 * 3. exportarCursosXML() - Exporta cursos a formato XML
 * 4. exportarCursosDAT() - Exporta cursos a formato DAT
 * 5. exportarTodoXML() - Exporta toda la base de datos (cursos + alumnos) a XML
 * 6. exportarTodoDAT() - Exporta toda la base de datos (cursos + alumnos) a DAT
 * 
 * Uso en examen: demostrar exportación de datos de Firestore a archivos locales
 */
public class ExportController {

	private AlumnosController alumnosCtrl;
	private CursosController cursosCtrl;

	public ExportController() {
		this.alumnosCtrl = new AlumnosController();
		this.cursosCtrl = new CursosController();
	}

	/**
	 * Exportar alumnos a XML.
	 * Formato: estructura XML con etiquetas <alumno>, <nombre>, <edad>, etc.
	 * 
	 * @param nombreArchivo nombre del archivo de salida
	 * @return true si se exportó correctamente
	 */
	public boolean exportarAlumnosXML(String nombreArchivo) {
		try {
			List<Alumnos> alumnos = alumnosCtrl.leerTodos();
			if (alumnos.isEmpty()) {
				System.out.println("[WARN] No hay alumnos para exportar");
				return false;
			}

			Path out = Paths.get(nombreArchivo);
			try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

				// Escribir cabecera XML
				pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				pw.println("<alumnos>");

				// Escribir datos
				for (Alumnos a : alumnos) {
					pw.println("  <alumno>");
					pw.println("    <id>" + xmlEscape(a.getId()) + "</id>");
					pw.println("    <nombre>" + xmlEscape(a.getName()) + "</nombre>");
					pw.println("    <edad>" + a.getAge() + "</edad>");
					pw.println("    <fecha>" + Alumnos.formatDate(a.getBirthDate()) + "</fecha>");
					pw.println("    <idCurso>" + xmlEscape(a.getIdCurso() != null ? a.getIdCurso() : "") + "</idCurso>");
					pw.println("  </alumno>");
				}
				
				pw.println("</alumnos>");
			}

			System.out.println("[INFO] Alumnos exportados a " + out.toAbsolutePath());
			return true;

		} catch (IOException e) {
			System.err.println("[ERROR] Error al exportar alumnos a XML: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exportar alumnos a DAT (formato texto con separador ;). Formato:
	 * id;nombre;edad;dd/MM/yyyy;idCurso
	 * 
	 * @param nombreArchivo nombre del archivo de salida
	 * @return true si se exportó correctamente
	 */
	public boolean exportarAlumnosDAT(String nombreArchivo) {
		try {
			List<Alumnos> alumnos = alumnosCtrl.leerTodos();
			if (alumnos.isEmpty()) {
				System.out.println("[WARN] No hay alumnos para exportar");
				return false;
			}

			Path out = Paths.get(nombreArchivo);
			try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

				// Escribir datos (sin cabecera en DAT)
				for (Alumnos a : alumnos) {
					pw.println(a.toExportLine());
				}
			}

			System.out.println("[INFO] Alumnos exportados a " + out.toAbsolutePath());
			return true;

		} catch (IOException e) {
			System.err.println("[ERROR] Error al exportar alumnos a DAT: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exportar cursos a XML.
	 * Formato: estructura XML con etiquetas <curso>, <nombre>, <descripcion>
	 * 
	 * @param nombreArchivo nombre del archivo de salida
	 * @return true si se exportó correctamente
	 */
	public boolean exportarCursosXML(String nombreArchivo) {
		try {
			List<Curso> cursos = cursosCtrl.leerTodos();
			if (cursos.isEmpty()) {
				System.out.println("[WARN] No hay cursos para exportar");
				return false;
			}

			Path out = Paths.get(nombreArchivo);
			try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

				// Escribir cabecera XML
				pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				pw.println("<cursos>");

				// Escribir datos
				for (Curso c : cursos) {
					pw.println("  <curso>");
					pw.println("    <id>" + xmlEscape(c.getId()) + "</id>");
					pw.println("    <nombre>" + xmlEscape(c.getNombre()) + "</nombre>");
					pw.println("    <descripcion>" + xmlEscape(c.getDescripcion()) + "</descripcion>");
					pw.println("  </curso>");
				}
				
				pw.println("</cursos>");
			}

			System.out.println("[INFO] Cursos exportados a " + out.toAbsolutePath());
			return true;

		} catch (IOException e) {
			System.err.println("[ERROR] Error al exportar cursos a XML: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exportar cursos a DAT (formato texto con separador ;). Formato:
	 * id;nombre;descripcion
	 * 
	 * @param nombreArchivo nombre del archivo de salida
	 * @return true si se exportó correctamente
	 */
	public boolean exportarCursosDAT(String nombreArchivo) {
		try {
			List<Curso> cursos = cursosCtrl.leerTodos();
			if (cursos.isEmpty()) {
				System.out.println("[WARN] No hay cursos para exportar");
				return false;
			}

			Path out = Paths.get(nombreArchivo);
			try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

				// Escribir datos (sin cabecera en DAT)
				for (Curso c : cursos) {
					pw.println(c.toExportLine());
				}
			}

			System.out.println("[INFO] Cursos exportados a " + out.toAbsolutePath());
			return true;

		} catch (IOException e) {
			System.err.println("[ERROR] Error al exportar cursos a DAT: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exportar toda la base de datos (alumnos + cursos) a XML.
	 * Formato: estructura XML con secciones <alumnos> y <cursos>
	 * 
	 * @param nombreArchivo nombre del archivo de salida
	 * @return true si se exportó correctamente
	 */
	public boolean exportarTodoXML(String nombreArchivo) {
		try {
			List<Alumnos> alumnos = alumnosCtrl.leerTodos();
			List<Curso> cursos = cursosCtrl.leerTodos();

			if (alumnos.isEmpty() && cursos.isEmpty()) {
				System.out.println("[WARN] No hay datos para exportar");
				return false;
			}

			Path out = Paths.get(nombreArchivo);
			try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

				// Escribir cabecera XML
				pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				pw.println("<baseDatos>");

				// Sección Cursos
				pw.println("  <cursos>");
				for (Curso c : cursos) {
					pw.println("    <curso>");
					pw.println("      <id>" + xmlEscape(c.getId()) + "</id>");
					pw.println("      <nombre>" + xmlEscape(c.getNombre()) + "</nombre>");
					pw.println("      <descripcion>" + xmlEscape(c.getDescripcion()) + "</descripcion>");
					pw.println("    </curso>");
				}
				pw.println("  </cursos>");

				// Sección Alumnos
				pw.println("  <alumnos>");
				for (Alumnos a : alumnos) {
					pw.println("    <alumno>");
					pw.println("      <id>" + xmlEscape(a.getId()) + "</id>");
					pw.println("      <nombre>" + xmlEscape(a.getName()) + "</nombre>");
					pw.println("      <edad>" + a.getAge() + "</edad>");
					pw.println("      <fecha>" + Alumnos.formatDate(a.getBirthDate()) + "</fecha>");
					pw.println("      <idCurso>" + xmlEscape(a.getIdCurso() != null ? a.getIdCurso() : "") + "</idCurso>");
					pw.println("    </alumno>");
				}
				pw.println("  </alumnos>");

				pw.println("</baseDatos>");
			}

			System.out.println("[INFO] Base de datos completa exportada a " + out.toAbsolutePath());
			System.out.println("       - " + cursos.size() + " cursos");
			System.out.println("       - " + alumnos.size() + " alumnos");
			return true;

		} catch (IOException e) {
			System.err.println("[ERROR] Error al exportar base de datos a XML: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exportar toda la base de datos (alumnos + cursos) a DAT.
	 * Formato: dos secciones separadas con marcadores
	 * 
	 * @param nombreArchivo nombre del archivo de salida
	 * @return true si se exportó correctamente
	 */
	public boolean exportarTodoDAT(String nombreArchivo) {
		try {
			List<Alumnos> alumnos = alumnosCtrl.leerTodos();
			List<Curso> cursos = cursosCtrl.leerTodos();

			if (alumnos.isEmpty() && cursos.isEmpty()) {
				System.out.println("[WARN] No hay datos para exportar");
				return false;
			}

			Path out = Paths.get(nombreArchivo);
			try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

				// Sección Cursos
				pw.println("### CURSOS ###");
				for (Curso c : cursos) {
					pw.println(c.toExportLine());
				}

				pw.println();
				
				// Sección Alumnos
				pw.println("### ALUMNOS ###");
				for (Alumnos a : alumnos) {
					pw.println(a.toExportLine());
				}
			}

			System.out.println("[INFO] Base de datos completa exportada a " + out.toAbsolutePath());
			System.out.println("       - " + cursos.size() + " cursos");
			System.out.println("       - " + alumnos.size() + " alumnos");
			return true;

		} catch (IOException e) {
			System.err.println("[ERROR] Error al exportar base de datos a DAT: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Helper: escapar caracteres especiales XML.
	 */
	private String xmlEscape(String s) {
		if (s == null)
			return "";
		return s.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&apos;");
	}
}
