package model;

/**
 * Modelo Curso - Representa un curso en Firestore.
 * 
 * Campos: - id: identificador único del documento en Firestore - nombre: nombre
 * del curso (ej. "Acceso a Datos") - descripcion: descripción breve del curso
 * 
 * Uso en examen: - Crear cursos para demostrar relaciones entre colecciones -
 * Los alumnos pueden referenciar un curso mediante idCurso - Permite consultas
 * JOIN-like (buscar alumnos de un curso)
 */
public class Curso {

	private String id;
	private String nombre;
	private String descripcion;

	// Constructores
	public Curso() {
	}

	public Curso(String id, String nombre, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// Helper: formato legible para mostrar
	@Override
	public String toString() {
		return "Curso{id='" + id + "', nombre='" + nombre + "', descripcion='" + descripcion + "'}";
	}

	// Helper: formatear para exportar CSV/DAT
	public String toExportLine() {
		return id + ";" + nombre + ";" + (descripcion != null ? descripcion : "");
	}
}
