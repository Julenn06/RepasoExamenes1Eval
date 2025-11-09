package controller;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import model.Alumnos;

/**
 * Controlador para gestión de Alumnos en archivos XML Implementa CRUD completo
 * usando DOM parsing
 */
public class AlumnoController {

	private static final Path XML_PATH = Paths.get("alumnos.xml");
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	/**
	 * Lee todos los alumnos del archivo XML
	 */
	public List<Alumnos> leerTodos() {
		List<Alumnos> alumnos = new ArrayList<>();
		try {
			if (!Files.exists(XML_PATH)) {
				System.out.println("⚠ Archivo XML no existe, se creará al agregar datos.");
				return alumnos;
			}

			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(XML_PATH.toFile());
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("alumno");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element elem = (Element) nodeList.item(i);
				String nombre = getElementText(elem, "nombre");
				int edad = Integer.parseInt(getElementText(elem, "edad"));
				Date fechaInscripcion = SDF.parse(getElementText(elem, "fechaInscripcion"));
				alumnos.add(new Alumnos(nombre, edad, fechaInscripcion));
			}
			System.out.println("✓ Se encontraron " + alumnos.size() + " alumnos");
		} catch (Exception e) {
			System.err.println("✗ Error al leer XML: " + e.getMessage());
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
			System.out.println("✗ No hay alumnos en el rango " + edadMin + "-" + edadMax);
		} else {
			System.out.println("✓ Se encontraron " + resultados.size() + " alumno(s)");
		}
		return resultados;
	}

	/**
	 * Crea un nuevo alumno en el XML
	 */
	public boolean crear(Alumnos alumno) {
		try {
			List<Alumnos> alumnos = leerTodos();

			// Verificar duplicados
			for (Alumnos a : alumnos) {
				if (a.getNombre().equalsIgnoreCase(alumno.getNombre())) {
					System.out.println("✗ Ya existe un alumno con ese nombre");
					return false;
				}
			}

			alumnos.add(alumno);
			guardarTodos(alumnos);
			System.out.println("✓ Alumno agregado: " + alumno.getNombre());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al crear alumno: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Actualiza un alumno existente
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
				System.out.println("✗ No se encontró el alumno: " + nombreViejo);
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
			Iterator<Alumnos> it = todos.iterator();
			boolean eliminado = false;

			while (it.hasNext()) {
				Alumnos a = it.next();
				if (a.getNombre().equalsIgnoreCase(nombre)) {
					it.remove();
					eliminado = true;
				}
			}

			if (eliminado) {
				guardarTodos(todos);
				System.out.println("✓ Alumno(s) eliminado(s): " + nombre);
				return true;
			} else {
				System.out.println("✗ No se encontró el alumno: " + nombre);
				return false;
			}
		} catch (Exception e) {
			System.err.println("✗ Error al eliminar: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exporta alumnos a formato CSV
	 */
	public boolean exportarCSV(String nombreArchivo) {
		try {
			List<Alumnos> alumnos = leerTodos();
			if (alumnos.isEmpty()) {
				System.out.println("⚠ No hay datos para exportar");
				return false;
			}

			Path path = Paths.get(nombreArchivo);
			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
				writer.println("nombre,edad,fechaInscripcion");
				for (Alumnos a : alumnos) {
					writer.println(String.format("%s,%d,%s", escapeCsv(a.getNombre()), a.getEdad(),
							SDF.format(a.getFechaInscripcion())));
				}
			}
			System.out.println("✓ Exportado a: " + path.toAbsolutePath());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al exportar CSV: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Exporta alumnos a formato DAT (texto plano)
	 */
	public boolean exportarDAT(String nombreArchivo) {
		try {
			List<Alumnos> alumnos = leerTodos();
			if (alumnos.isEmpty()) {
				System.out.println("⚠ No hay datos para exportar");
				return false;
			}

			Path path = Paths.get(nombreArchivo);
			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
				for (Alumnos a : alumnos) {
					writer.println(formatAlumno(a));
				}
			}
			System.out.println("✓ Exportado a: " + path.toAbsolutePath());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al exportar DAT: " + e.getMessage());
			return false;
		}
	}

	// ===== MÉTODOS PRIVADOS =====

	/**
	 * Guarda la lista completa de alumnos en el XML
	 */
	private void guardarTodos(List<Alumnos> alumnos) throws Exception {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.newDocument();

		Element rootElement = doc.createElement("alumnos");
		doc.appendChild(rootElement);

		for (Alumnos a : alumnos) {
			Element alumnoElem = doc.createElement("alumno");

			Element nombreElem = doc.createElement("nombre");
			nombreElem.setTextContent(a.getNombre());
			alumnoElem.appendChild(nombreElem);

			Element edadElem = doc.createElement("edad");
			edadElem.setTextContent(String.valueOf(a.getEdad()));
			alumnoElem.appendChild(edadElem);

			Element fechaElem = doc.createElement("fechaInscripcion");
			fechaElem.setTextContent(SDF.format(a.getFechaInscripcion()));
			alumnoElem.appendChild(fechaElem);

			rootElement.appendChild(alumnoElem);
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(XML_PATH.toFile());
		transformer.transform(source, result);
	}

	/**
	 * Obtiene el texto de un elemento hijo
	 */
	private String getElementText(Element parent, String tagName) {
		NodeList nl = parent.getElementsByTagName(tagName);
		if (nl.getLength() > 0) {
			return nl.item(0).getTextContent();
		}
		return "";
	}

	/**
	 * Formatea un alumno como string (formato DAT)
	 */
	private String formatAlumno(Alumnos a) {
		return String.format("%s;%d;%s", a.getNombre(), a.getEdad(), SDF.format(a.getFechaInscripcion()));
	}

	/**
	 * Escapa caracteres especiales para CSV
	 */
	private String escapeCsv(String s) {
		if (s == null)
			return "";
		String result = s.replace("\"", "\"\"");
		if (result.contains(",") || result.contains("\n") || result.contains("\r")) {
			result = "\"" + result + "\"";
		}
		return result;
	}
}
