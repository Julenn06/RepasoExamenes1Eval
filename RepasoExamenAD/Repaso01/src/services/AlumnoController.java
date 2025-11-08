package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import controller.DBConnection;
import model.Alumnos;

public class AlumnoController {

	private final Firestore db;
	Scanner sc = new Scanner(System.in);
	private String nombre;

	public AlumnoController() {
		this.db = DBConnection.getFirestore();
	}

	public void viewAlumnos() throws InterruptedException, ExecutionException {

		List<Alumnos> alumnos = new ArrayList<>();

		QuerySnapshot querySnapshot = db.collection("alumnos").get().get();

		for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {

			String name = doc.getString("name");
			Long ageLong = doc.getLong("age");
			int age = ageLong != null ? ageLong.intValue() : 0;

			Alumnos alumno = new Alumnos(name, age);
			alumnos.add(alumno);
		}

		int total = 1;
		
		for (Alumnos alumno : alumnos) {
			System.out.println(total + ". Alumno: " + alumno.getName() + ", Edad: " + alumno.getAge());
			total++;
		}
		return;
	}

	public void addAlumno() {

		String nombre;
		int edad;

		System.out.println("Introduce el nombre del alumno:");
		nombre = sc.nextLine();
		System.out.println("Introduce la edad del alumno:");
		edad = Integer.parseInt(sc.nextLine());

		Alumnos nuevoAlumno = new Alumnos(nombre, edad);

		db.collection("alumnos").add(nuevoAlumno);

	}

	public void editAlumno() throws InterruptedException, ExecutionException {

		viewAlumnos();

		System.out.println("Selecciona el nombre del alumno a editar:");
		nombre = sc.nextLine();

		QuerySnapshot querySnapshot = db.collection("alumnos").get().get();

		for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
			if (doc.getString("name").equalsIgnoreCase(nombre)) {
				System.out.println("Introduce el nuevo nombre:");
				String nuevoNombre = sc.nextLine();
				System.out.println("Introduce la nueva edad:");
				int nuevaEdad = Integer.parseInt(sc.nextLine());

				db.collection("alumnos").document(doc.getId()).update("name", nuevoNombre, "age", nuevaEdad);
				System.out.println("Alumno actualizado correctamente.");
				return;
			}
		}

	}

	public void deleteAlumno() throws InterruptedException, ExecutionException {

		viewAlumnos();

		System.out.println("Selecciona el nombre del alumno a eliminar:");
		nombre = sc.nextLine();
		QuerySnapshot querySnapshot = db.collection("alumnos").get().get();
		for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
			if (doc.getString("name").equalsIgnoreCase(nombre)) {
				db.collection("alumnos").document(doc.getId()).delete();
				System.out.println("Alumno eliminado correctamente.");
				return;
			}
		}
	}

	public void viewAlumnoByName() throws InterruptedException, ExecutionException {
		List<Alumnos> alumnos = new ArrayList<>();

		System.out.println("Introduce el nombre del alumno a buscar:");
		nombre = sc.nextLine();

		QuerySnapshot querySnapshot = db.collection("alumnos").whereEqualTo("name", nombre).get().get();

		for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {

			String name = doc.getString("name");
			Long ageLong = doc.getLong("age");
			int age = ageLong != null ? ageLong.intValue() : 0;

			Alumnos alumno = new Alumnos(name, age);
			alumnos.add(alumno);
		}
		
		int total = 1;

		for (Alumnos alumno : alumnos) {
			System.out.println(total + ". Alumno: " + alumno.getName() + ", Edad: " + alumno.getAge());
			total++;
		}
		return;
	}

}
