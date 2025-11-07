package controller;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import services.AlumnoController;
import services.DATController;
import services.XMLController;
import view.FirstView;

public class Main {

	public static void main(String[] args)
			throws InterruptedException, ExecutionException, ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException, SAXException, IOException {

		Scanner sc = new Scanner(System.in);
		int opcion = 0;

		DBConnection.initialize();

		AlumnoController AlumnoController = new AlumnoController();
		XMLController XMLController = new XMLController();
		DATController DATController = new DATController();

		do {
			System.out.println("1. Ver Alumnos");
			System.out.println("2. Añadir Alumno");
			System.out.println("3. Editar Alumno");
			System.out.println("4. Eliminar Alumno");
			System.out.println("5. Ver usuario por nombre");
			System.out.println("6. Guardar en XML");
			System.out.println("7. Leer desde XML");
			System.out.println("8. Leer desde XML por nombre");
			System.out.println("9. Guardar en DAT");
			System.out.println("10. Leer desde DAT");
			System.out.println("11. Leer desde DAT por nombre");
			System.out.println("12. Abrir vista grafica");
			System.out.println("13. Salir");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				AlumnoController.viewAlumnos();
				break;
			case 2:
				AlumnoController.addAlumno();
				break;
			case 3:
				AlumnoController.editAlumno();
				break;
			case 4:
				AlumnoController.deleteAlumno();
				break;

			case 5:
				AlumnoController.viewAlumnoByName();
				break;
			case 6:
				XMLController.saveToXML();
				break;
			case 7:
				XMLController.readFromXML();
				break;
			case 8:
				XMLController.readFromXMLByName();
				break;
			case 9:
				DATController.saveToDAT();
				break;
			case 10:
				DATController.readFromDAT();
				break;
			case 11:
				DATController.readFromDATByName();
				break;
			case 12:
				FirstView view = new FirstView();
				view.setVisible(true);
				break;
			case 13:
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opción no válida");
				break;
			}

		} while (opcion != 13);

		sc.close();

	}

}
