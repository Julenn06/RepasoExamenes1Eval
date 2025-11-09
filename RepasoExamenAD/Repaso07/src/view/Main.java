package view;

import java.util.Scanner;

import controller.AutorController;
import controller.EstadisticasController;
import controller.LibroController;
import controller.SocioController;
import controller.XMLController;
import model.Biblioteca;

/**
 * Clase principal con menú interactivo para gestionar la biblioteca
 */
public class Main {
	private XMLController xmlController;
	private AutorController autorController;
	private LibroController libroController;
	private SocioController socioController;
	private EstadisticasController estadisticasController;
	private Scanner scanner;

	public Main() {
		this.scanner = new Scanner(System.in);
		this.xmlController = new XMLController();
		this.autorController = new AutorController(xmlController);
		this.libroController = new LibroController(xmlController);
		this.socioController = new SocioController(xmlController);
		this.estadisticasController = new EstadisticasController(xmlController);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.iniciar();
	}

	public void iniciar() {
		mostrarCabecera();

		boolean continuar = true;
		while (continuar) {
			mostrarMenuPrincipal();
			int opcion = leerOpcion();

			switch (opcion) {
			case 1 -> menuAutores();
			case 2 -> menuLibros();
			case 3 -> menuSocios();
			case 4 -> menuEstadisticas();
			case 5 -> menuBusquedas();
			case 0 -> {
				System.out.println("\n👋 ¡Hasta pronto!");
				continuar = false;
			}
			default -> System.out.println("❌ Opción inválida");
			}
		}

		scanner.close();
	}

	private void mostrarCabecera() {
		Biblioteca biblioteca = xmlController.obtenerBiblioteca();
		System.out.println("\n╔════════════════════════════════════════════════════════╗");
		System.out.println("║                                                        ║");
		System.out.println("║         📚 SISTEMA DE GESTIÓN DE BIBLIOTECA 📚         ║");
		System.out.println("║                                                        ║");
		System.out.println("╚════════════════════════════════════════════════════════╝");
		System.out.println("\n" + biblioteca);
		System.out.println();
	}

	private void mostrarMenuPrincipal() {
		System.out.println("\n╔════════════════════════════════════════════════════════╗");
		System.out.println("║                    MENÚ PRINCIPAL                      ║");
		System.out.println("╠════════════════════════════════════════════════════════╣");
		System.out.println("║  1. 📖 Gestión de Autores                              ║");
		System.out.println("║  2. 📚 Gestión de Libros                               ║");
		System.out.println("║  3. 👥 Gestión de Socios                               ║");
		System.out.println("║  4. 📊 Estadísticas                                    ║");
		System.out.println("║  5. 🔍 Búsquedas Avanzadas                             ║");
		System.out.println("║  0. ❌ Salir                                            ║");
		System.out.println("╚════════════════════════════════════════════════════════╝");
		System.out.print("Seleccione una opción: ");
	}

	private void menuAutores() {
		boolean volver = false;
		while (!volver) {
			System.out.println("\n╔════════════════════════════════════════════════════════╗");
			System.out.println("║                  📖 MENÚ AUTORES                        ║");
			System.out.println("╠════════════════════════════════════════════════════════╣");
			System.out.println("║  1. Listar todos los autores                           ║");
			System.out.println("║  2. Ver detalles de un autor                           ║");
			System.out.println("║  3. Buscar autores por nombre                          ║");
			System.out.println("║  4. Listar autores por nacionalidad                    ║");
			System.out.println("║  5. Listar autores por género literario                ║");
			System.out.println("║  6. Ver autores premiados                              ║");
			System.out.println("║  0. ⬅ Volver al menú principal                         ║");
			System.out.println("╚════════════════════════════════════════════════════════╝");
			System.out.print("Seleccione una opción: ");

			int opcion = leerOpcion();

			switch (opcion) {
			case 1 -> autorController.listarAutores();
			case 2 -> {
				System.out.print("Ingrese el ID del autor: ");
				String id = scanner.nextLine();
				autorController.mostrarDetallesAutor(id);
			}
			case 3 -> {
				System.out.print("Ingrese el nombre a buscar: ");
				String nombre = scanner.nextLine();
				autorController.buscarAutoresPorNombre(nombre);
			}
			case 4 -> {
				System.out.print("Ingrese la nacionalidad: ");
				String nacionalidad = scanner.nextLine();
				autorController.listarAutoresPorNacionalidad(nacionalidad);
			}
			case 5 -> {
				System.out.print("Ingrese el género literario: ");
				String genero = scanner.nextLine();
				autorController.listarAutoresPorGenero(genero);
			}
			case 6 -> autorController.listarAutoresPremiados();
			case 0 -> volver = true;
			default -> System.out.println("❌ Opción inválida");
			}
		}
	}

