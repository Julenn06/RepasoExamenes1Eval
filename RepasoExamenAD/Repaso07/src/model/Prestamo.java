package model;

public class Prestamo {
	private String libroId;
	private String fechaPrestamo;
	private String fechaDevolucion;
	private String estado;

	public Prestamo() {
	}

	public Prestamo(String libroId, String fechaPrestamo, String fechaDevolucion, String estado) {
		this.libroId = libroId;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.estado = estado;
	}

	public String getLibroId() {
		return libroId;
	}

	public void setLibroId(String libroId) {
		this.libroId = libroId;
	}

	public String getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(String fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public String getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(String fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return String.format("Préstamo libro %s: %s → %s [%s]", libroId, fechaPrestamo, fechaDevolucion, estado);
	}
}
