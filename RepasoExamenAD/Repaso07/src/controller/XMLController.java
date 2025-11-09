package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Autor;
import model.Biblioteca;
import model.Capitulo;
import model.Libro;
import model.Premio;
import model.Prestamo;
import model.Resena;
import model.Socio;

/**
 * Controlador principal para manipular el XML de la biblioteca
 */
public class XMLController {
	private static final String XML_PATH = "biblioteca.xml";
	private Document document;
	private Biblioteca biblioteca;

	public XMLController() {
		cargarXML();
	}

	/**
	 * Carga el documento XML en memoria
	 */
	public void cargarXML() {
		try {
			File xmlFile = new File(XML_PATH);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();

			// Cargar información de la biblioteca
			Element root = document.getDocumentElement();
			biblioteca = new Biblioteca(getTextContent(root, "nombre"), getTextContent(root, "direccion"),
					getTextContent(root, "telefono"), getTextContent(root, "email"),
					Integer.parseInt(getTextContent(root, "anioFundacion")));

			System.out.println("✓ XML cargado correctamente");
		} catch (Exception e) {
			System.err.println("Error al cargar XML: " + e.getMessage());
		}
	}

	/**
	 * Guarda los cambios en el archivo XML
	 */
	public void guardarXML() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(XML_PATH));
			transformer.transform(source, result);

			System.out.println("✓ XML guardado correctamente");
		} catch (Exception e) {
			System.err.println("Error al guardar XML: " + e.getMessage());
		}
	}

	/**
	 * Obtiene todos los autores del XML
	 */
	public List<Autor> obtenerAutores() {
		List<Autor> autores = new ArrayList<>();
		NodeList nodeList = document.getElementsByTagName("autor");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element autorElement = (Element) nodeList.item(i);
			Autor autor = parsearAutor(autorElement);
			autores.add(autor);
		}

		return autores;
	}

	/**
	 * Parsea un elemento autor del XML
	 */
	private Autor parsearAutor(Element autorElement) {
		Autor autor = new Autor();
		autor.setId(autorElement.getAttribute("id"));
		autor.setNombre(getTextContent(autorElement, "nombre"));
		autor.setNacionalidad(getTextContent(autorElement, "nacionalidad"));
		autor.setFechaNacimiento(getTextContent(autorElement, "fechaNacimiento"));
		autor.setFechaFallecimiento(getTextContent(autorElement, "fechaFallecimiento"));
		autor.setGeneroLiterario(getTextContent(autorElement, "generoLiterario"));
		autor.setBiografia(getTextContent(autorElement, "biografia"));

		// Parsear premios
		NodeList premiosNode = autorElement.getElementsByTagName("premio");
		for (int i = 0; i < premiosNode.getLength(); i++) {
			Element premioElement = (Element) premiosNode.item(i);
			Premio premio = new Premio(getTextContent(premioElement, "nombre"),
					Integer.parseInt(getTextContent(premioElement, "anio")));
			autor.addPremio(premio);
		}

		// Parsear libros
		NodeList librosNode = autorElement.getElementsByTagName("libro");
		for (int i = 0; i < librosNode.getLength(); i++) {
			Element libroElement = (Element) librosNode.item(i);
			Libro libro = parsearLibro(libroElement, autor.getId());
			autor.addLibro(libro);
		}

		return autor;
	}

	/**
	 * Parsea un elemento libro del XML
	 */
	private Libro parsearLibro(Element libroElement, String autorId) {
		Libro libro = new Libro();
		libro.setId(libroElement.getAttribute("id"));
		libro.setTitulo(getTextContent(libroElement, "titulo"));
		libro.setIsbn(getTextContent(libroElement, "isbn"));
		libro.setAnioPublicacion(Integer.parseInt(getTextContent(libroElement, "anioPublicacion")));
		libro.setEditorial(getTextContent(libroElement, "editorial"));
		libro.setNumeroPaginas(Integer.parseInt(getTextContent(libroElement, "numeroPaginas")));
		libro.setIdioma(getTextContent(libroElement, "idioma"));
		libro.setPrecio(Double.parseDouble(getTextContent(libroElement, "precio")));
		libro.setStock(Integer.parseInt(getTextContent(libroElement, "stock")));
		libro.setCategoria(getTextContent(libroElement, "categoria"));
		libro.setValoracion(Double.parseDouble(getTextContent(libroElement, "valoracion")));
		libro.setDescripcion(getTextContent(libroElement, "descripcion"));
		libro.setAutorId(autorId);

		// Parsear capítulos
		NodeList capitulosNode = libroElement.getElementsByTagName("capitulo");
		for (int i = 0; i < capitulosNode.getLength(); i++) {
			Element capituloElement = (Element) capitulosNode.item(i);
			Capitulo capitulo = new Capitulo(Integer.parseInt(capituloElement.getAttribute("numero")),
					getTextContent(capituloElement, "titulo"),
					Integer.parseInt(getTextContent(capituloElement, "paginas")),
					getTextContent(capituloElement, "resumen"));
			libro.addCapitulo(capitulo);
		}

		// Parsear reseñas
		NodeList resenasNode = libroElement.getElementsByTagName("resena");
		for (int i = 0; i < resenasNode.getLength(); i++) {
			Element resenaElement = (Element) resenasNode.item(i);
			Resena resena = new Resena(getTextContent(resenaElement, "usuario"),
					Integer.parseInt(getTextContent(resenaElement, "puntuacion")),
					getTextContent(resenaElement, "fecha"), getTextContent(resenaElement, "comentario"));
			libro.addResena(resena);
		}

		return libro;
	}

	/**
	 * Obtiene todos los libros del XML (todos los autores)
	 */
	public List<Libro> obtenerTodosLosLibros() {
		List<Libro> libros = new ArrayList<>();
		List<Autor> autores = obtenerAutores();

		for (Autor autor : autores) {
			libros.addAll(autor.getLibros());
		}

		return libros;
	}

	/**
	 * Obtiene todos los socios del XML
	 */
	public List<Socio> obtenerSocios() {
		List<Socio> socios = new ArrayList<>();
		NodeList nodeList = document.getElementsByTagName("socio");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element socioElement = (Element) nodeList.item(i);
			Socio socio = parsearSocio(socioElement);
			socios.add(socio);
		}

		return socios;
	}

	/**
	 * Parsea un elemento socio del XML
	 */
	private Socio parsearSocio(Element socioElement) {
		Socio socio = new Socio();
		socio.setId(socioElement.getAttribute("id"));
		socio.setNombre(getTextContent(socioElement, "nombre"));
		socio.setDni(getTextContent(socioElement, "dni"));
		socio.setEmail(getTextContent(socioElement, "email"));
		socio.setTelefono(getTextContent(socioElement, "telefono"));
		socio.setFechaInscripcion(getTextContent(socioElement, "fechaInscripcion"));
		socio.setTipoSocio(getTextContent(socioElement, "tipoSocio"));
		socio.setEstado(getTextContent(socioElement, "estado"));
		socio.setPrestamosActivos(Integer.parseInt(getTextContent(socioElement, "prestamosActivos")));

		// Parsear historial de préstamos
		NodeList prestamosNode = socioElement.getElementsByTagName("prestamo");
		for (int i = 0; i < prestamosNode.getLength(); i++) {
			Element prestamoElement = (Element) prestamosNode.item(i);
			Prestamo prestamo = new Prestamo(getTextContent(prestamoElement, "libroId"),
					getTextContent(prestamoElement, "fechaPrestamo"),
					getTextContent(prestamoElement, "fechaDevolucion"), getTextContent(prestamoElement, "estado"));
			socio.addPrestamo(prestamo);
		}

		return socio;
	}

	/**
	 * Busca un autor por ID
	 */
	public Autor buscarAutorPorId(String id) {
		List<Autor> autores = obtenerAutores();
		for (Autor autor : autores) {
			if (autor.getId().equals(id)) {
				return autor;
			}
		}
		return null;
	}

	/**
	 * Busca un libro por ID
	 */
	public Libro buscarLibroPorId(String id) {
		List<Libro> libros = obtenerTodosLosLibros();
		for (Libro libro : libros) {
			if (libro.getId().equals(id)) {
				return libro;
			}
		}
		return null;
	}

	/**
	 * Busca un socio por ID
	 */
	public Socio buscarSocioPorId(String id) {
		List<Socio> socios = obtenerSocios();
		for (Socio socio : socios) {
			if (socio.getId().equals(id)) {
				return socio;
			}
		}
		return null;
	}

	/**
	 * Obtiene información de la biblioteca
	 */
	public Biblioteca obtenerBiblioteca() {
		return biblioteca;
	}

	/**
	 * Método auxiliar para obtener el contenido de texto de un elemento
	 */
	private String getTextContent(Element parent, String tagName) {
		NodeList nodeList = parent.getElementsByTagName(tagName);
		if (nodeList.getLength() > 0) {
			Node node = nodeList.item(0);
			if (node != null) {
				String content = node.getTextContent();
				return content != null ? content.trim() : "";
			}
		}
		return "";
	}

	public Document getDocument() {
		return document;
	}
}
