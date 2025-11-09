package model;

import java.util.ArrayList;
import java.util.List;

public class Autor {
	private String id;
	private String nombre;
	private String nacionalidad;
	private String fechaNacimiento;
	private String fechaFallecimiento;
	private String generoLiterario;
	private String biografia;
	private List<Premio> premios;
	private List<Libro> libros;

	public Autor() {
		this.premios = new ArrayList<>();
		this.libros = new ArrayList<>();
	}

	public Autor(String id, String nombre, String nacionalidad, String fechaNacimiento, String generoLiterario,
			String biografia) {
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
		this.generoLiterario = generoLiterario;
		this.biografia = biografia;
		this.premios = new ArrayList<>();
		this.libros = new ArrayList<>();
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

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFechaFallecimiento() {
		return fechaFallecimiento;
	}

	public void setFechaFallecimiento(String fechaFallecimiento) {
		this.fechaFallecimiento = fechaFallecimiento;
	}

	public String getGeneroLiterario() {
		return generoLiterario;
	}

	public void setGeneroLiterario(String generoLiterario) {
		this.generoLiterario = generoLiterario;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public List<Premio> getPremios() {
		return premios;
	}

	public void setPremios(List<Premio> premios) {
		this.premios = premios;
	}

	public void addPremio(Premio premio) {
		this.premios.add(premio);
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

	public void addLibro(Libro libro) {
		this.libros.add(libro);
	}

	@Override
	public String toString() {
		return String.format("Autor [%s]: %s (%s) - %d libros, %d premios", id, nombre, nacionalidad, libros.size(),
				premios.size());
	}
}
