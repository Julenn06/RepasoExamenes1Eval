package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Modelo de datos para Alumnos - VERSION EXTENDIDA Contiene información
 * completa del alumno incluyendo datos personales, contacto y estado académico
 */
public class Alumno {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	private String id;
	private String nombre;
	private String apellidos;
	private String dni;
	private String email;
	private String telefono;
	private Date fechaNacimiento;
	private String direccion;
	private String ciudad;
	private String codigoPostal;
	private String idCurso; // Referencia al curso
	private String estado; // ACTIVO, BAJA, SUSPENDIDO
	private Date fechaMatriculacion;
	private double notaMedia;
	private int creditosAprobados;

	// Constructor vacío
	public Alumno() {
		this.estado = "ACTIVO";
		this.notaMedia = 0.0;
		this.creditosAprobados = 0;
	}

	// Constructor completo
	public Alumno(String id, String nombre, String apellidos, String dni, String email, String telefono,
			Date fechaNacimiento, String direccion, String ciudad, String codigoPostal, String idCurso, String estado,
			Date fechaMatriculacion, double notaMedia, int creditosAprobados) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.idCurso = idCurso;
		this.estado = estado;
		this.fechaMatriculacion = fechaMatriculacion;
		this.notaMedia = notaMedia;
		this.creditosAprobados = creditosAprobados;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaMatriculacion() {
		return fechaMatriculacion;
	}

	public void setFechaMatriculacion(Date fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}

	public double getNotaMedia() {
		return notaMedia;
	}

	public void setNotaMedia(double notaMedia) {
		this.notaMedia = notaMedia;
	}

	public int getCreditosAprobados() {
		return creditosAprobados;
	}

	public void setCreditosAprobados(int creditosAprobados) {
		this.creditosAprobados = creditosAprobados;
	}

	// Métodos auxiliares
	public String getNombreCompleto() {
		return nombre + " " + apellidos;
	}

	public int getEdad() {
		if (fechaNacimiento == null)
			return 0;
		long diff = System.currentTimeMillis() - fechaNacimiento.getTime();
		return (int) (diff / (1000L * 60 * 60 * 24 * 365));
	}

	public String formatFechaNacimiento() {
		return fechaNacimiento != null ? SDF.format(fechaNacimiento) : "";
	}

	public String formatFechaMatriculacion() {
		return fechaMatriculacion != null ? SDF.format(fechaMatriculacion) : "";
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
		return String.format("%s - %s (%s) - %s - Estado: %s - Nota Media: %.2f", id, getNombreCompleto(), dni, email,
				estado, notaMedia);
	}
}
