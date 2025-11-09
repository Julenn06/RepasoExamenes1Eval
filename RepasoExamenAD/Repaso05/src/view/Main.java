package view;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import controller.AlumnosController;
import controller.CursosController;
import controller.DBConnection;
import controller.ExportController;
import model.Alumnos;
import model.Curso;

/**
 * Main - Menú interactivo para gestión completa de Firestore.
 * 
 * Funcionalidades implementadas: 1. Gestión de Alumnos (CRUD completo +
 * búsquedas + filtros) 2. Gestión de Cursos (CRUD completo) 3. Relaciones entre
 * documentos (asignar curso a alumno) 4. Exportación de datos (CSV y DAT) 5.
 * Listados con referencias (alumnos de un curso)
 * 
 * Arquitectura MVC: - Model: Alumnos, Curso (POJOs con getters/setters) - View:
 * Main (interfaz de usuario por consola) - Controller: AlumnosController,
 * CursosController, ExportController, DBConnection
 * 
 * Uso en examen: código completo y comentado para demostrar dominio de
 * Firestore + MVC
 */
public class Main {

	private final Scanner sc;
	private final AlumnosController alumnosCtrl;
	private final CursosController cursosCtrl;
	private final ExportController exportCtrl;

	public Main() {
		this.sc = new Scanner(System.in);
		
		// Inicializar conexión a Firestore
		System.out.println("Conectando a Firestore...");
		DBConnection.initialize();

		if (DBConnection.getFirestore() == null) {
			System.err.println("ERROR: No se pudo conectar a Firestore. Verifica serviceAccountKey.json");
			throw new RuntimeException("Error de conexión a Firestore");
		}

		// Inicializar controllers
		this.alumnosCtrl = new AlumnosController();
		this.cursosCtrl = new CursosController();
		this.exportCtrl = new ExportController();
	}

	public static void main(String[] args) {
		new Main().iniciar();
	}

	public void iniciar() {
		System.out.println("===========================================");
		System.out.println("  SISTEMA DE GESTIÓN FIRESTORE - REPASO05");
		System.out.println("===========================================\n");

		// Menú principal
		boolean running = true;
		while (running) {
			mostrarMenuPrincipal();
			System.out.print("\nElige una opción: ");
			String opcion = sc.nextLine().trim();

			switch (opcion) {
			case "1":
				menuGestionAlumnos();
				break;
			case "2":
				menuGestionCursos();
				break;
			case "3":
				menuRelaciones();
				break;
			case "4":
				menuExportacion();
				break;
			case "5":
				System.out.println("\n¡Hasta luego!");
				running = false;
				break;
			default:
				System.out.println("\n[ERROR] Opción no válida. Intenta de nuevo.");
			}
		}

		sc.close();
	}

	// ==================== MENÚS ====================

	private void mostrarMenuPrincipal() {
		System.out.println("\n╔═══════════════════════════════════════╗");
		System.out.println("║         MENÚ PRINCIPAL                ║");
		System.out.println("╚═══════════════════════════════════════╝");
		System.out.println("1. Gestión de Alumnos");
		System.out.println("2. Gestión de Cursos");
		System.out.println("3. Gestión de Relaciones (Alumno-Curso)");
		System.out.println("4. Exportación de Datos");
		System.out.println("5. Salir");
		System.out.println("───────────────────────────────────────");
	}