	private void menuLibros() {
		boolean volver = false;
		while (!volver) {
			System.out.println("\n╔════════════════════════════════════════════════════════╗");
			System.out.println("║                  📚 MENÚ LIBROS                         ║");
			System.out.println("╠════════════════════════════════════════════════════════╣");
			System.out.println("║  1. Listar todos los libros                            ║");
			System.out.println("║  2. Ver detalles de un libro                           ║");
			System.out.println("║  3. Buscar libros por título                           ║");
			System.out.println("║  4. Buscar libro por ISBN                              ║");
			System.out.println("║  5. Listar libros por categoría                        ║");
			System.out.println("║  6. Listar libros por editorial                        ║");
			System.out.println("║  7. Listar libros por año                              ║");
			System.out.println("║  8. Listar libros por rango de años                    ║");
			System.out.println("║  9. Listar libros por idioma                           ║");
			System.out.println("║ 10. Ver libros mejor valorados                         ║");
			System.out.println("║ 11. Ver libros con stock bajo                          ║");
			System.out.println("║ 12. Buscar por rango de precio                         ║");
			System.out.println("║  0. ⬅ Volver al menú principal                         ║");
			System.out.println("╚════════════════════════════════════════════════════════╝");
			System.out.print("Seleccione una opción: ");

			int opcion = leerOpcion();

			switch (opcion) {
			case 1 -> libroController.listarLibros();
			case 2 -> {
				System.out.print("Ingrese el ID del libro: ");
				String id = scanner.nextLine();
				libroController.mostrarDetallesLibro(id);
			}
			case 3 -> {
				System.out.print("Ingrese el título a buscar: ");
				String titulo = scanner.nextLine();
				libroController.buscarLibrosPorTitulo(titulo);
			}
			case 4 -> {
				System.out.print("Ingrese el ISBN: ");
				String isbn = scanner.nextLine();
				libroController.buscarLibroPorISBN(isbn);
			}
			case 5 -> {
				System.out.print("Ingrese la categoría: ");
				String categoria = scanner.nextLine();
				libroController.listarLibrosPorCategoria(categoria);
			}
			case 6 -> {
				System.out.print("Ingrese la editorial: ");
				String editorial = scanner.nextLine();
				libroController.listarLibrosPorEditorial(editorial);
			}
			case 7 -> {
				System.out.print("Ingrese el año: ");
				int anio = Integer.parseInt(scanner.nextLine());
				libroController.listarLibrosPorAnio(anio);
			}
			case 8 -> {
				System.out.print("Ingrese el año inicial: ");
				int anioInicio = Integer.parseInt(scanner.nextLine());
				System.out.print("Ingrese el año final: ");
				int anioFin = Integer.parseInt(scanner.nextLine());
				libroController.listarLibrosPorRangoAnios(anioInicio, anioFin);
			}
			case 9 -> {
				System.out.print("Ingrese el idioma: ");
				String idioma = scanner.nextLine();
				libroController.listarLibrosPorIdioma(idioma);
			}
			case 10 -> libroController.listarLibrosMejorValorados();
			case 11 -> libroController.listarLibrosStockBajo();
			case 12 -> {
				System.out.print("Ingrese el precio mínimo: ");
				double precioMin = Double.parseDouble(scanner.nextLine());
				System.out.print("Ingrese el precio máximo: ");
				double precioMax = Double.parseDouble(scanner.nextLine());
				libroController.listarLibrosPorRangoPrecio(precioMin, precioMax);
			}
			case 0 -> volver = true;
			default -> System.out.println("❌ Opción inválida");
			}
		}
	}

	private void menuSocios() {
		boolean volver = false;
		while (!volver) {
			System.out.println("\n╔════════════════════════════════════════════════════════╗");
			System.out.println("║                  👥 MENÚ SOCIOS                         ║");
			System.out.println("╠════════════════════════════════════════════════════════╣");
			System.out.println("║  1. Listar todos los socios                            ║");
			System.out.println("║  2. Ver detalles de un socio                           ║");
			System.out.println("║  3. Buscar socios por nombre                           ║");
			System.out.println("║  4. Buscar socio por DNI                               ║");
			System.out.println("║  5. Listar socios por tipo (Premium/Estándar)          ║");
			System.out.println("║  6. Ver socios con préstamos activos                   ║");
			System.out.println("║  0. ⬅ Volver al menú principal                         ║");
			System.out.println("╚════════════════════════════════════════════════════════╝");
			System.out.print("Seleccione una opción: ");

			int opcion = leerOpcion();

			switch (opcion) {
			case 1 -> socioController.listarSocios();
			case 2 -> {
				System.out.print("Ingrese el ID del socio: ");
				String id = scanner.nextLine();
				socioController.mostrarDetallesSocio(id);
			}
			case 3 -> {
				System.out.print("Ingrese el nombre a buscar: ");
				String nombre = scanner.nextLine();
				socioController.buscarSociosPorNombre(nombre);
			}
			case 4 -> {
				System.out.print("Ingrese el DNI: ");
				String dni = scanner.nextLine();
				socioController.buscarSocioPorDNI(dni);
			}
			case 5 -> {
				System.out.print("Ingrese el tipo (Premium/Estándar): ");
				String tipo = scanner.nextLine();
				socioController.listarSociosPorTipo(tipo);
			}
			case 6 -> socioController.listarSociosConPrestamos();
			case 0 -> volver = true;
			default -> System.out.println("❌ Opción inválida");
			}
		}
	}

