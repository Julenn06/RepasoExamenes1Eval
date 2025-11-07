package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import controller.DBConnection;
import model.Alumnos;

public class XMLController {

	Firestore db;
	Scanner sc = new Scanner(System.in);
	private File file = new File("alumnos.xml");

	public XMLController() {
		db = DBConnection.getFirestore();
	}

	public void saveToXML() throws InterruptedException, ExecutionException, ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {

		List<Alumnos> alumnos = new ArrayList<>();

		QuerySnapshot querySnapshot = db.collection("alumnos").get().get();

		for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {

			String name = doc.getString("name");
			Long ageLong = doc.getLong("age");
			int age = ageLong != null ? ageLong.intValue() : 0;

			Alumnos alumno = new Alumnos(name, age);
			alumnos.add(alumno);
		}

		DOMImplementation impl = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
		Document docXML = impl.createDocument(null, "Alumnos", null);
		Element root = docXML.getDocumentElement();

		for (Alumnos a : alumnos) {
			Element alumnoElement = docXML.createElement("Alumno");

			Element nameElement = docXML.createElement("Name");
			nameElement.appendChild(docXML.createTextNode(a.getName()));
			alumnoElement.appendChild(nameElement);

			Element ageElement = docXML.createElement("Age");
			ageElement.appendChild(docXML.createTextNode(String.valueOf(a.getAge())));
			alumnoElement.appendChild(ageElement);

			root.appendChild(alumnoElement);
		}

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.STANDALONE, "no");

		DOMSource source = new DOMSource(docXML);
		StreamResult result = new StreamResult(file);
		transformer.transform(source, result);

	}

	public void readFromXML() throws SAXException, IOException, ParserConfigurationException {
		if (!file.exists() || file.length() == 0) {
			System.err.println("[ERROR] No hay archivo XML disponible");
			return;
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = factory.newDocumentBuilder().parse(file);
		doc.getDocumentElement().normalize();

		NodeList listaAlumnos = doc.getElementsByTagName("Alumno");

		List<Alumnos> alumnos = new ArrayList<>();

		for (int i = 0; i < listaAlumnos.getLength(); i++) {
			Node nodo = listaAlumnos.item(i);

			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;

				String nombre = elemento.getElementsByTagName("Name").item(0).getTextContent();

				int edad = Integer.parseInt(elemento.getElementsByTagName("Age").item(0).getTextContent());

				alumnos.add(new Alumnos(nombre, edad));
			}
		}

		System.out.println("Alumnos cargados desde XML:");
		for (Alumnos a : alumnos) {
			System.out.println(" - " + a.getName() + " (" + a.getAge() + " años)");
		}
	}
}
