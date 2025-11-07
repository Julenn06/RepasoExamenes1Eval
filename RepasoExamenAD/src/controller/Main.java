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
			System.out.println("5. Guardar en XML");
			System.out.println("6. Leer desde XML");
			System.out.println("7. Guardar en DAT");
			System.out.println("8. Leer desde DAT");
			System.out.println("9. Abrir vista grafica");
			System.out.println("10. Salir");
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
				XMLController.saveToXML();
				break;
			case 6:
				XMLController.readFromXML();
				break;
			case 7:
				DATController.saveToDAT();
				break;
			case 8:
				DATController.readFromDAT();
				break;
			case 9:
				view.FirstView.main(null);
				break;
			case 10:
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opción no válida");
				break;
			}

		} while (opcion != 10);

		sc.close();

	}

}
