package model;

import java.util.Date;

/**
 * Modelo de datos para Alumno Representa un registro en el archivo alumnos.dat
 */
public class Alumnos {

	private String nombre;
	private int edad;
	private Date fechaInscripcion;

	public Alumnos() {
	}

	public Alumnos(String nombre, int edad, Date fechaInscripcion) {
		this.nombre = nombre;
		this.edad = edad;
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	@Override
	public String toString() {
		return String.format("Alumno[nombre=%s, edad=%d, fechaInscripcion=%s]", nombre, edad, fechaInscripcion);
	}
}
