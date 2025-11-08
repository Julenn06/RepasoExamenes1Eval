package services;

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

public class ReadByID {

	Scanner sc = new Scanner(System.in);

	private File file = new File("clientes.xml");

	public void leerPorId() throws SAXException, IOException, ParserConfigurationException {
		if (!file.exists() || file.length() == 0) {
			System.err.println("[ERROR] No hay archivo XML disponible");
			return;
		}

		System.out.println("Escribe el ID de la venta que quieres buscar:");
		int idBuscado = sc.nextInt();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = factory.newDocumentBuilder().parse(file);
		doc.getDocumentElement().normalize();

		NodeList listaVentas = doc.getElementsByTagName("venta");

		for (int i = 0; i < listaVentas.getLength(); i++) {
			Node nodoVenta = listaVentas.item(i);
			if (nodoVenta.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element venta = (Element) nodoVenta;
			String id = venta.getAttribute("id");

			if (Integer.parseInt(id) == idBuscado) {

				String cliente = getTextContent(venta, "cliente");
				String fecha = getTextContent(venta, "fecha");

				System.out.println("Venta id=" + id + " - Cliente: " + cliente + ", Fecha: " + fecha);

				NodeList productos = venta.getElementsByTagName("producto");
				for (int j = 0; j < productos.getLength(); j++) {
					Node nodoProducto = productos.item(j);
					if (nodoProducto.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}

					Element producto = (Element) nodoProducto;
					String nombre = getTextContent(producto, "nombre");
					String precio = getTextContent(producto, "precio");
					String cantidad = getTextContent(producto, "cantidad");

					System.out.println("    Producto: " + nombre + ", Precio: " + precio + ", Cantidad: " + cantidad);
				}
			}
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
