package controller;

import java.util.List;
import java.util.stream.Collectors;

import model.Libro;
import model.Prestamo;
import model.Socio;

/**
 * Controlador para gestionar operaciones CRUD de Socios
 */
public class SocioController {
	private XMLController xmlController;

	public SocioController(XMLController xmlController) {
		this.xmlController = xmlController;
	}

	/**
	 * Lista todos los socios
	 */
	public void listarSocios() {
		List<Socio> socios = xmlController.obtenerSocios();

		if (socios.isEmpty()) {
			System.out.println("No hay socios registrados.");
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  LISTADO DE SOCIOS (" + socios.size() + " total)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Socio socio : socios) {
			System.out.println("\n[" + socio.getId() + "] " + socio.getNombre());
			System.out.println("  ├─ DNI: " + socio.getDni());
			System.out.println("  ├─ Email: " + socio.getEmail());
			System.out.println("  ├─ Tipo: " + socio.getTipoSocio());
			System.out.println("  ├─ Estado: " + socio.getEstado());
			System.out.println("  └─ Préstamos activos: " + socio.getPrestamosActivos());
		}
		System.out.println("\n═══════════════════════════════════════════════════");
	}

	/**
	 * Muestra los detalles completos de un socio
	 */
	public void mostrarDetallesSocio(String id) {
		Socio socio = xmlController.buscarSocioPorId(id);

		if (socio == null) {
			System.out.println("✗ Socio no encontrado con ID: " + id);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  FICHA DEL SOCIO");
		System.out.println("═══════════════════════════════════════════════════");
		System.out.println("ID: " + socio.getId());
		System.out.println("Nombre: " + socio.getNombre());
		System.out.println("DNI: " + socio.getDni());
		System.out.println("Email: " + socio.getEmail());
		System.out.println("Teléfono: " + socio.getTelefono());
		System.out.println("Fecha de inscripción: " + socio.getFechaInscripcion());
		System.out.println("Tipo de socio: " + socio.getTipoSocio());
		System.out.println("Estado: " + socio.getEstado());
		System.out.println("Préstamos activos: " + socio.getPrestamosActivos());

		System.out.println("\nHistorial de préstamos (" + socio.getHistorialPrestamos().size() + "):");
		if (socio.getHistorialPrestamos().isEmpty()) {
			System.out.println("  (Sin préstamos registrados)");
		} else {
			for (Prestamo prestamo : socio.getHistorialPrestamos()) {
				Libro libro = xmlController.buscarLibroPorId(prestamo.getLibroId());
				String tituloLibro = libro != null ? libro.getTitulo() : "Desconocido";
				System.out.println("  • " + prestamo.getEstado() + " - " + tituloLibro);
				System.out.println("    " + prestamo.getFechaPrestamo() + " → " + prestamo.getFechaDevolucion());
			}
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Busca socios por nombre
	 */
	public void buscarSociosPorNombre(String nombre) {
		List<Socio> socios = xmlController.obtenerSocios();
		List<Socio> resultados = socios.stream().filter(s -> s.getNombre().toLowerCase().contains(nombre.toLowerCase()))
				.collect(Collectors.toList());

		if (resultados.isEmpty()) {
			System.out.println("✗ No se encontraron socios con el nombre: " + nombre);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  RESULTADOS DE BÚSQUEDA (" + resultados.size() + " encontrados)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Socio socio : resultados) {
			System.out.println("\n[" + socio.getId() + "] " + socio.getNombre());
			System.out.println("  DNI: " + socio.getDni() + " | Email: " + socio.getEmail());
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Busca socio por DNI
	 */
	public void buscarSocioPorDNI(String dni) {
		List<Socio> socios = xmlController.obtenerSocios();
		Socio socio = socios.stream().filter(s -> s.getDni().equalsIgnoreCase(dni)).findFirst().orElse(null);

		if (socio == null) {
			System.out.println("✗ No se encontró ningún socio con DNI: " + dni);
		} else {
			mostrarDetallesSocio(socio.getId());
		}
	}

	/**
	 * Lista socios por tipo
	 */
	public void listarSociosPorTipo(String tipo) {
		List<Socio> socios = xmlController.obtenerSocios();
		List<Socio> resultados = socios.stream().filter(s -> s.getTipoSocio().equalsIgnoreCase(tipo))
				.collect(Collectors.toList());

		if (resultados.isEmpty()) {
			System.out.println("✗ No se encontraron socios de tipo: " + tipo);
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  SOCIOS TIPO: " + tipo.toUpperCase());
		System.out.println("  (" + resultados.size() + " encontrados)");
		System.out.println("═══════════════════════════════════════════════════");

		for (Socio socio : resultados) {
			System.out.println("\n• " + socio.getNombre() + " [" + socio.getId() + "]");
			System.out.println("  Préstamos activos: " + socio.getPrestamosActivos());
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Lista socios con préstamos activos
	 */
	public void listarSociosConPrestamos() {
		List<Socio> socios = xmlController.obtenerSocios();
		List<Socio> conPrestamos = socios.stream().filter(s -> s.getPrestamosActivos() > 0)
				.collect(Collectors.toList());

		if (conPrestamos.isEmpty()) {
			System.out.println("No hay socios con préstamos activos.");
			return;
		}

		System.out.println("\n═══════════════════════════════════════════════════");
		System.out.println("  SOCIOS CON PRÉSTAMOS ACTIVOS (" + conPrestamos.size() + ")");
		System.out.println("═══════════════════════════════════════════════════");

		for (Socio socio : conPrestamos) {
			System.out.println("\n• " + socio.getNombre() + " [" + socio.getId() + "]");
			System.out.println("  Préstamos activos: " + socio.getPrestamosActivos());

			// Mostrar préstamos activos
			List<Prestamo> prestamosActivos = socio.getHistorialPrestamos().stream()
					.filter(p -> p.getEstado().equalsIgnoreCase("Prestado")).collect(Collectors.toList());

			for (Prestamo prestamo : prestamosActivos) {
				Libro libro = xmlController.buscarLibroPorId(prestamo.getLibroId());
				String titulo = libro != null ? libro.getTitulo() : "Desconocido";
				System.out.println("    - " + titulo + " (Devolución: " + prestamo.getFechaDevolucion() + ")");
			}
		}
		System.out.println("═══════════════════════════════════════════════════");
	}

	/**
	 * Cuenta el total de socios
	 */
	public int contarSocios() {
		return xmlController.obtenerSocios().size();
	}

	/**
	 * Cuenta socios activos
	 */
	public int contarSociosActivos() {
		List<Socio> socios = xmlController.obtenerSocios();
		return (int) socios.stream().filter(s -> s.getEstado().equalsIgnoreCase("Activo")).count();
	}
}
