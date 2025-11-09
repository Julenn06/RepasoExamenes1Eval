package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Modelo de datos para Cursos Representa un curso académico con información
 * completa
 */
public class Curso {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	private String id;
	private String codigo; // Ej: DAM, DAW, ASIR
	private String nombre;
	private String descripcion;
	private int duracionHoras;
	private String nivel; // BASICO, MEDIO, SUPERIOR
	private String turno; // MAÑANA, TARDE, NOCHE
	private int plazasDisponibles;
	private int plazasTotales;
	private Date fechaInicio;
	private Date fechaFin;
	private double precio;
	private String idCoordinador; // Referencia al profesor coordinador
	private boolean activo;

	// Constructor vacío
	public Curso() {
		this.activo = true;
	}

	// Constructor completo
	public Curso(String id, String codigo, String nombre, String descripcion, int duracionHoras, String nivel,
			String turno, int plazasDisponibles, int plazasTotales, Date fechaInicio, Date fechaFin, double precio,
			String idCoordinador, boolean activo) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.duracionHoras = duracionHoras;
		this.nivel = nivel;
		this.turno = turno;
		this.plazasDisponibles = plazasDisponibles;
		this.plazasTotales = plazasTotales;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.precio = precio;
		this.idCoordinador = idCoordinador;
		this.activo = activo;
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

	public int getDuracionHoras() {
		return duracionHoras;
	}

	public void setDuracionHoras(int duracionHoras) {
		this.duracionHoras = duracionHoras;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	public int getPlazasTotales() {
		return plazasTotales;
	}

	public void setPlazasTotales(int plazasTotales) {
		this.plazasTotales = plazasTotales;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getIdCoordinador() {
		return idCoordinador;
	}

	public void setIdCoordinador(String idCoordinador) {
		this.idCoordinador = idCoordinador;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	// Métodos auxiliares
	public int getPlazasOcupadas() {
		return plazasTotales - plazasDisponibles;
	}

	public double getPorcentajeOcupacion() {
		if (plazasTotales == 0)
			return 0;
		return ((double) getPlazasOcupadas() / plazasTotales) * 100;
	}

	public boolean hayPlazasDisponibles() {
		return plazasDisponibles > 0;
	}

	public String formatFechaInicio() {
		return fechaInicio != null ? SDF.format(fechaInicio) : "";
	}

	public String formatFechaFin() {
		return fechaFin != null ? SDF.format(fechaFin) : "";
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
		return String.format("%s - %s (%s) - %s - %d/%d plazas - %.2f€", codigo, nombre, nivel, turno,
				getPlazasOcupadas(), plazasTotales, precio);
	}
}
