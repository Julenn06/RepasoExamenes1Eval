package view;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import controller.*;
import model.*;

/**
 * Menú principal del sistema académico super completo Repaso06 con TODO lo
 * necesario para el examen
 */
public class Main {

	private final Scanner sc;
	private final AlumnoController alumnoCtrl;
	private final CursoController cursoCtrl;
	private final ProfesorController profesorCtrl;
	private final AsignaturaController asignaturaCtrl;
	private final MatriculaController matriculaCtrl;
	private final EstadisticasController estadisticasCtrl;

	public Main() {
		this.sc = new Scanner(System.in);
		DBConnection.initialize();
		this.alumnoCtrl = new AlumnoController();
		this.cursoCtrl = new CursoController();
		this.profesorCtrl = new ProfesorController();
		this.asignaturaCtrl = new AsignaturaController();
		this.matriculaCtrl = new MatriculaController();
		this.estadisticasCtrl = new EstadisticasController();
	}

	public static void main(String[] args) {
		new Main().iniciar();
	}

	public void iniciar() {
		System.out.println("\n🎓 SISTEMA DE GESTIÓN ACADÉMICA - REPASO06 🎓");
		System.out.println("Sistema súper completo para examen de Acceso a Datos\n");

		boolean running = true;
		while (running) {
			mostrarMenuPrincipal();
			String opcion = sc.nextLine().trim();

			switch (opcion) {
			case "1":
				menuAlumnos();
				break;
			case "2":
				menuCursos();
				break;
			case "3":
				menuProfesores();
				break;
			case "4":
				menuAsignaturas();
				break;
			case "5":
				menuMatriculas();
				break;
			case "6":
				menuEstadisticas();
				break;
			case "7":
				menuBusquedasAvanzadas();
				break;
			case "0":
				System.out.println("👋 ¡Hasta luego!");
				running = false;
				break;
			default:
				System.out.println("❌ Opción no válida");
			}
		}
		sc.close();
	}

	private void mostrarMenuPrincipal() {
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║                  MENÚ PRINCIPAL                           ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝");
		System.out.println("1. 👨‍🎓 Gestión de Alumnos");
		System.out.println("2. 📚 Gestión de Cursos");
		System.out.println("3. 👨‍🏫 Gestión de Profesores");
		System.out.println("4. 📖 Gestión de Asignaturas");
		System.out.println("5. 📝 Gestión de Matrículas y Calificaciones");
		System.out.println("6. 📊 Estadísticas y Reportes");
		System.out.println("7. 🔍 Búsquedas Avanzadas");
		System.out.println("0. 🚪 Salir");
		System.out.print("➤ Seleccione opción: ");
	}

