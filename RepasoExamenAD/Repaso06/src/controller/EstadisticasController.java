package controller;

import java.util.List;

import model.Alumno;
import model.Curso;
import model.Matricula;
import model.Profesor;

/**
 * Controlador de Estadísticas y Reportes Avanzados
 */
public class EstadisticasController {

	private AlumnoController alumnoCtrl;
	private CursoController cursoCtrl;
	private ProfesorController profesorCtrl;
	private MatriculaController matriculaCtrl;

	public EstadisticasController() {
		this.alumnoCtrl = new AlumnoController();
		this.cursoCtrl = new CursoController();
		this.profesorCtrl = new ProfesorController();
		this.matriculaCtrl = new MatriculaController();
	}

	public void mostrarResumenGeneral() {
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║           RESUMEN GENERAL DEL SISTEMA                     ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝");

		List<Alumno> alumnos = alumnoCtrl.leerTodos();
		List<Curso> cursos = cursoCtrl.leerTodos();
		List<Profesor> profesores = profesorCtrl.leerTodos();
		List<Matricula> matriculas = matriculaCtrl.leerTodas();

		System.out.println("📊 Total de Alumnos:       " + alumnos.size());
		System.out.println("📚 Total de Cursos:        " + cursos.size());
		System.out.println("👨‍🏫 Total de Profesores:   " + profesores.size());
		System.out.println("📝 Total de Matrículas:    " + matriculas.size());

		long alumnosActivos = alumnos.stream().filter(a -> "ACTIVO".equals(a.getEstado())).count();
		System.out.println("✅ Alumnos Activos:        " + alumnosActivos);

		long cursosActivos = cursos.stream().filter(Curso::isActivo).count();
		System.out.println("✅ Cursos Activos:         " + cursosActivos);
	}

	public void mostrarEstadisticasAlumnos() {
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║           ESTADÍSTICAS DE ALUMNOS                         ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝");

		List<Alumno> alumnos = alumnoCtrl.leerTodos();
		if (alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");
			return;
		}

		double sumaNotas = 0;
		int sumaCreditos = 0;
		int sumaEdades = 0;

		for (Alumno a : alumnos) {
			sumaNotas += a.getNotaMedia();
			sumaCreditos += a.getCreditosAprobados();
			sumaEdades += a.getEdad();
		}

		System.out.println("📈 Nota Media Global:           " + String.format("%.2f", sumaNotas / alumnos.size()));
		System.out.println("📚 Créditos Aprobados (media):  " + (sumaCreditos / alumnos.size()));
		System.out.println("🎂 Edad Media:                  " + (sumaEdades / alumnos.size()) + " años");

		List<Alumno> top5 = alumnoCtrl.obtenerTopAlumnos(5);
		System.out.println("\n🏆 TOP 5 ALUMNOS POR NOTA MEDIA:");
		for (int i = 0; i < top5.size(); i++) {
			Alumno a = top5.get(i);
			System.out.println(String.format("   %d. %s - %.2f", i + 1, a.getNombreCompleto(), a.getNotaMedia()));
		}
	}

