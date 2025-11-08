package model;

public class Producto {
	private String nombre;
	private double precio;
	private int cantidad;

	public Producto(String nombre, double precio, int cantidad) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getTotal() {
		return precio * cantidad;
	}

	public String toString() {
		return "Producto [nombre=" + nombre + ", precio=" + precio + ", cantidad=" + cantidad + "]";
	}
}
