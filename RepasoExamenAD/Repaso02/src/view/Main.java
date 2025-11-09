package view;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.DBConnection;
import services.ReadAll;
import services.ReadByID;
import services.ReadByName;
import services.ReadByProductName;

public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {

		Scanner sc = new Scanner(System.in);
		
		DBConnection.initialize();

		ReadAll readAll = new ReadAll();
		ReadByID readByID = new ReadByID();
		ReadByName readByName = new ReadByName();
		ReadByProductName readByProductName = new ReadByProductName();
		int opcion = 0;
		do {
			System.out.println("Leer xml largo");
			System.out.println("================");
			System.out.println("1. Leer Todo");
			System.out.println("2. Leer por ID");
			System.out.println("3. Leer por Nombre");
			System.out.println("4. Leer por Nombre del Producto");
			System.out.println("5. Salir");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				readAll.leerTodo();
				break;
			case 2:
				readByID.leerPorId();
				break;
			case 3:
				readByName.leerPorNombre();
				break;
			case 4:
				readByProductName.leerPorNombreProducto();
				break;
			case 5:
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opcion no valida");
				break;
			}
		} while (opcion != 5);
		sc.close();
	}
}
