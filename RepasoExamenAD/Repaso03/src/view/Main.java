package view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import controller.AlumnoController;
import model.Alumnos;

/**
 * Vista (UI) para gestión de Alumnos con archivos DAT Arquitectura MVC - Solo
 * maneja interfaz de usuario
 */
public class Main {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	private static AlumnoController alumnoCtrl = new AlumnoController();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean running = true;

		System.out.println("╔════════════════════════════════════════════════╗");
		System.out.println("║  SISTEMA DE GESTIÓN DE ALUMNOS - REPASO03     ║");
		System.out.println("║  Arquitectura MVC con archivos DAT (texto)     ║");
		System.out.println("╚════════════════════════════════════════════════╝\n");

		while (running) {
			mostrarMenu();
			System.out.print("➤ Elige una opción: ");
			String opcion = sc.nextLine().trim();

			switch (opcion) {
			case "1":
				leerTodos();
				break;
			case "2":
				buscarPorNombre(sc);
				break;
			case "3":
				buscarPorNombreParcial(sc);
				break;
			case "4":
				filtrarPorEdad(sc);
				break;
			case "5":
				agregarAlumno(sc);
				break;
			case "6":
				editarAlumno(sc);
				break;
			case "7":
				eliminarAlumno(sc);
				break;
			case "8":
				exportarDatos(sc);
				break;
			case "9":
				System.out.println("\n👋 ¡Hasta luego!");
				running = false;
				break;
			default:
				System.out.println("❌ Opción no válida. Intenta de nuevo.");
			}

			if (running) {
				System.out.println("\n" + "─".repeat(50));
			}
		}

