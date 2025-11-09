package controller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadByProductName {

	Scanner sc = new Scanner(System.in);

	private File file = new File("clientes.xml");

	public void leerPorNombreProducto() throws SAXException, IOException, ParserConfigurationException {
		if (!file.exists() || file.length() == 0) {
			System.err.println("[ERROR] No hay archivo XML disponible");
			return;
		}

		System.out.println("Escribe el Nombre del Producto que quieres buscar:");
		String productoBuscado = sc.nextLine().trim();
		if (productoBuscado.isEmpty()) {
			System.err.println("[ERROR] Nombre de producto vacío. Cancelando búsqueda.");
			return;
		}
		String buscLower = productoBuscado.toLowerCase();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = factory.newDocumentBuilder().parse(file);
		doc.getDocumentElement().normalize();

		NodeList listaVentas = doc.getElementsByTagName("venta");

		boolean foundAny = false;
		for (int i = 0; i < listaVentas.getLength(); i++) {
			Node nodoVenta = listaVentas.item(i);
			if (nodoVenta.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element venta = (Element) nodoVenta;
			String id = venta.getAttribute("id");

			String cliente = getTextContent(venta, "cliente");
			String fecha = getTextContent(venta, "fecha");

			NodeList productos = venta.getElementsByTagName("producto");
			boolean ventaHasMatch = false;

			for (int j = 0; j < productos.getLength(); j++) {
				Node nodoProducto = productos.item(j);
				if (nodoProducto.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}

				Element producto = (Element) nodoProducto;
				String nombre = getTextContent(producto, "nombre");
				String precio = getTextContent(producto, "precio");
				String cantidad = getTextContent(producto, "cantidad");

				if (nombre.toLowerCase().contains(buscLower)) {
					if (!ventaHasMatch) {
						System.out.println("Venta id=" + id + " - Cliente: " + cliente + ", Fecha: " + fecha);
						ventaHasMatch = true;
					}
					System.out.println("    Producto: " + nombre + ", Precio: " + precio + ", Cantidad: " + cantidad);
					foundAny = true;
				}
			}
		}

		if (!foundAny) {
			System.out.println("No se encontraron productos que coincidan con '" + productoBuscado + "'.");
		}
	}

	private String getTextContent(Element parent, String tagName) {
		NodeList nodes = parent.getElementsByTagName(tagName);
		if (nodes == null || nodes.getLength() == 0 || nodes.item(0) == null) {
			return "";
		}
		String text = nodes.item(0).getTextContent();
		return text == null ? "" : text.trim();
	}

}
