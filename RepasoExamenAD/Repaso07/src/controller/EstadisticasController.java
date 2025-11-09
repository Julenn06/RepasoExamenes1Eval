package controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Autor;
import model.Libro;
import model.Socio;

/**
 * Controlador para generar estadísticas de la biblioteca
 */
public class EstadisticasController {
	private XMLController xmlController;

	public EstadisticasController(XMLController xmlController) {
		this.xmlController = xmlController;
	}

	/**
	 * Muestra un resumen general de estadísticas
	 */
	public void mostrarResumenGeneral() {
		List<Autor> autores = xmlController.obtenerAutores();
		List<Libro> libros = xmlController.obtenerTodosLosLibros();
		List<Socio> socios = xmlController.obtenerSocios();

		int totalCapitulos = libros.stream().mapToInt(l -> l.getCapitulos().size()).sum();

		int totalResenas = libros.stream().mapToInt(l -> l.getResenas().size()).sum();

		int totalPremios = autores.stream().mapToInt(a -> a.getPremios().size()).sum();

		int prestamosActivos = socios.stream().mapToInt(Socio::getPrestamosActivos).sum();

		int totalPrestamos = socios.stream().mapToInt(s -> s.getHistorialPrestamos().size()).sum();

		double valoracionPromedio = libros.stream().mapToDouble(Libro::getValoracion).average().orElse(0.0);

		double valorInventario = libros.stream().mapToDouble(l -> l.getPrecio() * l.getStock()).sum();

		System.out.println("\n╔═══════════════════════════════════════════════════╗");
		System.out.println("║     ESTADÍSTICAS GENERALES DE LA BIBLIOTECA      ║");
		System.out.println("╠═══════════════════════════════════════════════════╣");
		System.out.println("║                                                   ║");
		System.out.println("║  📚 CONTENIDO                                     ║");
		System.out.println("║  ├─ Autores registrados: " + String.format("%-23d", autores.size()) + "║");
		System.out.println("║  ├─ Libros en catálogo: " + String.format("%-23d", libros.size()) + "║");
		System.out.println("║  ├─ Total capítulos: " + String.format("%-27d", totalCapitulos) + "║");
		System.out.println("║  ├─ Total reseñas: " + String.format("%-29d", totalResenas) + "║");
		System.out.println("║  └─ Total premios: " + String.format("%-29d", totalPremios) + "║");
		System.out.println("║                                                   ║");
		System.out.println("║  👥 SOCIOS                                        ║");
		System.out.println("║  ├─ Socios registrados: " + String.format("%-26d", socios.size()) + "║");
		System.out.println("║  ├─ Préstamos activos: " + String.format("%-27d", prestamosActivos) + "║");
		System.out.println("║  └─ Total préstamos históricos: " + String.format("%-15d", totalPrestamos) + "║");
		System.out.println("║                                                   ║");
		System.out.println("║  💰 VALORACIÓN E INVENTARIO                       ║");
		System.out.println(
				"║  ├─ Valoración promedio: " + String.format("★%.2f/5.0", valoracionPromedio) + "             ║");
		System.out.println("║  └─ Valor inventario: " + String.format("%.2f€", valorInventario) + "           ║");
		System.out.println("║                                                   ║");
		System.out.println("╚═══════════════════════════════════════════════════╝");
	}

	/**
	 * Muestra estadísticas por categoría de libros
	 */
	public void mostrarEstadisticasPorCategoria() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();

		Map<String, Long> librosPorCategoria = libros.stream()
				.collect(Collectors.groupingBy(Libro::getCategoria, Collectors.counting()));

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  📊 DISTRIBUCIÓN POR CATEGORÍA");
		System.out.println("═══════════════════════════════════════════════════");

