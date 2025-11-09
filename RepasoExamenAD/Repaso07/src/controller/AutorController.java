package controller;

import java.util.List;
import java.util.stream.Collectors;

import model.Autor;
import model.Libro;
import model.Premio;

/**
 * Controlador para gestionar operaciones CRUD de Autores
 */
public class AutorController {
	private XMLController xmlController;

	public AutorController(XMLController xmlController) {
		this.xmlController = xmlController;
	}

	/**
	 * Lista todos los autores
	 */
	public void listarAutores() {
		List<Autor> autores = xmlController.obtenerAutores();

		if (autores.isEmpty()) {
			System.out.println("No hay autores registrados.");
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  LISTADO DE AUTORES (" + autores.size() + " total)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Autor autor : autores) {
			System.out.println("\n[" + autor.getId() + "] " + autor.getNombre());
			System.out.println("  ├─ Nacionalidad: " + autor.getNacionalidad());
			System.out.println("  ├─ Género literario: " + autor.getGeneroLiterario());
			System.out.println("  ├─ Nacimiento: " + autor.getFechaNacimiento()
					+ (autor.getFechaFallecimiento() != null && !autor.getFechaFallecimiento().isEmpty()
							? " - Fallecimiento: " + autor.getFechaFallecimiento()
							: ""));
			System.out.println("  ├─ Libros publicados: " + autor.getLibros().size());
			System.out.println("  └─ Premios: " + autor.getPremios().size());
		}
		System.out.println("\n═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra los detalles completos de un autor
	 */
	public void mostrarDetallesAutor(String id) {
		Autor autor = xmlController.buscarAutorPorId(id);

		if (autor == null) {
			System.out.println("✗ Autor no encontrado con ID: " + id);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  DETALLES DEL AUTOR");
		System.out.println("═══════════════════════════════════════════════════");
		System.out.println("ID: " + autor.getId());
		System.out.println("Nombre: " + autor.getNombre());
		System.out.println("Nacionalidad: " + autor.getNacionalidad());
		System.out.println("Nacimiento: " + autor.getFechaNacimiento());
		if (autor.getFechaFallecimiento() != null && !autor.getFechaFallecimiento().isEmpty()) {
			System.out.println("Fallecimiento: " + autor.getFechaFallecimiento());
		}
		System.out.println("Género literario: " + autor.getGeneroLiterario());
		System.out.println("\nBiografía:");
		System.out.println(autor.getBiografia());

		System.out.println("\nPremios recibidos (" + autor.getPremios().size() + "):");
		if (autor.getPremios().isEmpty()) {
			System.out.println("  (Sin premios registrados)");
		} else {
			for (Premio premio : autor.getPremios()) {
				System.out.println("  • " + premio);
			}
		}

		System.out.println("\nObras publicadas (" + autor.getLibros().size() + "):");
		if (autor.getLibros().isEmpty()) {
			System.out.println("  (Sin libros registrados)");
		} else {
			for (Libro libro : autor.getLibros()) {
				System.out.println(
						"  • [" + libro.getId() + "] " + libro.getTitulo() + " (" + libro.getAnioPublicacion() + ")");
			}
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Busca autores por nombre (búsqueda parcial)
	 */
	public void buscarAutoresPorNombre(String nombre) {
		List<Autor> autores = xmlController.obtenerAutores();
		List<Autor> resultados = autores.stream()
				.filter(a -> a.getNombre().toLowerCase().contains(nombre.toLowerCase())).collect(Collectors.toList());

		if (resultados.isEmpty()) {
			System.out.println("✗ No se encontraron autores con el nombre: " + nombre);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  RESULTADOS DE BÚSQUEDA (" + resultados.size() + " encontrados)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Autor autor : resultados) {
			System.out.println("\n[" + autor.getId() + "] " + autor.getNombre());
			System.out.println("  ├─ " + autor.getNacionalidad() + " - " + autor.getGeneroLiterario());
			System.out.println("  └─ " + autor.getLibros().size() + " libros publicados");
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Lista autores por nacionalidad
	 */
	public void listarAutoresPorNacionalidad(String nacionalidad) {
		List<Autor> autores = xmlController.obtenerAutores();
		List<Autor> resultados = autores.stream().filter(a -> a.getNacionalidad().equalsIgnoreCase(nacionalidad))
				.collect(Collectors.toList());

		if (resultados.isEmpty()) {
			System.out.println("✗ No se encontraron autores de nacionalidad: " + nacionalidad);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  AUTORES DE NACIONALIDAD " + nacionalidad.toUpperCase());
		System.out.println("  (" + resultados.size() + " encontrados)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Autor autor : resultados) {
			System.out.println("\n• " + autor.getNombre() + " [" + autor.getId() + "]");
			System.out.println("  Género: " + autor.getGeneroLiterario() + " | Libros: " + autor.getLibros().size());
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Lista autores por género literario
	 */
	public void listarAutoresPorGenero(String genero) {
		List<Autor> autores = xmlController.obtenerAutores();
		List<Autor> resultados = autores.stream()
				.filter(a -> a.getGeneroLiterario().toLowerCase().contains(genero.toLowerCase()))
				.collect(Collectors.toList());

		if (resultados.isEmpty()) {
			System.out.println("✗ No se encontraron autores del género: " + genero);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  AUTORES DEL GÉNERO: " + genero.toUpperCase());
		System.out.println("  (" + resultados.size() + " encontrados)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Autor autor : resultados) {
			System.out.println("\n• " + autor.getNombre() + " (" + autor.getNacionalidad() + ")");
			System.out.println("  ID: " + autor.getId() + " | Libros: " + autor.getLibros().size());
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Lista autores premiados
	 */
	public void listarAutoresPremiados() {
		List<Autor> autores = xmlController.obtenerAutores();
		List<Autor> premiados = autores.stream().filter(a -> !a.getPremios().isEmpty()).collect(Collectors.toList());

		if (premiados.isEmpty()) {
			System.out.println("No hay autores con premios registrados.");
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  AUTORES PREMIADOS (" + premiados.size() + " total)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Autor autor : premiados) {
			System.out.println("\n" + autor.getNombre() + " [" + autor.getId() + "]");
			System.out.println("  Premios (" + autor.getPremios().size() + "):");
			for (Premio premio : autor.getPremios()) {
				System.out.println("    ★ " + premio);
			}
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Cuenta el total de autores
	 */
	public int contarAutores() {
		return xmlController.obtenerAutores().size();
	}

	/**
	 * Cuenta el total de premios de todos los autores
	 */
	public int contarTotalPremios() {
		List<Autor> autores = xmlController.obtenerAutores();
		return autores.stream().mapToInt(a -> a.getPremios().size()).sum();
	}
}
