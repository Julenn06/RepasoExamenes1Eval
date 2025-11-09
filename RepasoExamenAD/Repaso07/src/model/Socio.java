package model;

import java.util.ArrayList;
import java.util.List;

public class Socio {
	private String id;
	private String nombre;
	private String dni;
	private String email;
	private String telefono;
	private String fechaInscripcion;
	private String tipoSocio;
	private String estado;
	private int prestamosActivos;
	private List<Prestamo> historialPrestamos;

	public Socio() {
		this.historialPrestamos = new ArrayList<>();
	}

	public Socio(String id, String nombre, String dni, String email, String telefono, String fechaInscripcion,
			String tipoSocio, String estado, int prestamosActivos) {
		this.id = id;
		this.nombre = nombre;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
		this.fechaInscripcion = fechaInscripcion;
		this.tipoSocio = tipoSocio;
		this.estado = estado;
		this.prestamosActivos = prestamosActivos;
		this.historialPrestamos = new ArrayList<>();
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

	public String getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getTipoSocio() {
		return tipoSocio;
	}

	public void setTipoSocio(String tipoSocio) {
		this.tipoSocio = tipoSocio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getPrestamosActivos() {
		return prestamosActivos;
	}

	public void setPrestamosActivos(int prestamosActivos) {
		this.prestamosActivos = prestamosActivos;
	}

	public List<Prestamo> getHistorialPrestamos() {
		return historialPrestamos;
	}

	public void setHistorialPrestamos(List<Prestamo> historialPrestamos) {
		this.historialPrestamos = historialPrestamos;
	}

	public void addPrestamo(Prestamo prestamo) {
		this.historialPrestamos.add(prestamo);
	}

	@Override
	public String toString() {
		return String.format("Socio [%s]: %s (%s) - %s, %d préstamos activos", id, nombre, dni, tipoSocio,
				prestamosActivos);
	}
}
