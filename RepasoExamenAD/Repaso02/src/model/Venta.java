package model;

import java.util.Date;

public class Venta {
	private int id;
	private String cliente;
	private Date fecha;
	private Producto[] productos;

	public Venta(int id, String cliente, Date fecha, Producto[] productos) {
		this.id = id;
		this.cliente = cliente;
		this.fecha = fecha;
		this.productos = productos;
	}

	public int getId() {
		return id;
	}

	public String getCliente() {
		return cliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public Producto[] getProductos() {
		return productos;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setProductos(Producto[] productos) {
		this.productos = productos;
	}

	public double getTotalVenta() {
		double total = 0;
		for (Producto producto : productos) {
			total += producto.getTotal();
		}
		return total;
	}

}