		librosPorCategoria.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEach(entry -> {
					System.out.printf("  %-20s : %d libros\n", entry.getKey(), entry.getValue());
				});

		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra estadísticas por nacionalidad de autores
	 */
	public void mostrarEstadisticasPorNacionalidad() {
		List<Autor> autores = xmlController.obtenerAutores();

		Map<String, Long> autoresPorNacionalidad = autores.stream()
				.collect(Collectors.groupingBy(Autor::getNacionalidad, Collectors.counting()));

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  🌍 DISTRIBUCIÓN POR NACIONALIDAD");
		System.out.println("═══════════════════════════════════════════════════");

		autoresPorNacionalidad.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEach(entry -> {
					System.out.printf("  %-20s : %d autores\n", entry.getKey(), entry.getValue());
				});

		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra los autores más prolíficos (más libros)
	 */
	public void mostrarAutoresMasProlíficos() {
		List<Autor> autores = xmlController.obtenerAutores();

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  ✍ AUTORES MÁS PROLÍFICOS");
		System.out.println("═══════════════════════════════════════════════════");

		autores.stream().sorted((a1, a2) -> Integer.compare(a2.getLibros().size(), a1.getLibros().size())).limit(5)
				.forEach(autor -> {
					System.out.printf("  • %-30s : %d libros\n", autor.getNombre(), autor.getLibros().size());
				});

		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra libros con más reseñas
	 */
	public void mostrarLibrosConMasResenas() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  💬 LIBROS CON MÁS RESEÑAS");
		System.out.println("═══════════════════════════════════════════════════");

		libros.stream().filter(l -> !l.getResenas().isEmpty())
				.sorted((l1, l2) -> Integer.compare(l2.getResenas().size(), l1.getResenas().size())).limit(5)
				.forEach(libro -> {
					System.out.printf("  • %-35s : %d reseñas (★%.1f)\n", libro.getTitulo(), libro.getResenas().size(),
							libro.getValoracion());
				});

		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra estadísticas de stock
	 */
	public void mostrarEstadisticasStock() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();

		int totalUnidades = libros.stream().mapToInt(Libro::getStock).sum();

		double stockPromedio = libros.stream().mapToInt(Libro::getStock).average().orElse(0.0);

		long librosStockBajo = libros.stream().filter(l -> l.getStock() < 10).count();

		long librosSinStock = libros.stream().filter(l -> l.getStock() == 0).count();

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  📦 ESTADÍSTICAS DE STOCK");
		System.out.println("═══════════════════════════════════════════════════");
		System.out.printf("  Total unidades en inventario: %d\n", totalUnidades);
		System.out.printf("  Stock promedio por libro: %.2f\n", stockPromedio);
		System.out.printf("  Libros con stock bajo (<10): %d\n", librosStockBajo);
		System.out.printf("  Libros sin stock: %d\n", librosSinStock);
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra estadísticas por editorial
	 */
	public void mostrarEstadisticasPorEditorial() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();

		Map<String, Long> librosPorEditorial = libros.stream()
				.collect(Collectors.groupingBy(Libro::getEditorial, Collectors.counting()));

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  🏢 DISTRIBUCIÓN POR EDITORIAL");
		System.out.println("═══════════════════════════════════════════════════");

		librosPorEditorial.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEach(entry -> {
					System.out.printf("  %-25s : %d libros\n", entry.getKey(), entry.getValue());
				});

		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra libros publicados por década
	 */
	public void mostrarLibrosPorDecada() {
		List<Libro> libros = xmlController.obtenerTodosLosLibros();

		Map<String, Long> librosPorDecada = libros.stream()
				.collect(Collectors.groupingBy(l -> (l.getAnioPublicacion() / 10 * 10) + "s", Collectors.counting()));

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  📅 PUBLICACIONES POR DÉCADA");
		System.out.println("═══════════════════════════════════════════════════");

		librosPorDecada.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
			System.out.printf("  %-10s : %d libros\n", entry.getKey(), entry.getValue());
		});

		System.out.println("═══════════════════════════════════════════════════");
	}
}