	private void menuGestionAlumnos() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════╗");
			System.out.println("║       GESTIÓN DE ALUMNOS              ║");
			System.out.println("╚═══════════════════════════════════════╝");
			System.out.println("1. Crear nuevo alumno");
			System.out.println("2. Listar todos los alumnos");
			System.out.println("3. Buscar alumno por ID");
			System.out.println("4. Buscar alumno por nombre");
			System.out.println("5. Filtrar alumnos por edad");
			System.out.println("6. Filtrar alumnos por curso");
			System.out.println("7. Actualizar alumno");
			System.out.println("8. Eliminar alumno");
			System.out.println("9. Volver al menú principal");
			System.out.println("───────────────────────────────────────");
			System.out.print("Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				crearAlumno();
				break;
			case "2":
				listarAlumnos();
				break;
			case "3":
				buscarAlumnoPorId();
				break;
			case "4":
				buscarAlumnoPorNombre();
				break;
			case "5":
				filtrarAlumnosPorEdad();
				break;
			case "6":
				filtrarAlumnosPorCurso();
				break;
			case "7":
				actualizarAlumno();
				break;
			case "8":
				eliminarAlumno();
				break;
			case "9":
				back = true;
				break;
			default:
				System.out.println("[ERROR] Opción no válida.");
			}
		}
	}

	private void menuGestionCursos() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════╗");
			System.out.println("║        GESTIÓN DE CURSOS              ║");
			System.out.println("╚═══════════════════════════════════════╝");
			System.out.println("1. Crear nuevo curso");
			System.out.println("2. Listar todos los cursos");
			System.out.println("3. Buscar curso por ID");
			System.out.println("4. Actualizar curso");
			System.out.println("5. Eliminar curso");
			System.out.println("6. Volver al menú principal");
			System.out.println("───────────────────────────────────────");
			System.out.print("Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				crearCurso();
				break;
			case "2":
				listarCursos();
				break;
			case "3":
				buscarCursoPorId();
				break;
			case "4":
				actualizarCurso();
				break;
			case "5":
				eliminarCurso();
				break;
			case "6":
				back = true;
				break;
			default:
				System.out.println("[ERROR] Opción no válida.");
			}
		}
	}

	private void menuRelaciones() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════╗");
			System.out.println("║   GESTIÓN DE RELACIONES               ║");
			System.out.println("╚═══════════════════════════════════════╝");
			System.out.println("1. Asignar curso a alumno");
			System.out.println("2. Ver alumnos de un curso");
			System.out.println("3. Eliminar asignación (quitar curso de alumno)");
			System.out.println("4. Volver al menú principal");
			System.out.println("───────────────────────────────────────");
			System.out.print("Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				asignarCursoAAlumno();
				break;
			case "2":
				verAlumnosDeCurso();
				break;
			case "3":
				quitarCursoDeAlumno();
				break;
			case "4":
				back = true;
				break;
			default:
				System.out.println("[ERROR] Opción no válida.");
			}
		}
	}

	private void menuExportacion() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════╗");
			System.out.println("║      EXPORTACIÓN DE DATOS             ║");
			System.out.println("╚═══════════════════════════════════════╝");
			System.out.println("1. Exportar alumnos a XML");
			System.out.println("2. Exportar alumnos a DAT");
			System.out.println("3. Exportar cursos a XML");
			System.out.println("4. Exportar cursos a DAT");
			System.out.println("5. Exportar TODA la base de datos a XML");
			System.out.println("6. Exportar TODA la base de datos a DAT");
			System.out.println("7. Volver al menú principal");
			System.out.println("───────────────────────────────────────");
			System.out.print("Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				exportCtrl.exportarAlumnosXML("alumnos_export.xml");
				break;
			case "2":
				exportCtrl.exportarAlumnosDAT("alumnos_export.dat");
				break;
			case "3":
				exportCtrl.exportarCursosXML("cursos_export.xml");
				break;
			case "4":
				exportCtrl.exportarCursosDAT("cursos_export.dat");
				break;
			case "5":
				exportCtrl.exportarTodoXML("basedatos_completa.xml");
				break;
			case "6":
				exportCtrl.exportarTodoDAT("basedatos_completa.dat");
				break;
			case "7":
				back = true;
				break;
			default:
				System.out.println("[ERROR] Opción no válida.");
			}
		}
	}

	// ==================== OPERACIONES ALUMNOS ====================

	private void crearAlumno() {
		System.out.println("\n--- Crear Nuevo Alumno ---");
		System.out.print("Nombre: ");
		String nombre = sc.nextLine().trim();
		if (nombre.isEmpty()) {
			System.out.println("[ERROR] El nombre no puede estar vacío.");
			return;
		}

		System.out.print("Edad: ");
		int edad;
		try {
			edad = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("[ERROR] Edad inválida.");
			return;
		}

		System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
		String fechaStr = sc.nextLine().trim();
		Date fecha = Alumnos.parseDate(fechaStr);
		if (fecha == null) {
			System.out.println("[ERROR] Fecha inválida. Usa formato dd/MM/yyyy.");
			return;
		}

		System.out.print("ID del curso (opcional, Enter para omitir): ");
		String idCurso = sc.nextLine().trim();

		Alumnos alumno = new Alumnos();
		alumno.setName(nombre);
		alumno.setAge(edad);
		alumno.setBirthDate(fecha);
		alumno.setIdCurso(idCurso.isEmpty() ? null : idCurso);

		String id = alumnosCtrl.crear(alumno);
		if (id != null) {
			System.out.println("✓ Alumno creado exitosamente con ID: " + id);
		}
	}

	private void listarAlumnos() {
		System.out.println("\n--- Lista de Alumnos ---");
		List<Alumnos> alumnos = alumnosCtrl.leerTodos();
		if (alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");
			return;
		}

		for (Alumnos a : alumnos) {
			System.out.println(a);
		}
	}

	private void buscarAlumnoPorId() {
		System.out.print("\nIntroduce el ID del alumno: ");
		String id = sc.nextLine().trim();
		Alumnos alumno = alumnosCtrl.leerPorId(id);
		if (alumno != null) {
			System.out.println("✓ Encontrado: " + alumno);
		} else {
			System.out.println("[INFO] No se encontró alumno con ese ID.");
		}
	}

	private void buscarAlumnoPorNombre() {
		System.out.print("\nIntroduce el nombre (o fragmento): ");
		String nombre = sc.nextLine().trim();
		List<Alumnos> alumnos = alumnosCtrl.leerPorNombre(nombre);
		if (alumnos.isEmpty()) {
			System.out.println("[INFO] No se encontraron coincidencias.");
			return;
		}

		System.out.println("Resultados:");
		for (Alumnos a : alumnos) {
			System.out.println(a);
		}
	}

	private void filtrarAlumnosPorEdad() {
		System.out.print("\nEdad mínima: ");
		int min;
		try {
			min = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("[ERROR] Edad inválida.");
			return;
		}

		System.out.print("Edad máxima: ");
		int max;
		try {
			max = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("[ERROR] Edad inválida.");
			return;
		}

		List<Alumnos> alumnos = alumnosCtrl.filtrarPorEdad(min, max);
		if (alumnos.isEmpty()) {
			System.out.println("[INFO] No se encontraron alumnos en ese rango.");
			return;
		}

		System.out.println("Resultados:");
		for (Alumnos a : alumnos) {
			System.out.println(a);
		}
	}

	private void filtrarAlumnosPorCurso() {
		System.out.print("\nIntroduce el ID del curso: ");
		String idCurso = sc.nextLine().trim();
		List<Alumnos> alumnos = alumnosCtrl.filtrarPorCurso(idCurso);
		if (alumnos.isEmpty()) {
			System.out.println("[INFO] No hay alumnos asignados a ese curso.");
			return;
		}

		System.out.println("Alumnos del curso " + idCurso + ":");
		for (Alumnos a : alumnos) {
			System.out.println(a);
		}
	}

	private void actualizarAlumno() {
		System.out.print("\nIntroduce el ID del alumno a actualizar: ");
		String id = sc.nextLine().trim();
		Alumnos alumno = alumnosCtrl.leerPorId(id);
		if (alumno == null) {
			System.out.println("[ERROR] No se encontró alumno con ese ID.");
			return;
		}

		System.out.println("Alumno actual: " + alumno);
		System.out.println("Deja vacío para mantener el valor actual.\n");

		System.out.print("Nuevo nombre [" + alumno.getName() + "]: ");
		String nombre = sc.nextLine().trim();
		if (!nombre.isEmpty()) {
			alumno.setName(nombre);
		}

		System.out.print("Nueva edad [" + alumno.getAge() + "]: ");
		String edadStr = sc.nextLine().trim();
		if (!edadStr.isEmpty()) {
			try {
				alumno.setAge(Integer.parseInt(edadStr));
			} catch (NumberFormatException e) {
				System.out.println("[WARN] Edad inválida, se mantiene la anterior.");
			}
		}

		System.out.print("Nueva fecha (dd/MM/yyyy) [" + Alumnos.formatDate(alumno.getBirthDate()) + "]: ");
		String fechaStr = sc.nextLine().trim();
		if (!fechaStr.isEmpty()) {
			Date fecha = Alumnos.parseDate(fechaStr);
			if (fecha != null) {
				alumno.setBirthDate(fecha);
			} else {
				System.out.println("[WARN] Fecha inválida, se mantiene la anterior.");
			}
		}

		System.out.print("Nuevo ID de curso [" + (alumno.getIdCurso() != null ? alumno.getIdCurso() : "") + "]: ");
		String curso = sc.nextLine().trim();
		if (!curso.isEmpty()) {
			alumno.setIdCurso(curso);
		}

		if (alumnosCtrl.actualizar(alumno)) {
			System.out.println("✓ Alumno actualizado exitosamente.");
		}
	}

	private void eliminarAlumno() {
		System.out.print("\nIntroduce el ID del alumno a eliminar: ");
		String id = sc.nextLine().trim();
		System.out.print("¿Estás seguro? (s/n): ");
		String confirm = sc.nextLine().trim().toLowerCase();
		if (!confirm.equals("s")) {
			System.out.println("Operación cancelada.");
			return;
		}

		if (alumnosCtrl.eliminar(id)) {
			System.out.println("✓ Alumno eliminado exitosamente.");
		}
	}

	// ==================== OPERACIONES CURSOS ====================

	private void crearCurso() {
		System.out.println("\n--- Crear Nuevo Curso ---");
		System.out.print("Nombre del curso: ");
		String nombre = sc.nextLine().trim();
		if (nombre.isEmpty()) {
			System.out.println("[ERROR] El nombre no puede estar vacío.");
			return;
		}

		System.out.print("Descripción: ");
		String descripcion = sc.nextLine().trim();

		Curso curso = new Curso();
		curso.setNombre(nombre);
		curso.setDescripcion(descripcion);

		String id = cursosCtrl.crear(curso);
		if (id != null) {
			System.out.println("✓ Curso creado exitosamente con ID: " + id);
		}
	}

	private void listarCursos() {
		System.out.println("\n--- Lista de Cursos ---");
		List<Curso> cursos = cursosCtrl.leerTodos();
		if (cursos.isEmpty()) {
			System.out.println("No hay cursos registrados.");
			return;
		}

		for (Curso c : cursos) {
			System.out.println(c);
		}
	}

	private void buscarCursoPorId() {
		System.out.print("\nIntroduce el ID del curso: ");
		String id = sc.nextLine().trim();
		Curso curso = cursosCtrl.leerPorId(id);
		if (curso != null) {
			System.out.println("✓ Encontrado: " + curso);
		} else {
			System.out.println("[INFO] No se encontró curso con ese ID.");
		}
	}

	private void actualizarCurso() {
		System.out.print("\nIntroduce el ID del curso a actualizar: ");
		String id = sc.nextLine().trim();
		Curso curso = cursosCtrl.leerPorId(id);
		if (curso == null) {
			System.out.println("[ERROR] No se encontró curso con ese ID.");
			return;
		}

		System.out.println("Curso actual: " + curso);
		System.out.println("Deja vacío para mantener el valor actual.\n");

		System.out.print("Nuevo nombre [" + curso.getNombre() + "]: ");
		String nombre = sc.nextLine().trim();
		if (!nombre.isEmpty()) {
			curso.setNombre(nombre);
		}

		System.out.print("Nueva descripción [" + curso.getDescripcion() + "]: ");
		String desc = sc.nextLine().trim();
		if (!desc.isEmpty()) {
			curso.setDescripcion(desc);
		}

		if (cursosCtrl.actualizar(curso)) {
			System.out.println("✓ Curso actualizado exitosamente.");
		}
	}

	private void eliminarCurso() {
		System.out.print("\nIntroduce el ID del curso a eliminar: ");
		String id = sc.nextLine().trim();
		System.out.print("¿Estás seguro? (s/n): ");
		String confirm = sc.nextLine().trim().toLowerCase();
		if (!confirm.equals("s")) {
			System.out.println("Operación cancelada.");
			return;
		}

		if (cursosCtrl.eliminar(id)) {
			System.out.println("✓ Curso eliminado exitosamente.");
		}
	}

	// ==================== OPERACIONES RELACIONES ====================

	private void asignarCursoAAlumno() {
		System.out.print("\nIntroduce el ID del alumno: ");
		String idAlumno = sc.nextLine().trim();
		Alumnos alumno = alumnosCtrl.leerPorId(idAlumno);
		if (alumno == null) {
			System.out.println("[ERROR] No se encontró alumno con ese ID.");
			return;
		}

		System.out.print("Introduce el ID del curso: ");
		String idCurso = sc.nextLine().trim();
		Curso curso = cursosCtrl.leerPorId(idCurso);
		if (curso == null) {
			System.out.println("[ERROR] No se encontró curso con ese ID.");
			return;
		}

		alumno.setIdCurso(idCurso);
		if (alumnosCtrl.actualizar(alumno)) {
			System.out.println("✓ Curso '" + curso.getNombre() + "' asignado a " + alumno.getName());
		}
	}

	private void verAlumnosDeCurso() {
		System.out.print("\nIntroduce el ID del curso: ");
		String idCurso = sc.nextLine().trim();
		Curso curso = cursosCtrl.leerPorId(idCurso);
		if (curso == null) {
			System.out.println("[ERROR] No se encontró curso con ese ID.");
			return;
		}

		System.out.println("\nCurso: " + curso.getNombre());
		System.out.println("Alumnos matriculados:");
		List<Alumnos> alumnos = alumnosCtrl.filtrarPorCurso(idCurso);
		if (alumnos.isEmpty()) {
			System.out.println("  (ninguno)");
			return;
		}

		for (Alumnos a : alumnos) {
			System.out.println("  - " + a.getName() + " (ID: " + a.getId() + ")");
		}
	}

	private void quitarCursoDeAlumno() {
		System.out.print("\nIntroduce el ID del alumno: ");
		String idAlumno = sc.nextLine().trim();
		Alumnos alumno = alumnosCtrl.leerPorId(idAlumno);
		if (alumno == null) {
			System.out.println("[ERROR] No se encontró alumno con ese ID.");
			return;
		}

		if (alumno.getIdCurso() == null || alumno.getIdCurso().isEmpty()) {
			System.out.println("[INFO] Este alumno no tiene curso asignado.");
			return;
		}

		alumno.setIdCurso(null);
		if (alumnosCtrl.actualizar(alumno)) {
			System.out.println("✓ Curso eliminado del alumno " + alumno.getName());
		}
	}
}
