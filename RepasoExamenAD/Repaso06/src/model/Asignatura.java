package model;

/**
 * Modelo de datos para Asignaturas Representa las asignaturas impartidas en los
 * cursos
 */
public class Asignatura {

	private String id;
	private String codigo; // Ej: AD, PSP, DI
	private String nombre;
	private String descripcion;
	private int creditos;
	private int horasSemanales;
	private String tipo; // OBLIGATORIA, OPTATIVA, PROYECTO
	private String idCurso; // Referencia al curso
	private String idProfesor; // Referencia al profesor que la imparte
	private String aula;
	private String horario;
	private boolean activa;

	// Constructor vacío
	public Asignatura() {
		this.activa = true;
	}

	// Constructor completo
	public Asignatura(String id, String codigo, String nombre, String descripcion, int creditos, int horasSemanales,
			String tipo, String idCurso, String idProfesor, String aula, String horario, boolean activa) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.creditos = creditos;
		this.horasSemanales = horasSemanales;
		this.tipo = tipo;
		this.idCurso = idCurso;
		this.idProfesor = idProfesor;
		this.aula = aula;
		this.horario = horario;
		this.activa = activa;
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public int getHorasSemanales() {
		return horasSemanales;
	}

	public void setHorasSemanales(int horasSemanales) {
		this.horasSemanales = horasSemanales;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	public String getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(String idProfesor) {
		this.idProfesor = idProfesor;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	@Override
	public String toString() {
		return String.format("%s - %s (%d créditos) - %s - %s - Aula: %s", codigo, nombre, creditos, tipo, horario,
				aula);
	}
}
