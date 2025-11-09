package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Modelo de datos para Matrículas (Calificaciones) Representa la relación entre
 * alumno y asignatura con sus calificaciones
 */
public class Matricula {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	private String id;
	private String idAlumno;
	private String idAsignatura;
	private Date fechaMatricula;
	private double notaParcial1;
	private double notaParcial2;
	private double notaParcial3;
	private double notaFinal;
	private String calificacion; // APROBADO, SUSPENSO, NOTABLE, SOBRESALIENTE, MH
	private int convocatoria; // 1=Primera, 2=Segunda, etc.
	private Date fechaExamen;
	private boolean convalidada;
	private String observaciones;

	// Constructor vacío
	public Matricula() {
		this.convocatoria = 1;
		this.convalidada = false;
		this.notaParcial1 = 0.0;
		this.notaParcial2 = 0.0;
		this.notaParcial3 = 0.0;
		this.notaFinal = 0.0;
	}

	// Constructor completo
	public Matricula(String id, String idAlumno, String idAsignatura, Date fechaMatricula, double notaParcial1,
			double notaParcial2, double notaParcial3, double notaFinal, String calificacion, int convocatoria,
			Date fechaExamen, boolean convalidada, String observaciones) {
		this.id = id;
		this.idAlumno = idAlumno;
		this.idAsignatura = idAsignatura;
		this.fechaMatricula = fechaMatricula;
		this.notaParcial1 = notaParcial1;
		this.notaParcial2 = notaParcial2;
		this.notaParcial3 = notaParcial3;
		this.notaFinal = notaFinal;
		this.calificacion = calificacion;
		this.convocatoria = convocatoria;
		this.fechaExamen = fechaExamen;
		this.convalidada = convalidada;
		this.observaciones = observaciones;
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(String idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(String idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public Date getFechaMatricula() {
		return fechaMatricula;
	}

	public void setFechaMatricula(Date fechaMatricula) {
		this.fechaMatricula = fechaMatricula;
	}

	public double getNotaParcial1() {
		return notaParcial1;
	}

	public void setNotaParcial1(double notaParcial1) {
		this.notaParcial1 = notaParcial1;
	}

	public double getNotaParcial2() {
		return notaParcial2;
	}

	public void setNotaParcial2(double notaParcial2) {
		this.notaParcial2 = notaParcial2;
	}

	public double getNotaParcial3() {
		return notaParcial3;
	}

	public void setNotaParcial3(double notaParcial3) {
		this.notaParcial3 = notaParcial3;
	}

	public double getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(double notaFinal) {
		this.notaFinal = notaFinal;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public int getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(int convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Date getFechaExamen() {
		return fechaExamen;
	}

	public void setFechaExamen(Date fechaExamen) {
		this.fechaExamen = fechaExamen;
	}

	public boolean isConvalidada() {
		return convalidada;
	}

	public void setConvalidada(boolean convalidada) {
		this.convalidada = convalidada;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	// Métodos auxiliares
	public double calcularNotaMedia() {
		return (notaParcial1 + notaParcial2 + notaParcial3) / 3.0;
	}

	public boolean estaAprobada() {
		return notaFinal >= 5.0 || convalidada;
	}

	public String determinarCalificacion() {
		if (convalidada)
			return "CONVALIDADA";
		if (notaFinal < 5.0)
			return "SUSPENSO";
		if (notaFinal < 7.0)
			return "APROBADO";
		if (notaFinal < 9.0)
			return "NOTABLE";
		if (notaFinal < 10.0)
			return "SOBRESALIENTE";
		return "MATRICULA_HONOR";
	}

	public String formatFechaMatricula() {
		return fechaMatricula != null ? SDF.format(fechaMatricula) : "";
	}

	public String formatFechaExamen() {
		return fechaExamen != null ? SDF.format(fechaExamen) : "";
	}

	public static Date parseDate(String dateStr) {
		try {
			return SDF.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return String.format("Matrícula %s - Nota: %.2f - %s - Conv: %d%s", id, notaFinal, calificacion, convocatoria,
				convalidada ? " (CONVALIDADA)" : "");
	}
}
