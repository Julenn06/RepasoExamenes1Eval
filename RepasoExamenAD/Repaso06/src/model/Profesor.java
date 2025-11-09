package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Modelo de datos para Profesores Contiene información completa del profesorado
 */
public class Profesor {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

	private String id;
	private String nombre;
	private String apellidos;
	private String dni;
	private String email;
	private String telefono;
	private Date fechaNacimiento;
	private String especialidad;
	private String departamento;
	private String tipoContrato; // FIJO, INTERINO, TEMPORAL
	private Date fechaContratacion;
	private double salario;
	private String titulacion;
	private int añosExperiencia;
	private boolean activo;

	// Constructor vacío
	public Profesor() {
		this.activo = true;
	}

	// Constructor completo
	public Profesor(String id, String nombre, String apellidos, String dni, String email, String telefono,
			Date fechaNacimiento, String especialidad, String departamento, String tipoContrato, Date fechaContratacion,
			double salario, String titulacion, int añosExperiencia, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.especialidad = especialidad;
		this.departamento = departamento;
		this.tipoContrato = tipoContrato;
		this.fechaContratacion = fechaContratacion;
		this.salario = salario;
		this.titulacion = titulacion;
		this.añosExperiencia = añosExperiencia;
		this.activo = activo;
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

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public Date getFechaContratacion() {
		return fechaContratacion;
	}

	public void setFechaContratacion(Date fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(String titulacion) {
		this.titulacion = titulacion;
	}

	public int getAñosExperiencia() {
		return añosExperiencia;
	}

	public void setAñosExperiencia(int añosExperiencia) {
		this.añosExperiencia = añosExperiencia;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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

	public String formatFechaContratacion() {
		return fechaContratacion != null ? SDF.format(fechaContratacion) : "";
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
		return String.format("%s - %s (%s) - %s - %s - %.2f€", id, getNombreCompleto(), especialidad, departamento,
				tipoContrato, salario);
	}
}
