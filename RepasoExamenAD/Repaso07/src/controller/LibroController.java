package controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import model.Autor;
import model.Capitulo;
import model.Libro;
import model.Resena;

/**
 * Controlador para gestionar operaciones CRUD de Libros
 */
public class LibroController {
	private XMLController xmlController;

	public LibroController(XMLController xmlController) {
		this.xmlController = xmlController;
	}

	/**
	 * Lista todos los libros
	 */
	public void listarLibros() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();

		if (libros.isEmpty()) {
			System.out.println("No hay libros registrados.");
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  CATÁLOGO DE LIBROS (" + libros.size() + " total)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Libro libro : libros) {
			System.out.println("\n[" + libro.getId() + "] " + libro.getTitulo());
			System.out.println("  ├─ Autor: " + obtenerNombreAutor(libro.getAutorId()));
			System.out.println("  ├─ Editorial: " + libro.getEditorial() + " (" + libro.getAnioPublicacion() + ")");
			System.out.println("  ├─ ISBN: " + libro.getIsbn());
			System.out.println("  ├─ Precio: " + libro.getPrecio() + "€ | Stock: " + libro.getStock());
			System.out.println("  └─ Valoración: ★" + libro.getValoracion() + "/5.0");
		}
		System.out.println("\n═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra los detalles completos de un libro
	 */
	public void mostrarDetallesLibro(String id) {
		Libro libro = xmlController.buscarLibroPorId(id);

		if (libro == null) {
			System.out.println("✗ Libro no encontrado con ID: " + id);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  FICHA DEL LIBRO");
		System.out.println("═══════════════════════════════════════════════════");
		System.out.println("ID: " + libro.getId());
		System.out.println("Título: " + libro.getTitulo());
		System.out.println("Autor: " + obtenerNombreAutor(libro.getAutorId()));
		System.out.println("ISBN: " + libro.getIsbn());
		System.out.println("Editorial: " + libro.getEditorial());
		System.out.println("Año de publicación: " + libro.getAnioPublicacion());
		System.out.println("Páginas: " + libro.getNumeroPaginas());
		System.out.println("Idioma: " + libro.getIdioma());
		System.out.println("Categoría: " + libro.getCategoria());
		System.out.println("Precio: " + libro.getPrecio() + "€");
		System.out.println("Stock disponible: " + libro.getStock());
		System.out.println("Valoración: ★" + libro.getValoracion() + "/5.0");

		System.out.println("\nDescripción:");
		System.out.println(libro.getDescripcion());

		System.out.println("\nCapítulos (" + libro.getCapitulos().size() + "):");
		if (libro.getCapitulos().isEmpty()) {
			System.out.println("  (Sin capítulos registrados)");
		} else {
			for (Capitulo cap : libro.getCapitulos()) {
				System.out.println("  " + cap);
				System.out.println("     " + cap.getResumen());
			}
		}

		System.out.println("\nReseñas (" + libro.getResenas().size() + "):");
		if (libro.getResenas().isEmpty()) {
			System.out.println("  (Sin reseñas)");
		} else {
			for (Resena resena : libro.getResenas()) {
				System.out.println("  • " + resena);
			}
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Busca libros por título (búsqueda parcial)
	 */
	public void buscarLibrosPorTitulo(String titulo) {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream().filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
				.collect(Collectors.toList());

		mostrarResultadosBusqueda(resultados, "título contiene '" + titulo + "'");
	}

	/**
	 * Busca libros por ISBN
	 */
	public void buscarLibroPorISBN(String isbn) {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream().filter(l -> l.getIsbn().equalsIgnoreCase(isbn))
				.collect(Collectors.toList());

		if (resultados.isEmpty()) {
			System.out.println("✗ No se encontró ningún libro con ISBN: " + isbn);
		} else {
			mostrarDetallesLibro(resultados.get(0).getId());
		}
	}

	/**
	 * Lista libros por categoría
	 */
	public void listarLibrosPorCategoria(String categoria) {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream().filter(l -> l.getCategoria().equalsIgnoreCase(categoria))
				.collect(Collectors.toList());

		mostrarResultadosBusqueda(resultados, "categoría: " + categoria);
	}

	/**
	 * Lista libros por editorial
	 */
	public void listarLibrosPorEditorial(String editorial) {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream()
				.filter(l -> l.getEditorial().toLowerCase().contains(editorial.toLowerCase()))
				.collect(Collectors.toList());

		mostrarResultadosBusqueda(resultados, "editorial contiene '" + editorial + "'");
	}

	/**
	 * Lista libros por año de publicación
	 */
	public void listarLibrosPorAnio(int anio) {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream().filter(l -> l.getAnioPublicacion() == anio)
				.collect(Collectors.toList());

		mostrarResultadosBusqueda(resultados, "año " + anio);
	}

	/**
	 * Lista libros por rango de años
	 */
	public void listarLibrosPorRangoAnios(int anioInicio, int anioFin) {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream()
				.filter(l -> l.getAnioPublicacion() >= anioInicio && l.getAnioPublicacion() <= anioFin)
				.sorted(Comparator.comparingInt(Libro::getAnioPublicacion)).collect(Collectors.toList());

		mostrarResultadosBusqueda(resultados, "publicados entre " + anioInicio + " y " + anioFin);
	}

	/**
	 * Lista libros por idioma
	 */
	public void listarLibrosPorIdioma(String idioma) {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream().filter(l -> l.getIdioma().equalsIgnoreCase(idioma))
				.collect(Collectors.toList());

		mostrarResultadosBusqueda(resultados, "idioma: " + idioma);
	}

	/**
	 * Lista libros con stock bajo (menos de 10 unidades)
	 */
	public void listarLibrosStockBajo() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream().filter(l -> l.getStock() < 10)
				.sorted(Comparator.comparingInt(Libro::getStock)).collect(Collectors.toList());

		if (resultados.isEmpty()) {
			System.out.println("✓ Todos los libros tienen stock suficiente.");
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  ⚠ LIBROS CON STOCK BAJO (" + resultados.size() + ")");
		System.out.println("═══════════════════════════════════════════════════");

		for (Libro libro : resultados) {
			System.out.println("\n[" + libro.getId() + "] " + libro.getTitulo());
			System.out.println("  Stock actual: " + libro.getStock() + " unidades");
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Lista libros mejor valorados
	 */
	public void listarLibrosMejorValorados() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> topLibros = libros.stream().sorted(Comparator.comparingDouble(Libro::getValoracion).reversed())
				.limit(5).collect(Collectors.toList());

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  ★ TOP LIBROS MEJOR VALORADOS");
		System.out.println("═══════════════════════════════════════════════════");

		int posicion = 1;
		for (Libro libro : topLibros) {
			System.out.println("\n" + posicion + ". " + libro.getTitulo() + " - ★" + libro.getValoracion());
			System.out.println("   Autor: " + obtenerNombreAutor(libro.getAutorId()));
			System.out.println("   " + libro.getResenas().size() + " reseñas");
			posicion++;
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Lista libros por rango de precio
	 */
	public void listarLibrosPorRangoPrecio(double precioMin, double precioMax) {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Libro> resultados = libros.stream().filter(l -> l.getPrecio() >= precioMin && l.getPrecio() <= precioMax)
				.sorted(Comparator.comparingDouble(Libro::getPrecio)).collect(Collectors.toList());

		mostrarResultadosBusqueda(resultados, "precio entre " + precioMin + "€ y " + precioMax + "€");
	}

	/**
	 * Método auxiliar para mostrar resultados de búsqueda
	 */
	private void mostrarResultadosBusqueda(List<Libro> resultados, String criterio) {
		if (resultados.isEmpty()) {
			System.out.println("✗ No se encontraron libros con " + criterio);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  RESULTADOS: " + criterio);
		System.out.println("  (" + resultados.size() + " encontrados)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Libro libro : resultados) {
			System.out.println("\n[" + libro.getId() + "] " + libro.getTitulo());
			System.out.println("  ├─ Autor: " + obtenerNombreAutor(libro.getAutorId()));
			System.out.println("  ├─ " + libro.getEditorial() + " (" + libro.getAnioPublicacion() + ")");
			System.out.println(
					"  └─ " + libro.getPrecio() + "€ | Stock: " + libro.getStock() + " | ★" + libro.getValoracion());
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Obtiene el nombre del autor por ID
	 */
	private String obtenerNombreAutor(String autorId) {
		Autor autor = xmlController.buscarAutorPorId(autorId);
		return autor != null ? autor.getNombre() : "Desconocido";
	}

	/**
	 * Cuenta el total de libros
	 */
	public int contarLibros() {
		return xmlController.obtenerTodosLosLibros().size();
	}

	/**
	 * Calcula el valor total del inventario
	 */
	public double calcularValorInventario() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		return libros.stream().mapToDouble(l -> l.getPrecio() * l.getStock()).sum();
	}
}