		sc.close();
	}

	private static void mostrarMenu() {
		System.out.println("\n╔════════════════════════════════════════════════╗");
		System.out.println("║         GESTIÓN DE ALUMNOS (.DAT)              ║");
		System.out.println("╚════════════════════════════════════════════════╝");
		System.out.println("1. 📋 Ver todos los alumnos");
		System.out.println("2. 🔍 Buscar por nombre exacto");
		System.out.println("3. 🔎 Buscar por nombre (parcial)");
		System.out.println("4. 🎂 Filtrar por rango de edad");
		System.out.println("5. ➕ Agregar nuevo alumno");
		System.out.println("6. ✏️  Editar alumno");
		System.out.println("7. ❌ Eliminar alumno");
		System.out.println("8. 📤 Exportar datos (CSV/XML/DAT)");
		System.out.println("9. 🚪 Salir");
		System.out.println("─".repeat(50));
	}

	private static void leerTodos() {
		System.out.println("\n📋 LISTADO COMPLETO DE ALUMNOS");
		System.out.println("═".repeat(50));

		List<Alumnos> alumnos = alumnoCtrl.leerTodos();
		if (alumnos.isEmpty()) {
			System.out.println("⚠️  No hay alumnos registrados.");
			return;
		}

		for (int i = 0; i < alumnos.size(); i++) {
			Alumnos al = alumnos.get(i);
			System.out.printf("\n[%d] 👤 %s\n", (i + 1), al.getNombre());
			System.out.printf("    🎂 Edad: %d años\n", al.getEdad());
			System.out.printf("    📅 Fecha inscripción: %s\n", SDF.format(al.getFechaInscripcion()));
		}
		System.out.println("\n✅ Total: " + alumnos.size() + " alumnos");
	}

	private static void buscarPorNombre(Scanner sc) {
		System.out.print("\n🔍 Introduce el nombre exacto del alumno: ");
		String nombre = sc.nextLine().trim();

		Alumnos alumno = alumnoCtrl.buscarPorNombre(nombre);
		if (alumno != null) {
			System.out.println("\n✅ ALUMNO ENCONTRADO:");
			System.out.println("═".repeat(50));
			System.out.printf("👤 Nombre: %s\n", alumno.getNombre());
			System.out.printf("🎂 Edad: %d años\n", alumno.getEdad());
			System.out.printf("📅 Fecha inscripción: %s\n", SDF.format(alumno.getFechaInscripcion()));
		} else {
			System.out.println("❌ No se encontró ningún alumno con ese nombre.");
		}
	}

	private static void buscarPorNombreParcial(Scanner sc) {
		System.out.print("\n🔎 Introduce parte del nombre: ");
		String parte = sc.nextLine().trim();

		List<Alumnos> resultados = alumnoCtrl.buscarPorNombreParcial(parte);
		if (resultados.isEmpty()) {
			System.out.println("❌ No se encontraron alumnos que coincidan con '" + parte + "'.");
			return;
		}

		System.out.println("\n✅ RESULTADOS DE BÚSQUEDA: " + resultados.size() + " alumno(s)");
		System.out.println("═".repeat(50));
		for (int i = 0; i < resultados.size(); i++) {
			Alumnos al = resultados.get(i);
			System.out.printf("\n[%d] 👤 %s\n", (i + 1), al.getNombre());
			System.out.printf("    🎂 Edad: %d años\n", al.getEdad());
			System.out.printf("    📅 Fecha inscripción: %s\n", SDF.format(al.getFechaInscripcion()));
		}
	}

	private static void filtrarPorEdad(Scanner sc) {
		System.out.print("\n🎂 Introduce edad mínima: ");
		int edadMin = Integer.parseInt(sc.nextLine().trim());
		System.out.print("🎂 Introduce edad máxima: ");
		int edadMax = Integer.parseInt(sc.nextLine().trim());

		List<Alumnos> resultados = alumnoCtrl.filtrarPorEdad(edadMin, edadMax);
		if (resultados.isEmpty()) {
			System.out.println("❌ No hay alumnos en el rango de edad " + edadMin + "-" + edadMax + " años.");
			return;
		}

		System.out.printf("\n✅ ALUMNOS CON EDAD ENTRE %d Y %d AÑOS: %d alumno(s)\n", edadMin, edadMax,
				resultados.size());
		System.out.println("═".repeat(50));
		for (int i = 0; i < resultados.size(); i++) {
			Alumnos al = resultados.get(i);
			System.out.printf("\n[%d] 👤 %s\n", (i + 1), al.getNombre());
			System.out.printf("    🎂 Edad: %d años\n", al.getEdad());
			System.out.printf("    📅 Fecha inscripción: %s\n", SDF.format(al.getFechaInscripcion()));
		}
	}

	private static void agregarAlumno(Scanner sc) {
		System.out.println("\n➕ AGREGAR NUEVO ALUMNO");
		System.out.println("═".repeat(50));

		System.out.print("👤 Nombre completo: ");
		String nombre = sc.nextLine().trim();

		System.out.print("🎂 Edad: ");
		int edad = Integer.parseInt(sc.nextLine().trim());

		System.out.print("📅 Fecha inscripción (dd/MM/yyyy): ");
		String fechaStr = sc.nextLine().trim();
		Date fecha;
		try {
			fecha = SDF.parse(fechaStr);
		} catch (Exception e) {
			System.out.println("❌ Formato de fecha incorrecto.");
			return;
		}

		Alumnos nuevoAlumno = new Alumnos(nombre, edad, fecha);
		if (alumnoCtrl.crear(nuevoAlumno)) {
			System.out.println("✅ Alumno agregado correctamente.");
		} else {
			System.out.println("❌ Error al agregar el alumno.");
		}
	}

	private static void editarAlumno(Scanner sc) {
		System.out.println("\n✏️  EDITAR ALUMNO");
		System.out.println("═".repeat(50));

		System.out.print("🔍 Nombre del alumno a editar: ");
		String nombreViejo = sc.nextLine().trim();

		Alumnos alumno = alumnoCtrl.buscarPorNombre(nombreViejo);
		if (alumno == null) {
			System.out.println("❌ No se encontró ningún alumno con ese nombre.");
			return;
		}

		System.out.println("\n📝 Datos actuales:");
		System.out.printf("   👤 Nombre: %s\n", alumno.getNombre());
		System.out.printf("   🎂 Edad: %d años\n", alumno.getEdad());
		System.out.printf("   📅 Fecha inscripción: %s\n", SDF.format(alumno.getFechaInscripcion()));

		System.out.print("\n👤 Nuevo nombre (Enter para mantener): ");
		String nuevoNombre = sc.nextLine().trim();
		if (!nuevoNombre.isEmpty()) {
			alumno.setNombre(nuevoNombre);
		}

		System.out.print("🎂 Nueva edad (Enter para mantener): ");
		String edadStr = sc.nextLine().trim();
		if (!edadStr.isEmpty()) {
			alumno.setEdad(Integer.parseInt(edadStr));
		}

		System.out.print("📅 Nueva fecha (dd/MM/yyyy, Enter para mantener): ");
		String fechaStr = sc.nextLine().trim();
		if (!fechaStr.isEmpty()) {
			try {
				alumno.setFechaInscripcion(SDF.parse(fechaStr));
			} catch (Exception e) {
				System.out.println("⚠️  Formato de fecha incorrecto, se mantiene la fecha actual.");
			}
		}

		if (alumnoCtrl.actualizar(nombreViejo, alumno)) {
			System.out.println("✅ Alumno actualizado correctamente.");
		} else {
			System.out.println("❌ Error al actualizar el alumno.");
		}
	}

	private static void eliminarAlumno(Scanner sc) {
		System.out.println("\n❌ ELIMINAR ALUMNO");
		System.out.println("═".repeat(50));

		System.out.print("🔍 Nombre del alumno a eliminar: ");
		String nombre = sc.nextLine().trim();

		Alumnos alumno = alumnoCtrl.buscarPorNombre(nombre);
		if (alumno == null) {
			System.out.println("❌ No se encontró ningún alumno con ese nombre.");
			return;
		}

		System.out.println("\n📝 Datos del alumno:");
		System.out.printf("   👤 Nombre: %s\n", alumno.getNombre());
		System.out.printf("   🎂 Edad: %d años\n", alumno.getEdad());
		System.out.printf("   📅 Fecha inscripción: %s\n", SDF.format(alumno.getFechaInscripcion()));

		System.out.print("\n⚠️  ¿Confirmas la eliminación? (S/N): ");
		String confirmacion = sc.nextLine().trim().toUpperCase();

		if (confirmacion.equals("S") || confirmacion.equals("SI")) {
			if (alumnoCtrl.eliminar(nombre)) {
				System.out.println("✅ Alumno eliminado correctamente.");
			} else {
				System.out.println("❌ Error al eliminar el alumno.");
			}
		} else {
			System.out.println("❌ Operación cancelada.");
		}
	}

	private static void exportarDatos(Scanner sc) {
		System.out.println("\n📤 EXPORTAR DATOS");
		System.out.println("═".repeat(50));
		System.out.println("1. 📄 Exportar a CSV");
		System.out.println("2. 📋 Exportar a XML");
		System.out.println("3. 💾 Exportar a DAT (copia)");
		System.out.print("➤ Elige formato: ");
		String opcion = sc.nextLine().trim();

		switch (opcion) {
		case "1":
			if (alumnoCtrl.exportarCSV("alumnos.csv")) {
				System.out.println("✅ Datos exportados a 'alumnos.csv'");
			} else {
				System.out.println("❌ Error al exportar a CSV.");
			}
			break;
		case "2":
			if (alumnoCtrl.exportarXML("alumnos.xml")) {
				System.out.println("✅ Datos exportados a 'alumnos.xml'");
			} else {
				System.out.println("❌ Error al exportar a XML.");
			}
			break;
		case "3":
			if (alumnoCtrl.exportarDAT("alumnos_backup.dat")) {
				System.out.println("✅ Datos exportados a 'alumnos_backup.dat'");
			} else {
				System.out.println("❌ Error al crear copia de seguridad.");
			}
			break;
		default:
			System.out.println("❌ Opción no válida.");
		}
	}
}