	// ==================== MENÚ ALUMNOS ====================
	private void menuAlumnos() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
			System.out.println("║              GESTIÓN DE ALUMNOS                           ║");
			System.out.println("╚═══════════════════════════════════════════════════════════╝");
			System.out.println("1. Crear nuevo alumno");
			System.out.println("2. Ver todos los alumnos");
			System.out.println("3. Buscar alumno por ID");
			System.out.println("4. Buscar alumno por DNI");
			System.out.println("5. Buscar alumno por email");
			System.out.println("6. Buscar alumno por nombre");
			System.out.println("7. Actualizar alumno");
			System.out.println("8. Eliminar alumno");
			System.out.println("9. Filtrar por curso");
			System.out.println("10. Filtrar por ciudad");
			System.out.println("11. Filtrar por estado (ACTIVO/BAJA/SUSPENDIDO)");
			System.out.println("12. Filtrar por nota media mínima");
			System.out.println("13. Filtrar por créditos aprobados");
			System.out.println("14. Top alumnos por nota");
			System.out.println("15. Alumnos sin curso asignado");
			System.out.println("0. Volver");
			System.out.print("➤ Opción: ");

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
				buscarAlumnoPorDni();
				break;
			case "5":
				buscarAlumnoPorEmail();
				break;
			case "6":
				buscarAlumnoPorNombre();
				break;
			case "7":
				actualizarAlumno();
				break;
			case "8":
				eliminarAlumno();
				break;
			case "9":
				filtrarAlumnosPorCurso();
				break;
			case "10":
				filtrarAlumnosPorCiudad();
				break;
			case "11":
				filtrarAlumnosPorEstado();
				break;
			case "12":
				filtrarAlumnosPorNota();
				break;
			case "13":
				filtrarAlumnosPorCreditos();
				break;
			case "14":
				topAlumnos();
				break;
			case "15":
				alumnosSinCurso();
				break;
			case "0":
				back = true;
				break;
			default:
				System.out.println("❌ Opción no válida");
			}
		}
	}

	private void crearAlumno() {
		System.out.println("\n--- CREAR NUEVO ALUMNO ---");
		Alumno alumno = new Alumno();

		System.out.print("Nombre: ");
		alumno.setNombre(sc.nextLine());

		System.out.print("Apellidos: ");
		alumno.setApellidos(sc.nextLine());

		System.out.print("DNI: ");
		alumno.setDni(sc.nextLine());

		System.out.print("Email: ");
		alumno.setEmail(sc.nextLine());

		System.out.print("Teléfono: ");
		alumno.setTelefono(sc.nextLine());

		System.out.print("Fecha nacimiento (dd/MM/yyyy): ");
		alumno.setFechaNacimiento(Alumno.parseDate(sc.nextLine()));

		System.out.print("Dirección: ");
		alumno.setDireccion(sc.nextLine());

		System.out.print("Ciudad: ");
		alumno.setCiudad(sc.nextLine());

		System.out.print("Código postal: ");
		alumno.setCodigoPostal(sc.nextLine());

		System.out.print("Estado (ACTIVO/BAJA/SUSPENDIDO): ");
		alumno.setEstado(sc.nextLine());

		alumno.setFechaMatriculacion(new Date());

		alumnoCtrl.crear(alumno);
	}

	private void listarAlumnos() {
		System.out.println("\n--- LISTADO DE ALUMNOS ---");
		List<Alumno> alumnos = alumnoCtrl.leerTodos();
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void buscarAlumnoPorId() {
		System.out.print("ID del alumno: ");
		String id = sc.nextLine();
		Alumno alumno = alumnoCtrl.leerPorId(id);
		if (alumno != null) {
			mostrarDetallesAlumno(alumno);
		}
	}

	private void buscarAlumnoPorDni() {
		System.out.print("DNI: ");
		String dni = sc.nextLine();
		Alumno alumno = alumnoCtrl.buscarPorDni(dni);
		if (alumno != null) {
			mostrarDetallesAlumno(alumno);
		}
	}

	private void buscarAlumnoPorEmail() {
		System.out.print("Email: ");
		String email = sc.nextLine();
		Alumno alumno = alumnoCtrl.buscarPorEmail(email);
		if (alumno != null) {
			mostrarDetallesAlumno(alumno);
		}
	}

	private void buscarAlumnoPorNombre() {
		System.out.print("Nombre a buscar: ");
		String nombre = sc.nextLine();
		List<Alumno> alumnos = alumnoCtrl.buscarPorNombre(nombre);
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void actualizarAlumno() {
		System.out.print("ID del alumno a actualizar: ");
		String id = sc.nextLine();
		Alumno alumno = alumnoCtrl.leerPorId(id);
		if (alumno != null) {
			System.out.print("Nuevo email: ");
			alumno.setEmail(sc.nextLine());
			System.out.print("Nuevo teléfono: ");
			alumno.setTelefono(sc.nextLine());
			System.out.print("Nueva nota media: ");
			alumno.setNotaMedia(Double.parseDouble(sc.nextLine()));
			System.out.print("Nuevos créditos aprobados: ");
			alumno.setCreditosAprobados(Integer.parseInt(sc.nextLine()));
			alumnoCtrl.actualizar(alumno);
		}
	}

	private void eliminarAlumno() {
		System.out.print("ID del alumno a eliminar: ");
		String id = sc.nextLine();
		alumnoCtrl.eliminar(id);
	}

	private void filtrarAlumnosPorCurso() {
		System.out.print("ID del curso: ");
		String idCurso = sc.nextLine();
		List<Alumno> alumnos = alumnoCtrl.filtrarPorCurso(idCurso);
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void filtrarAlumnosPorCiudad() {
		System.out.print("Ciudad: ");
		String ciudad = sc.nextLine();
		List<Alumno> alumnos = alumnoCtrl.filtrarPorCiudad(ciudad);
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void filtrarAlumnosPorEstado() {
		System.out.print("Estado (ACTIVO/BAJA/SUSPENDIDO): ");
		String estado = sc.nextLine();
		List<Alumno> alumnos = alumnoCtrl.filtrarPorEstado(estado);
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void filtrarAlumnosPorNota() {
		System.out.print("Nota mínima: ");
		double nota = Double.parseDouble(sc.nextLine());
		List<Alumno> alumnos = alumnoCtrl.filtrarPorNotaMedia(nota);
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void filtrarAlumnosPorCreditos() {
		System.out.print("Créditos mínimos: ");
		int creditos = Integer.parseInt(sc.nextLine());
		List<Alumno> alumnos = alumnoCtrl.filtrarPorCreditos(creditos);
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void topAlumnos() {
		System.out.print("¿Cuántos top alumnos mostrar?: ");
		int limite = Integer.parseInt(sc.nextLine());
		List<Alumno> alumnos = alumnoCtrl.obtenerTopAlumnos(limite);
		for (int i = 0; i < alumnos.size(); i++) {
			System.out.println((i + 1) + ". " + alumnos.get(i).toString());
		}
	}

	private void alumnosSinCurso() {
		List<Alumno> alumnos = alumnoCtrl.buscarSinCurso();
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void mostrarDetallesAlumno(Alumno a) {
		System.out.println("\n══════════ DETALLES DEL ALUMNO ══════════");
		System.out.println("ID:              " + a.getId());
		System.out.println("Nombre completo: " + a.getNombreCompleto());
		System.out.println("DNI:             " + a.getDni());
		System.out.println("Email:           " + a.getEmail());
		System.out.println("Teléfono:        " + a.getTelefono());
		System.out.println("Edad:            " + a.getEdad() + " años");
		System.out.println("Dirección:       " + a.getDireccion());
		System.out.println("Ciudad:          " + a.getCiudad());
		System.out.println("CP:              " + a.getCodigoPostal());
		System.out.println("Estado:          " + a.getEstado());
		System.out.println("Nota Media:      " + a.getNotaMedia());
		System.out.println("Créditos:        " + a.getCreditosAprobados());
		System.out.println("════════════════════════════════════════");
	}

	// ==================== MENÚ CURSOS ====================
	private void menuCursos() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
			System.out.println("║              GESTIÓN DE CURSOS                            ║");
			System.out.println("╚═══════════════════════════════════════════════════════════╝");
			System.out.println("1. Crear nuevo curso");
			System.out.println("2. Ver todos los cursos");
			System.out.println("3. Buscar curso por ID");
			System.out.println("4. Buscar curso por código");
			System.out.println("5. Actualizar curso");
			System.out.println("6. Eliminar curso");
			System.out.println("7. Filtrar por nivel");
			System.out.println("8. Filtrar por turno");
			System.out.println("9. Filtrar cursos activos/inactivos");
			System.out.println("10. Cursos con plazas disponibles");
			System.out.println("11. Cursos más caros");
			System.out.println("12. Cursos con mayor ocupación");
			System.out.println("0. Volver");
			System.out.print("➤ Opción: ");

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
				buscarCursoPorCodigo();
				break;
			case "5":
				actualizarCurso();
				break;
			case "6":
				eliminarCurso();
				break;
			case "7":
				filtrarCursosPorNivel();
				break;
			case "8":
				filtrarCursosPorTurno();
				break;
			case "9":
				filtrarCursosPorEstado();
				break;
			case "10":
				cursosConPlazas();
				break;
			case "11":
				cursosMasCaros();
				break;
			case "12":
				cursosMayorOcupacion();
				break;
			case "0":
				back = true;
				break;
			default:
				System.out.println("❌ Opción no válida");
			}
		}
	}

	private void crearCurso() {
		System.out.println("\n--- CREAR NUEVO CURSO ---");
		Curso curso = new Curso();

		System.out.print("Código (ej: DAM, DAW): ");
		curso.setCodigo(sc.nextLine());

		System.out.print("Nombre: ");
		curso.setNombre(sc.nextLine());

		System.out.print("Descripción: ");
		curso.setDescripcion(sc.nextLine());

		System.out.print("Duración (horas): ");
		curso.setDuracionHoras(Integer.parseInt(sc.nextLine()));

		System.out.print("Nivel (BASICO/MEDIO/SUPERIOR): ");
		curso.setNivel(sc.nextLine());

		System.out.print("Turno (MAÑANA/TARDE/NOCHE): ");
		curso.setTurno(sc.nextLine());

		System.out.print("Plazas totales: ");
		int plazas = Integer.parseInt(sc.nextLine());
		curso.setPlazasTotales(plazas);
		curso.setPlazasDisponibles(plazas);

		System.out.print("Precio: ");
		curso.setPrecio(Double.parseDouble(sc.nextLine()));

		curso.setActivo(true);
		curso.setFechaInicio(new Date());

		cursoCtrl.crear(curso);
	}

	private void listarCursos() {
		System.out.println("\n--- LISTADO DE CURSOS ---");
		List<Curso> cursos = cursoCtrl.leerTodos();
		for (Curso c : cursos) {
			System.out.println(c.toString());
		}
	}

	private void buscarCursoPorId() {
		System.out.print("ID del curso: ");
		String id = sc.nextLine();
		Curso curso = cursoCtrl.leerPorId(id);
		if (curso != null) {
			System.out.println(curso.toString());
		}
	}

	private void buscarCursoPorCodigo() {
		System.out.print("Código: ");
		String codigo = sc.nextLine();
		Curso curso = cursoCtrl.buscarPorCodigo(codigo);
		if (curso != null) {
			System.out.println(curso.toString());
		}
	}

	private void actualizarCurso() {
		System.out.print("ID del curso: ");
		String id = sc.nextLine();
		Curso curso = cursoCtrl.leerPorId(id);
		if (curso != null) {
			System.out.print("Nuevo precio: ");
			curso.setPrecio(Double.parseDouble(sc.nextLine()));
			System.out.print("Plazas disponibles: ");
			curso.setPlazasDisponibles(Integer.parseInt(sc.nextLine()));
			cursoCtrl.actualizar(curso);
		}
	}

	private void eliminarCurso() {
		System.out.print("ID del curso: ");
		cursoCtrl.eliminar(sc.nextLine());
	}

	private void filtrarCursosPorNivel() {
		System.out.print("Nivel: ");
		List<Curso> cursos = cursoCtrl.filtrarPorNivel(sc.nextLine());
		for (Curso c : cursos) {
			System.out.println(c.toString());
		}
	}

	private void filtrarCursosPorTurno() {
		System.out.print("Turno: ");
		List<Curso> cursos = cursoCtrl.filtrarPorTurno(sc.nextLine());
		for (Curso c : cursos) {
			System.out.println(c.toString());
		}
	}

	private void filtrarCursosPorEstado() {
		System.out.print("¿Activos? (true/false): ");
		boolean activo = Boolean.parseBoolean(sc.nextLine());
		List<Curso> cursos = cursoCtrl.filtrarPorEstado(activo);
		for (Curso c : cursos) {
			System.out.println(c.toString());
		}
	}

	private void cursosConPlazas() {
		List<Curso> cursos = cursoCtrl.filtrarConPlazasDisponibles();
		for (Curso c : cursos) {
			System.out.println(c.toString());
		}
	}

	private void cursosMasCaros() {
		System.out.print("¿Cuántos?: ");
		int limite = Integer.parseInt(sc.nextLine());
		List<Curso> cursos = cursoCtrl.obtenerCursosMasCaros(limite);
		for (int i = 0; i < cursos.size(); i++) {
			System.out.println((i + 1) + ". " + cursos.get(i).toString());
		}
	}

	private void cursosMayorOcupacion() {
		System.out.print("¿Cuántos?: ");
		int limite = Integer.parseInt(sc.nextLine());
		List<Curso> cursos = cursoCtrl.obtenerCursosMayorOcupacion(limite);
		for (int i = 0; i < cursos.size(); i++) {
			System.out.println((i + 1) + ". " + cursos.get(i).toString());
		}
	}

	// ==================== MENÚ PROFESORES ====================
	private void menuProfesores() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
			System.out.println("║            GESTIÓN DE PROFESORES                          ║");
			System.out.println("╚═══════════════════════════════════════════════════════════╝");
			System.out.println("1. Crear profesor");
			System.out.println("2. Ver todos");
			System.out.println("3. Buscar por ID");
			System.out.println("4. Buscar por DNI");
			System.out.println("5. Actualizar");
			System.out.println("6. Eliminar");
			System.out.println("7. Filtrar por departamento");
			System.out.println("8. Filtrar por especialidad");
			System.out.println("9. Filtrar por tipo de contrato");
			System.out.println("10. Filtrar por salario mínimo");
			System.out.println("0. Volver");
			System.out.print("➤ Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				crearProfesor();
				break;
			case "2":
				listarProfesores();
				break;
			case "3":
				buscarProfesorPorId();
				break;
			case "4":
				buscarProfesorPorDni();
				break;
			case "5":
				actualizarProfesor();
				break;
			case "6":
				eliminarProfesor();
				break;
			case "7":
				filtrarProfesoresPorDepartamento();
				break;
			case "8":
				filtrarProfesoresPorEspecialidad();
				break;
			case "9":
				filtrarProfesoresPorContrato();
				break;
			case "10":
				filtrarProfesoresPorSalario();
				break;
			case "0":
				back = true;
				break;
			default:
				System.out.println("❌ Opción no válida");
			}
		}
	}

	private void crearProfesor() {
		System.out.println("\n--- CREAR PROFESOR ---");
		Profesor profesor = new Profesor();

		System.out.print("Nombre: ");
		profesor.setNombre(sc.nextLine());

		System.out.print("Apellidos: ");
		profesor.setApellidos(sc.nextLine());

		System.out.print("DNI: ");
		profesor.setDni(sc.nextLine());

		System.out.print("Email: ");
		profesor.setEmail(sc.nextLine());

		System.out.print("Teléfono: ");
		profesor.setTelefono(sc.nextLine());

		System.out.print("Especialidad: ");
		profesor.setEspecialidad(sc.nextLine());

		System.out.print("Departamento: ");
		profesor.setDepartamento(sc.nextLine());

		System.out.print("Tipo contrato (FIJO/INTERINO/TEMPORAL): ");
		profesor.setTipoContrato(sc.nextLine());

		System.out.print("Salario: ");
		profesor.setSalario(Double.parseDouble(sc.nextLine()));

		System.out.print("Años experiencia: ");
		profesor.setAñosExperiencia(Integer.parseInt(sc.nextLine()));

		profesor.setFechaNacimiento(new Date());
		profesor.setFechaContratacion(new Date());

		profesorCtrl.crear(profesor);
	}

	private void listarProfesores() {
		List<Profesor> profesores = profesorCtrl.leerTodos();
		for (Profesor p : profesores) {
			System.out.println(p.toString());
		}
	}

	private void buscarProfesorPorId() {
		System.out.print("ID: ");
		Profesor p = profesorCtrl.leerPorId(sc.nextLine());
		if (p != null)
			System.out.println(p.toString());
	}

	private void buscarProfesorPorDni() {
		System.out.print("DNI: ");
		Profesor p = profesorCtrl.buscarPorDni(sc.nextLine());
		if (p != null)
			System.out.println(p.toString());
	}

	private void actualizarProfesor() {
		System.out.print("ID: ");
		Profesor p = profesorCtrl.leerPorId(sc.nextLine());
		if (p != null) {
			System.out.print("Nuevo salario: ");
			p.setSalario(Double.parseDouble(sc.nextLine()));
			profesorCtrl.actualizar(p);
		}
	}

	private void eliminarProfesor() {
		System.out.print("ID: ");
		profesorCtrl.eliminar(sc.nextLine());
	}

	private void filtrarProfesoresPorDepartamento() {
		System.out.print("Departamento: ");
		List<Profesor> profesores = profesorCtrl.filtrarPorDepartamento(sc.nextLine());
		for (Profesor p : profesores) {
			System.out.println(p.toString());
		}
	}

	private void filtrarProfesoresPorEspecialidad() {
		System.out.print("Especialidad: ");
		List<Profesor> profesores = profesorCtrl.filtrarPorEspecialidad(sc.nextLine());
		for (Profesor p : profesores) {
			System.out.println(p.toString());
		}
	}

	private void filtrarProfesoresPorContrato() {
		System.out.print("Tipo: ");
		List<Profesor> profesores = profesorCtrl.filtrarPorTipoContrato(sc.nextLine());
		for (Profesor p : profesores) {
			System.out.println(p.toString());
		}
	}

	private void filtrarProfesoresPorSalario() {
		System.out.print("Salario mínimo: ");
		List<Profesor> profesores = profesorCtrl.filtrarPorSalarioMinimo(Double.parseDouble(sc.nextLine()));
		for (Profesor p : profesores) {
			System.out.println(p.toString());
		}
	}

	// ==================== MENÚ ASIGNATURAS ====================
	private void menuAsignaturas() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
			System.out.println("║            GESTIÓN DE ASIGNATURAS                         ║");
			System.out.println("╚═══════════════════════════════════════════════════════════╝");
			System.out.println("1. Crear asignatura");
			System.out.println("2. Ver todas");
			System.out.println("3. Buscar por ID");
			System.out.println("4. Buscar por código");
			System.out.println("5. Filtrar por curso");
			System.out.println("6. Filtrar por profesor");
			System.out.println("7. Filtrar por tipo (OBLIGATORIA/OPTATIVA)");
			System.out.println("0. Volver");
			System.out.print("➤ Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				crearAsignatura();
				break;
			case "2":
				listarAsignaturas();
				break;
			case "3":
				buscarAsignaturaPorId();
				break;
			case "4":
				buscarAsignaturaPorCodigo();
				break;
			case "5":
				filtrarAsignaturasPorCurso();
				break;
			case "6":
				filtrarAsignaturasPorProfesor();
				break;
			case "7":
				filtrarAsignaturasPorTipo();
				break;
			case "0":
				back = true;
				break;
			default:
				System.out.println("❌ Opción no válida");
			}
		}
	}

	private void crearAsignatura() {
		System.out.println("\n--- CREAR ASIGNATURA ---");
		Asignatura asignatura = new Asignatura();

		System.out.print("Código: ");
		asignatura.setCodigo(sc.nextLine());

		System.out.print("Nombre: ");
		asignatura.setNombre(sc.nextLine());

		System.out.print("Descripción: ");
		asignatura.setDescripcion(sc.nextLine());

		System.out.print("Créditos: ");
		asignatura.setCreditos(Integer.parseInt(sc.nextLine()));

		System.out.print("Horas semanales: ");
		asignatura.setHorasSemanales(Integer.parseInt(sc.nextLine()));

		System.out.print("Tipo (OBLIGATORIA/OPTATIVA/PROYECTO): ");
		asignatura.setTipo(sc.nextLine());

		System.out.print("Aula: ");
		asignatura.setAula(sc.nextLine());

		System.out.print("Horario: ");
		asignatura.setHorario(sc.nextLine());

		asignaturaCtrl.crear(asignatura);
	}

	private void listarAsignaturas() {
		List<Asignatura> asignaturas = asignaturaCtrl.leerTodas();
		for (Asignatura a : asignaturas) {
			System.out.println(a.toString());
		}
	}

	private void buscarAsignaturaPorId() {
		System.out.print("ID: ");
		Asignatura a = asignaturaCtrl.leerPorId(sc.nextLine());
		if (a != null)
			System.out.println(a.toString());
	}

	private void buscarAsignaturaPorCodigo() {
		System.out.print("Código: ");
		Asignatura a = asignaturaCtrl.buscarPorCodigo(sc.nextLine());
		if (a != null)
			System.out.println(a.toString());
	}

	private void filtrarAsignaturasPorCurso() {
		System.out.print("ID Curso: ");
		List<Asignatura> asignaturas = asignaturaCtrl.filtrarPorCurso(sc.nextLine());
		for (Asignatura a : asignaturas) {
			System.out.println(a.toString());
		}
	}

	private void filtrarAsignaturasPorProfesor() {
		System.out.print("ID Profesor: ");
		List<Asignatura> asignaturas = asignaturaCtrl.filtrarPorProfesor(sc.nextLine());
		for (Asignatura a : asignaturas) {
			System.out.println(a.toString());
		}
	}

	private void filtrarAsignaturasPorTipo() {
		System.out.print("Tipo: ");
		List<Asignatura> asignaturas = asignaturaCtrl.filtrarPorTipo(sc.nextLine());
		for (Asignatura a : asignaturas) {
			System.out.println(a.toString());
		}
	}

	// ==================== MENÚ MATRÍCULAS ====================
	private void menuMatriculas() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
			System.out.println("║      GESTIÓN DE MATRÍCULAS Y CALIFICACIONES              ║");
			System.out.println("╚═══════════════════════════════════════════════════════════╝");
			System.out.println("1. Crear matrícula");
			System.out.println("2. Ver todas");
			System.out.println("3. Actualizar calificaciones");
			System.out.println("4. Filtrar por alumno");
			System.out.println("5. Filtrar por asignatura");
			System.out.println("6. Ver aprobadas");
			System.out.println("7. Ver suspensas");
			System.out.println("8. Ver convalidadas");
			System.out.println("0. Volver");
			System.out.print("➤ Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				crearMatricula();
				break;
			case "2":
				listarMatriculas();
				break;
			case "3":
				actualizarCalificaciones();
				break;
			case "4":
				filtrarMatriculasPorAlumno();
				break;
			case "5":
				filtrarMatriculasPorAsignatura();
				break;
			case "6":
				verAprobadas();
				break;
			case "7":
				verSuspensas();
				break;
			case "8":
				verConvalidadas();
				break;
			case "0":
				back = true;
				break;
			default:
				System.out.println("❌ Opción no válida");
			}
		}
	}

	private void crearMatricula() {
		System.out.println("\n--- CREAR MATRÍCULA ---");
		Matricula matricula = new Matricula();

		System.out.print("ID Alumno: ");
		matricula.setIdAlumno(sc.nextLine());

		System.out.print("ID Asignatura: ");
		matricula.setIdAsignatura(sc.nextLine());

		matricula.setFechaMatricula(new Date());
		matricula.setConvocatoria(1);

		matriculaCtrl.crear(matricula);
	}

	private void listarMatriculas() {
		List<Matricula> matriculas = matriculaCtrl.leerTodas();
		for (Matricula m : matriculas) {
			System.out.println(m.toString());
		}
	}

	private void actualizarCalificaciones() {
		System.out.print("ID Matrícula: ");
		Matricula m = matriculaCtrl.leerPorId(sc.nextLine());
		if (m != null) {
			System.out.print("Nota Parcial 1: ");
			m.setNotaParcial1(Double.parseDouble(sc.nextLine()));
			System.out.print("Nota Parcial 2: ");
			m.setNotaParcial2(Double.parseDouble(sc.nextLine()));
			System.out.print("Nota Parcial 3: ");
			m.setNotaParcial3(Double.parseDouble(sc.nextLine()));
			System.out.print("Nota Final: ");
			m.setNotaFinal(Double.parseDouble(sc.nextLine()));
			m.setCalificacion(m.determinarCalificacion());
			matriculaCtrl.actualizar(m);
		}
	}

	private void filtrarMatriculasPorAlumno() {
		System.out.print("ID Alumno: ");
		List<Matricula> matriculas = matriculaCtrl.filtrarPorAlumno(sc.nextLine());
		for (Matricula m : matriculas) {
			System.out.println(m.toString());
		}
	}

	private void filtrarMatriculasPorAsignatura() {
		System.out.print("ID Asignatura: ");
		List<Matricula> matriculas = matriculaCtrl.filtrarPorAsignatura(sc.nextLine());
		for (Matricula m : matriculas) {
			System.out.println(m.toString());
		}
	}

	private void verAprobadas() {
		List<Matricula> matriculas = matriculaCtrl.filtrarAprobadas();
		for (Matricula m : matriculas) {
			System.out.println(m.toString());
		}
	}

	private void verSuspensas() {
		List<Matricula> matriculas = matriculaCtrl.filtrarSuspensas();
		for (Matricula m : matriculas) {
			System.out.println(m.toString());
		}
	}

	private void verConvalidadas() {
		List<Matricula> matriculas = matriculaCtrl.filtrarConvalidadas();
		for (Matricula m : matriculas) {
			System.out.println(m.toString());
		}
	}

	// ==================== MENÚ ESTADÍSTICAS ====================
	private void menuEstadisticas() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
			System.out.println("║         ESTADÍSTICAS Y REPORTES                           ║");
			System.out.println("╚═══════════════════════════════════════════════════════════╝");
			System.out.println("1. Resumen general del sistema");
			System.out.println("2. Estadísticas de alumnos");
			System.out.println("3. Estadísticas de cursos");
			System.out.println("4. Estadísticas de profesores");
			System.out.println("5. Estadísticas de matrículas");
			System.out.println("0. Volver");
			System.out.print("➤ Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				estadisticasCtrl.mostrarResumenGeneral();
				break;
			case "2":
				estadisticasCtrl.mostrarEstadisticasAlumnos();
				break;
			case "3":
				estadisticasCtrl.mostrarEstadisticasCursos();
				break;
			case "4":
				estadisticasCtrl.mostrarEstadisticasProfesores();
				break;
			case "5":
				estadisticasCtrl.mostrarEstadisticasMatriculas();
				break;
			case "0":
				back = true;
				break;
			default:
				System.out.println("❌ Opción no válida");
			}
		}
	}

	// ==================== MENÚ BÚSQUEDAS AVANZADAS ====================
	private void menuBusquedasAvanzadas() {
		boolean back = false;
		while (!back) {
			System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
			System.out.println("║          BÚSQUEDAS AVANZADAS                              ║");
			System.out.println("╚═══════════════════════════════════════════════════════════╝");
			System.out.println("1. Alumnos por rango de edad");
			System.out.println("2. Cursos por rango de precio");
			System.out.println("3. Profesores por experiencia mínima");
			System.out.println("4. Asignaturas por créditos mínimos");
			System.out.println("5. Matrículas por nota mínima");
			System.out.println("6. Matrículas por convocatoria");
			System.out.println("0. Volver");
			System.out.print("➤ Opción: ");

			String opcion = sc.nextLine().trim();
			switch (opcion) {
			case "1":
				buscarAlumnosPorRangoEdad();
				break;
			case "2":
				buscarCursosPorRangoPrecio();
				break;
			case "3":
				buscarProfesoresPorExperiencia();
				break;
			case "4":
				buscarAsignaturasPorCreditos();
				break;
			case "5":
				buscarMatriculasPorNota();
				break;
			case "6":
				buscarMatriculasPorConvocatoria();
				break;
			case "0":
				back = true;
				break;
			default:
				System.out.println("❌ Opción no válida");
			}
		}
	}

	private void buscarAlumnosPorRangoEdad() {
		System.out.print("Edad mínima: ");
		int min = Integer.parseInt(sc.nextLine());
		System.out.print("Edad máxima: ");
		int max = Integer.parseInt(sc.nextLine());
		List<Alumno> alumnos = alumnoCtrl.filtrarPorEdad(min, max);
		for (Alumno a : alumnos) {
			System.out.println(a.toString());
		}
	}

	private void buscarCursosPorRangoPrecio() {
		System.out.print("Precio mínimo: ");
		double min = Double.parseDouble(sc.nextLine());
		System.out.print("Precio máximo: ");
		double max = Double.parseDouble(sc.nextLine());
		List<Curso> cursos = cursoCtrl.filtrarPorPrecio(min, max);
		for (Curso c : cursos) {
			System.out.println(c.toString());
		}
	}

	private void buscarProfesoresPorExperiencia() {
		System.out.print("Años mínimos: ");
		int años = Integer.parseInt(sc.nextLine());
		List<Profesor> profesores = profesorCtrl.filtrarPorExperiencia(años);
		for (Profesor p : profesores) {
			System.out.println(p.toString());
		}
	}

	private void buscarAsignaturasPorCreditos() {
		System.out.print("Créditos mínimos: ");
		int creditos = Integer.parseInt(sc.nextLine());
		List<Asignatura> asignaturas = asignaturaCtrl.filtrarPorCreditos(creditos);
		for (Asignatura a : asignaturas) {
			System.out.println(a.toString());
		}
	}

	private void buscarMatriculasPorNota() {
		System.out.print("Nota mínima: ");
		double nota = Double.parseDouble(sc.nextLine());
		List<Matricula> matriculas = matriculaCtrl.filtrarPorNotaMinima(nota);
		for (Matricula m : matriculas) {
			System.out.println(m.toString());
		}
	}

	private void buscarMatriculasPorConvocatoria() {
		System.out.print("Convocatoria: ");
		int conv = Integer.parseInt(sc.nextLine());
		List<Matricula> matriculas = matriculaCtrl.filtrarPorConvocatoria(conv);
		for (Matricula m : matriculas) {
			System.out.println(m.toString());
		}
	}
}