	private void menuEstadisticas() {
		boolean volver = false;
		while (!volver) {
			System.out.println("\n╔════════════════════════════════════════════════════════╗");
			System.out.println("║                📊 MENÚ ESTADÍSTICAS                     ║");
			System.out.println("╠════════════════════════════════════════════════════════╣");
			System.out.println("║  1. Resumen general                                    ║");
			System.out.println("║  2. Estadísticas por categoría                         ║");
			System.out.println("║  3. Estadísticas por nacionalidad de autores           ║");
			System.out.println("║  4. Autores más prolíficos                             ║");
			System.out.println("║  5. Libros con más reseñas                             ║");
			System.out.println("║  6. Estadísticas de stock                              ║");
			System.out.println("║  7. Estadísticas por editorial                         ║");
			System.out.println("║  8. Publicaciones por década                           ║");
			System.out.println("║  0. ⬅ Volver al menú principal                         ║");
			System.out.println("╚════════════════════════════════════════════════════════╝");
			System.out.print("Seleccione una opción: ");

			int opcion = leerOpcion();

			switch (opcion) {
			case 1 -> estadisticasController.mostrarResumenGeneral();
			case 2 -> estadisticasController.mostrarEstadisticasPorCategoria();
			case 3 -> estadisticasController.mostrarEstadisticasPorNacionalidad();
			case 4 -> estadisticasController.mostrarAutoresMasProlíficos();
			case 5 -> estadisticasController.mostrarLibrosConMasResenas();
			case 6 -> estadisticasController.mostrarEstadisticasStock();
			case 7 -> estadisticasController.mostrarEstadisticasPorEditorial();
			case 8 -> estadisticasController.mostrarLibrosPorDecada();
			case 0 -> volver = true;
			default -> System.out.println("❌ Opción inválida");
			}
		}
	}

	private void menuBusquedas() {
		boolean volver = false;
		while (!volver) {
			System.out.println("\n╔════════════════════════════════════════════════════════╗");
			System.out.println("║              🔍 MENÚ BÚSQUEDAS AVANZADAS                ║");
			System.out.println("╠════════════════════════════════════════════════════════╣");
			System.out.println("║  1. Buscar autores por nacionalidad                    ║");
			System.out.println("║  2. Buscar autores por género literario                ║");
			System.out.println("║  3. Buscar libros por título                           ║");
			System.out.println("║  4. Buscar libros por editorial                        ║");
			System.out.println("║  5. Buscar libros por rango de años                    ║");
			System.out.println("║  6. Buscar libros por rango de precios                 ║");
			System.out.println("║  7. Buscar socios por tipo                             ║");
			System.out.println("║  8. Ver libros con stock bajo (<10 unidades)           ║");
			System.out.println("║  0. ⬅ Volver al menú principal                         ║");
			System.out.println("╚════════════════════════════════════════════════════════╝");
			System.out.print("Seleccione una opción: ");

			int opcion = leerOpcion();

			switch (opcion) {
			case 1 -> {
				System.out.print("Ingrese la nacionalidad: ");
				String nacionalidad = scanner.nextLine();
				autorController.listarAutoresPorNacionalidad(nacionalidad);
			}
			case 2 -> {
				System.out.print("Ingrese el género literario: ");
				String genero = scanner.nextLine();
				autorController.listarAutoresPorGenero(genero);
			}
			case 3 -> {
				System.out.print("Ingrese el título: ");
				String titulo = scanner.nextLine();
				libroController.buscarLibrosPorTitulo(titulo);
			}
			case 4 -> {
				System.out.print("Ingrese la editorial: ");
				String editorial = scanner.nextLine();
				libroController.listarLibrosPorEditorial(editorial);
			}
			case 5 -> {
				System.out.print("Año inicial: ");
				int anioInicio = Integer.parseInt(scanner.nextLine());
				System.out.print("Año final: ");
				int anioFin = Integer.parseInt(scanner.nextLine());
				libroController.listarLibrosPorRangoAnios(anioInicio, anioFin);
			}
			case 6 -> {
				System.out.print("Precio mínimo: ");
				double precioMin = Double.parseDouble(scanner.nextLine());
				System.out.print("Precio máximo: ");
				double precioMax = Double.parseDouble(scanner.nextLine());
				libroController.listarLibrosPorRangoPrecio(precioMin, precioMax);
			}
			case 7 -> {
				System.out.print("Tipo de socio (Premium/Estándar): ");
				String tipo = scanner.nextLine();
				socioController.listarSociosPorTipo(tipo);
			}
			case 8 -> libroController.listarLibrosStockBajo();
			case 0 -> volver = true;
			default -> System.out.println("❌ Opción inválida");
			}
		}
	}

	private int leerOpcion() {
		try {
			String input = scanner.nextLine();
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
