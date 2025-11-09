package model;

public class Biblioteca {
	private String nombre;
	private String direccion;
	private String telefono;
	private String email;
	private int anioFundacion;

	public Biblioteca() {
	}

	public Biblioteca(String nombre, String direccion, String telefono, String email, int anioFundacion) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.anioFundacion = anioFundacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAnioFundacion() {
		return anioFundacion;
	}

	public void setAnioFundacion(int anioFundacion) {
		this.anioFundacion = anioFundacion;
	}

	@Override
	public String toString() {
		return String.format("%s (Fundada en %d)\n%s\nTel: %s | Email: %s", nombre, anioFundacion, direccion, telefono,
				email);
	}
}
