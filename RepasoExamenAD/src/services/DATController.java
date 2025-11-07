package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import controller.DBConnection;
import model.Alumnos;

public class DATController {

	Firestore db;
	Scanner sc = new Scanner(System.in);
	private File file = new File("alumnos.dat");

	public DATController() {
		db = DBConnection.getFirestore();
	}

	public void saveToDAT() throws InterruptedException, ExecutionException, ParserConfigurationException,
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

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(alumnos);
			System.out.println("Archivo DAT guardado correctamente: " + file.getName());
		} catch (Exception e) {
			System.err.println("Error al guardar el DAT: " + e.getMessage());
		}
	}

	public void readFromDAT() {
		if (!file.exists() || file.length() == 0) {
			System.err.println("[ERROR] No hay archivo DAT disponible");
			return;
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			Object obj = ois.readObject();
			if (!(obj instanceof List<?>)) {
				System.err.println("[ERROR] El contenido del DAT no es una lista");
				return;
			}

			List<?> rawList = (List<?>) obj;
			List<Alumnos> alumnos = new ArrayList<>();
			for (Object item : rawList) {
				if (item instanceof Alumnos) {
					alumnos.add((Alumnos) item);
				} else {
					System.err.println("[WARN] Elemento inesperado en DAT: "
							+ (item == null ? "null" : item.getClass().getName()));
				}
			}

			System.out.println("Alumnos cargados desde DAT:");
			for (Alumnos a : alumnos) {
				System.out.println(" - " + a.getName() + " (" + a.getAge() + " años)");
			}
		} catch (Exception e) {
			System.err.println("Error al leer el DAT: " + e.getMessage());
		}
	}

	public void readFromDATByName() {
		System.out.print("Introduce el nombre del alumno a buscar: ");
		String nombreBuscado = sc.nextLine().trim();

		if (!file.exists() || file.length() == 0) {
			System.err.println("[ERROR] No hay archivo DAT disponible");
			return;
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			Object obj = ois.readObject();
			if (!(obj instanceof List<?>)) {
				System.err.println("[ERROR] El contenido del DAT no es una lista");
				return;
			}

			List<?> rawList = (List<?>) obj;
			List<Alumnos> alumnosEncontrados = new ArrayList<>();

			for (Object item : rawList) {
				if (item instanceof Alumnos) {
					Alumnos a = (Alumnos) item;
					if (a.getName().equalsIgnoreCase(nombreBuscado)) {
						alumnosEncontrados.add(a);
					}
				}
			}

			if (alumnosEncontrados.isEmpty()) {
				System.out.println("No se ha encontrado ningún alumno con el nombre '" + nombreBuscado + "'.");
			} else {
				System.out.println("Alumnos encontrados con el nombre '" + nombreBuscado + "':");
				for (Alumnos a : alumnosEncontrados) {
					System.out.println(" - " + a.getName() + " (" + a.getAge() + " años)");
				}
			}

		} catch (Exception e) {
			System.err.println("[ERROR] Error al leer el archivo DAT: " + e.getMessage());
		}
	}

}
