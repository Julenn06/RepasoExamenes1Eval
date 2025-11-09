package model;

import java.util.ArrayList;
import java.util.List;

public class Libro {
	private String id;
	private String titulo;
	private String isbn;
	private int anioPublicacion;
	private String editorial;
	private int numeroPaginas;
	private String idioma;
	private double precio;
	private int stock;
	private String categoria;
	private double valoracion;
	private String descripcion;
	private String autorId;
	private List<Capitulo> capitulos;
	private List<Resena> resenas;

	public Libro() {
		this.capitulos = new ArrayList<>();
		this.resenas = new ArrayList<>();
	}

	public Libro(String id, String titulo, String isbn, int anioPublicacion, String editorial, int numeroPaginas,
			String idioma, double precio, int stock, String categoria, double valoracion, String descripcion,
			String autorId) {
		this.id = id;
		this.titulo = titulo;
		this.isbn = isbn;
		this.anioPublicacion = anioPublicacion;
		this.editorial = editorial;
		this.numeroPaginas = numeroPaginas;
		this.idioma = idioma;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.valoracion = valoracion;
		this.descripcion = descripcion;
		this.autorId = autorId;
		this.capitulos = new ArrayList<>();
		this.resenas = new ArrayList<>();
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getAnioPublicacion() {
		return anioPublicacion;
	}

	public void setAnioPublicacion(int anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getValoracion() {
		return valoracion;
	}

	public void setValoracion(double valoracion) {
		this.valoracion = valoracion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAutorId() {
		return autorId;
	}

	public void setAutorId(String autorId) {
		this.autorId = autorId;
	}

	public List<Capitulo> getCapitulos() {
		return capitulos;
	}

	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}

	public void addCapitulo(Capitulo capitulo) {
		this.capitulos.add(capitulo);
	}

	public List<Resena> getResenas() {
		return resenas;
	}

	public void setResenas(List<Resena> resenas) {
		this.resenas = resenas;
	}

	public void addResena(Resena resena) {
		this.resenas.add(resena);
	}

	@Override
	public String toString() {
		return String.format("Libro [%s]: \"%s\" (%d) - %s, %.2f€, Stock: %d", id, titulo, anioPublicacion, editorial,
				precio, stock);
	}
}
