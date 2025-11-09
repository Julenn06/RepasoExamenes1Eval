package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Modelo Alumnos - Representa un alumno en Firestore.
 * 
 * Campos: - id: identificador único del documento en Firestore - name: nombre
 * del alumno - age: edad del alumno - birthDate: fecha de nacimiento - idCurso:
 * referencia al documento de Curso (relación entre colecciones)
 * 
 * Uso en examen: - Crear instancias con new Alumnos() - Setear campos con
 * setters - Convertir a/desde Map para Firestore usando métodos helper
 */
public class Alumnos {

	private String id; // ID del documento en Firestore
	private String name;
	private int age;
	private Date birthDate;
	private String idCurso; // Referencia al curso (ID del documento Curso)

	// Formato de fecha para conversión String <-> Date
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	// Constructores
	public Alumnos() {
	}

	public Alumnos(String id, String name, int age, Date birthDate, String idCurso) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.birthDate = birthDate;
		this.idCurso = idCurso;
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	// Helper: formato legible para mostrar
	@Override
	public String toString() {
		String fecha = birthDate != null ? SDF.format(birthDate) : "sin fecha";
		String curso = idCurso != null && !idCurso.isEmpty() ? idCurso : "sin curso";
		return "Alumno{id='" + id + "', name='" + name + "', age=" + age + ", birthDate=" + fecha + ", curso=" + curso
				+ "}";
	}

	// Helper: formatear para exportar CSV/DAT
	public String toExportLine() {
		String fecha = birthDate != null ? SDF.format(birthDate) : "";
		String curso = idCurso != null ? idCurso : "";
		return id + ";" + name + ";" + age + ";" + fecha + ";" + curso;
	}

	// Helper: parsear fecha desde String
	public static Date parseDate(String s) {
		try {
			return SDF.parse(s);
		} catch (Exception e) {
			return null;
		}
	}

	// Helper: formatear fecha a String
	public static String formatDate(Date d) {
		return d != null ? SDF.format(d) : "";
	}
}
