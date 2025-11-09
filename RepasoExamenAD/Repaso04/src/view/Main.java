package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import model.Alumnos;

public class Main {

	private static final Path XML_PATH = Paths.get("alumnos.xml");
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean running = true;

		while (running) {
			mostrarMenu();
			System.out.print("Elige una opción: ");
			String opcion = sc.nextLine().trim();

			switch (opcion) {
			case "1":
				leerDatos();
				break;
			case "2":
				leerPorID(sc);
				break;
			case "3":
				leerPorNombre(sc);
				break;
			case "4":
				filtrarPorEdad(sc);
				break;
			case "5":
				agregarRegistro(sc);
				break;
			case "6":
				editarRegistro(sc);
				break;
			case "7":
				eliminarRegistro(sc);
				break;
			case "8":
				exportarDatos(sc);
				break;
			case "9":
				System.out.println("Saliendo...");
				running = false;
				break;
			default:
				System.out.println("Opción no válida. Intenta de nuevo.");
			}

			System.out.println();
		}

		sc.close();
	}

	private static void mostrarMenu() {
		System.out.println("\nGestión de datos (XML / filtros / export)");
		System.out.println("----------------------------------------");
		System.out.println("1. Leer todos los datos");
		System.out.println("2. Leer por ID (nombre exacto)");
		System.out.println("3. Leer por nombre (búsqueda parcial)");
		System.out.println("4. Filtrar por rango de edad");
		System.out.println("5. Agregar nuevo registro");
		System.out.println("6. Editar registro (por nombre)");
		System.out.println("7. Eliminar registro (por nombre)");
		System.out.println("8. Exportar datos (CSV / DAT)");
		System.out.println("9. Salir");
		System.out.println("----------------------------------------");
	}

	// ---------- Operaciones XML ----------
	private static void leerDatos() {
		List<Alumnos> list = readAllFromXml();
		if (list.isEmpty()) {
			System.out.println("No hay registros en " + XML_PATH);
			return;
		}
		System.out.println("Registros (XML - nombre;edad;dd/MM/yyyy):");
		for (Alumnos a : list) {
			System.out.println(formatAlumnoLine(a));
		}
	}

	private static void leerPorID(Scanner sc) {
		System.out.print("Introduce el nombre (ID) exacto: ");
		String id = sc.nextLine().trim();
		if (id.isEmpty()) {
			System.out.println("Nombre vacío.");
			return;
		}
		List<Alumnos> list = readAllFromXml();
		for (Alumnos a : list) {
			if (a.getName() != null && a.getName().equalsIgnoreCase(id)) {
				System.out.println("Encontrado: " + formatAlumnoLine(a));
				return;
			}
		}
		System.out.println("No se encontró ningún registro con nombre '" + id + "'.");
	}

	private static void leerPorNombre(Scanner sc) {
		System.out.print("Introduce nombre o fragmento a buscar: ");
		String q = sc.nextLine().trim().toLowerCase();
		if (q.isEmpty()) {
			System.out.println("Consulta vacía.");
			return;
		}
		List<Alumnos> list = readAllFromXml();
		boolean any = false;
		for (Alumnos a : list) {
			if (a.getName() != null && a.getName().toLowerCase().contains(q)) {
				System.out.println(formatAlumnoLine(a));
				any = true;
			}
		}
		if (!any)
			System.out.println("No hay coincidencias para '" + q + "'.");
	}

	private static void filtrarPorEdad(Scanner sc) {
		try {
			System.out.print("Edad mínima: ");
			int min = Integer.parseInt(sc.nextLine().trim());
			System.out.print("Edad máxima: ");
			int max = Integer.parseInt(sc.nextLine().trim());
			if (min > max) {
				System.out.println("Rango inválido: la mínima es mayor que la máxima.");
				return;
			}
			List<Alumnos> list = readAllFromXml();
			boolean any = false;
			for (Alumnos a : list) {
				if (a.getAge() >= min && a.getAge() <= max) {
					System.out.println(formatAlumnoLine(a));
					any = true;
				}
			}
			if (!any)
				System.out.println("No se encontraron alumnos en el rango " + min + " - " + max + ".");
		} catch (NumberFormatException e) {
			System.out.println("Entrada no válida para edad. Vuelve a intentarlo.");
		}
	}

	private static void agregarRegistro(Scanner sc) {
		try {
			System.out.print("Nombre: ");
			String name = sc.nextLine().trim();
			if (name.isEmpty()) {
				System.out.println("El nombre no puede quedar vacío.");
				return;
			}
			if (name.contains(";")) {
				System.out.println("El carácter ';' no está permitido en el nombre.");
				return;
			}
			System.out.print("Edad: ");
			int age = Integer.parseInt(sc.nextLine().trim());
			System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
			String fecha = sc.nextLine().trim();
			Date bd = parseDateOrNull(fecha);
			if (bd == null) {
				System.out.println("Formato de fecha inválido. Usa dd/MM/yyyy.");
				return;
			}

			Alumnos a = new Alumnos();
			a.setName(name);
			a.setAge(age);
			a.setBirthDate(bd);

			appendAlumnoToXml(a);
			System.out.println("Registro agregado: " + formatAlumnoLine(a));
		} catch (NumberFormatException e) {
			System.out.println("Edad no válida.");
		} catch (Exception e) {
			System.out.println("Error al guardar: " + e.getMessage());
		}
	}

	private static void editarRegistro(Scanner sc) {
		System.out.print("Nombre del registro a editar: ");
		String name = sc.nextLine().trim();
		if (name.isEmpty()) {
			System.out.println("Nombre vacío.");
			return;
		}
		List<Alumnos> list = readAllFromXml();
		Alumnos found = null;
		for (Alumnos a : list) {
			if (a.getName() != null && a.getName().equalsIgnoreCase(name)) {
				found = a;
				break;
			}
		}
		if (found == null) {
			System.out.println("No se encontró el registro con nombre '" + name + "'.");
			return;
		}

		System.out.println("Dejar campo vacío mantiene el valor actual.");
		System.out.print("Nuevo nombre [" + found.getName() + "]: ");
		String nn = sc.nextLine().trim();
		if (!nn.isEmpty()) {
			if (nn.contains(";")) {
				System.out.println("El carácter ';' no está permitido en el nombre.");
				return;
			}
			found.setName(nn);
		}
		System.out.print("Nueva edad [" + found.getAge() + "]: ");
		String ea = sc.nextLine().trim();
		if (!ea.isEmpty()) {
			try {
				found.setAge(Integer.parseInt(ea));
			} catch (NumberFormatException e) {
				System.out.println("Edad no válida. Edición cancelada.");
				return;
			}
		}
		System.out.print("Nueva fecha (dd/MM/yyyy) ["
				+ (found.getBirthDate() == null ? "" : SDF.format(found.getBirthDate())) + "]: ");
		String fd = sc.nextLine().trim();
		if (!fd.isEmpty()) {
			Date d = parseDateOrNull(fd);
			if (d == null) {
				System.out.println("Fecha inválida. Edición cancelada.");
				return;
			}
			found.setBirthDate(d);
		}

		try {
			writeAllToXml(list);
			System.out.println("Registro actualizado: " + formatAlumnoLine(found));
		} catch (Exception e) {
			System.out.println("Error al guardar cambios: " + e.getMessage());
		}
	}

	private static void eliminarRegistro(Scanner sc) {
		System.out.print("Nombre del registro a eliminar: ");
		String name = sc.nextLine().trim();
		if (name.isEmpty()) {
			System.out.println("Nombre vacío.");
			return;
		}
		List<Alumnos> list = readAllFromXml();
		boolean removed = false;
		Iterator<Alumnos> it = list.iterator();
		while (it.hasNext()) {
			Alumnos a = it.next();
			if (a.getName() != null && a.getName().equalsIgnoreCase(name)) {
				it.remove();
				removed = true;
			}
		}
		if (!removed) {
			System.out.println("No se encontró ningún registro con nombre '" + name + "'.");
			return;
		}
		try {
			writeAllToXml(list);
			System.out.println("Registro(s) eliminado(s) correctamente.");
		} catch (Exception e) {
			System.out.println("Error al guardar después de eliminar: " + e.getMessage());
		}
	}

	private static void exportarDatos(Scanner sc) {
		System.out.println("Formatos disponibles: 1) CSV  2) DAT (texto)");
		System.out.print("Elige formato (1-2): ");
		String opt = sc.nextLine().trim();
		try {
			switch (opt) {
			case "1":
				exportCSV();
				break;
			case "2":
				exportDAT();
				break;
			default:
				System.out.println("Opción de exportación no válida.");
			}
		} catch (IOException e) {
			System.out.println("Error en exportación: " + e.getMessage());
		}
	}

	// ---------- XML helpers ----------
	private static List<Alumnos> readAllFromXml() {
		List<Alumnos> out = new ArrayList<>();
		if (!Files.exists(XML_PATH))
			return out;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(XML_PATH.toFile());
			NodeList nodes = doc.getElementsByTagName("alumno");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element el = (Element) nodes.item(i);
				String nombre = getTextContent(el, "nombre");
				String edadS = getTextContent(el, "edad");
				String fecha = getTextContent(el, "fecha");
				Alumnos a = new Alumnos();
				a.setName(nombre);
				try {
					a.setAge(edadS == null || edadS.isEmpty() ? 0 : Integer.parseInt(edadS));
				} catch (NumberFormatException e) {
					a.setAge(0);
				}
				if (fecha != null && !fecha.isEmpty()) {
					a.setBirthDate(parseDateOrNull(fecha));
				}
				out.add(a);
			}
		} catch (Exception e) {
			System.out.println("Error leyendo XML: " + e.getMessage());
		}
		return out;
	}

	private static String getTextContent(Element parent, String tag) {
		NodeList nl = parent.getElementsByTagName(tag);
		if (nl.getLength() == 0)
			return "";
		return nl.item(0).getTextContent();
	}

	private static void writeAllToXml(List<Alumnos> list)
			throws ParserConfigurationException, TransformerException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("alumnos");
		doc.appendChild(root);
		for (Alumnos a : list) {
			Element al = doc.createElement("alumno");
			Element nm = doc.createElement("nombre");
			nm.setTextContent(a.getName() == null ? "" : a.getName());
			Element ed = doc.createElement("edad");
			ed.setTextContent(String.valueOf(a.getAge()));
			Element fd = doc.createElement("fecha");
			fd.setTextContent(a.getBirthDate() == null ? "" : SDF.format(a.getBirthDate()));
			al.appendChild(nm);
			al.appendChild(ed);
			al.appendChild(fd);
			root.appendChild(al);
		}
		// write to temp then move
		Path tmp = Paths.get(XML_PATH.toString() + ".tmp");
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(tmp, StandardCharsets.UTF_8,
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {
			t.transform(new DOMSource(doc), new StreamResult(pw));
		}
		Files.move(tmp, XML_PATH, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
	}

	private static void appendAlumnoToXml(Alumnos a) throws Exception {
		List<Alumnos> list = readAllFromXml();
		list.add(a);
		writeAllToXml(list);
	}

	private static String formatAlumnoLine(Alumnos a) {
		String fecha = a.getBirthDate() == null ? "" : SDF.format(a.getBirthDate());
		return (a.getName() == null ? "" : a.getName()) + ";" + a.getAge() + ";" + fecha;
	}

	private static Date parseDateOrNull(String s) {
		try {
			return SDF.parse(s);
		} catch (ParseException e) {
			return null;
		}
	}

	private static void exportCSV() throws IOException {
		List<Alumnos> list = readAllFromXml();
		if (list.isEmpty()) {
			System.out.println("No hay datos para exportar.");
			return;
		}
		Path out = Paths.get("alumnos_xml_export.csv");
		try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8,
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {
			pw.println("nombre,edad,fecha");
			for (Alumnos a : list) {
				String f = a.getBirthDate() == null ? "" : SDF.format(a.getBirthDate());
				pw.println(escapeCsv(a.getName()) + "," + a.getAge() + "," + escapeCsv(f));
			}
		}
		System.out.println("Exportado a " + out.toAbsolutePath());
	}

	private static void exportDAT() throws IOException {
		List<Alumnos> list = readAllFromXml();
		if (list.isEmpty()) {
			System.out.println("No hay datos para exportar.");
			return;
		}
		Path out = Paths.get("alumnos_xml_export.dat");
		try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8,
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {
			for (Alumnos a : list) {
				pw.println(formatAlumnoLine(a));
			}
		}
		System.out.println("Exportado a " + out.toAbsolutePath());
	}

	private static String escapeCsv(String s) {
		if (s == null)
			return "";
		String r = s.replace("\"", "\"\"");
		if (r.contains(",") || r.contains("\n") || r.contains("\r"))
			r = "\"" + r + "\"";
		return r;
	}

}
