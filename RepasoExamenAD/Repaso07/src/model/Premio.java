package model;

public class Premio {
	private String nombre;
	private int anio;

	public Premio() {
	}

	public Premio(String nombre, int anio) {
		this.nombre = nombre;
		this.anio = anio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", nombre, anio);
	}
}