	public void mostrarEstadisticasCursos() {
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║           ESTADÍSTICAS DE CURSOS                          ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝");

		List<Curso> cursos = cursoCtrl.leerTodos();
		if (cursos.isEmpty()) {
			System.out.println("No hay cursos registrados.");
			return;
		}

		double sumaPrecio = 0;
		int sumaPlazas = 0;
		int sumaHoras = 0;

		for (Curso c : cursos) {
			sumaPrecio += c.getPrecio();
			sumaPlazas += c.getPlazasTotales();
			sumaHoras += c.getDuracionHoras();
		}

		System.out.println("💰 Precio Medio:              " + String.format("%.2f€", sumaPrecio / cursos.size()));
		System.out.println("👥 Plazas Totales (media):    " + (sumaPlazas / cursos.size()));
		System.out.println("⏰ Duración Media:            " + (sumaHoras / cursos.size()) + " horas");

		List<Curso> masCaros = cursoCtrl.obtenerCursosMasCaros(3);
		System.out.println("\n💵 TOP 3 CURSOS MÁS CAROS:");
		for (int i = 0; i < masCaros.size(); i++) {
			Curso c = masCaros.get(i);
			System.out.println(String.format("   %d. %s - %.2f€", i + 1, c.getNombre(), c.getPrecio()));
		}

		List<Curso> mayorOcupacion = cursoCtrl.obtenerCursosMayorOcupacion(3);
		System.out.println("\n📊 TOP 3 CURSOS CON MAYOR OCUPACIÓN:");
		for (int i = 0; i < mayorOcupacion.size(); i++) {
			Curso c = mayorOcupacion.get(i);
			System.out.println(
					String.format("   %d. %s - %.1f%% ocupado", i + 1, c.getNombre(), c.getPorcentajeOcupacion()));
		}
	}

	public void mostrarEstadisticasProfesores() {
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║           ESTADÍSTICAS DE PROFESORES                      ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝");

		List<Profesor> profesores = profesorCtrl.leerTodos();
		if (profesores.isEmpty()) {
			System.out.println("No hay profesores registrados.");
			return;
		}

		double sumaSalario = 0;
		int sumaExperiencia = 0;

		for (Profesor p : profesores) {
			sumaSalario += p.getSalario();
			sumaExperiencia += p.getAñosExperiencia();
		}

		System.out.println("💰 Salario Medio:           " + String.format("%.2f€", sumaSalario / profesores.size()));
		System.out.println("📅 Experiencia Media:       " + (sumaExperiencia / profesores.size()) + " años");

		long fijos = profesores.stream().filter(p -> "FIJO".equals(p.getTipoContrato())).count();
		long interinos = profesores.stream().filter(p -> "INTERINO".equals(p.getTipoContrato())).count();
		long temporales = profesores.stream().filter(p -> "TEMPORAL".equals(p.getTipoContrato())).count();

		System.out.println("\n📋 DISTRIBUCIÓN POR TIPO DE CONTRATO:");
		System.out.println("   - FIJOS:      " + fijos);
		System.out.println("   - INTERINOS:  " + interinos);
		System.out.println("   - TEMPORALES: " + temporales);
	}

	public void mostrarEstadisticasMatriculas() {
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║           ESTADÍSTICAS DE MATRÍCULAS                      ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝");

		List<Matricula> matriculas = matriculaCtrl.leerTodas();
		if (matriculas.isEmpty()) {
			System.out.println("No hay matrículas registradas.");
			return;
		}

		long aprobadas = matriculas.stream().filter(Matricula::estaAprobada).count();
		long suspensas = matriculas.size() - aprobadas;
		double tasaAprobados = (double) aprobadas / matriculas.size() * 100;

		System.out.println("✅ Aprobadas:          " + aprobadas + " (" + String.format("%.1f%%", tasaAprobados) + ")");
		System.out.println(
				"❌ Suspensas:          " + suspensas + " (" + String.format("%.1f%%", 100 - tasaAprobados) + ")");

		long convalidadas = matriculas.stream().filter(Matricula::isConvalidada).count();
		System.out.println("🔄 Convalidadas:       " + convalidadas);

		double sumaNotas = 0;
		for (Matricula m : matriculas) {
			sumaNotas += m.getNotaFinal();
		}
		System.out.println("📊 Nota Media:         " + String.format("%.2f", sumaNotas / matriculas.size()));

		long sobresalientes = matriculas.stream().filter(m -> "SOBRESALIENTE".equals(m.getCalificacion())).count();
		long notables = matriculas.stream().filter(m -> "NOTABLE".equals(m.getCalificacion())).count();
		System.out.println("\n🏆 DISTRIBUCIÓN DE CALIFICACIONES:");
		System.out.println("   - Sobresalientes: " + sobresalientes);
		System.out.println("   - Notables:       " + notables);
	}
}
