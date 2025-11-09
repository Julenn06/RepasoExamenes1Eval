package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import model.Alumnos;

/**
 * AlumnosController - Gestión CRUD completa de Alumnos en Firestore.
 * 
 * Colección: "alumnos"
 * 
 * Operaciones implementadas: 1. crear(Alumnos) - Crea un nuevo documento con ID
 * autogenerado 2. leerTodos() - Obtiene todos los alumnos de la colección 3.
 * leerPorId(String) - Busca un alumno por su ID 4. leerPorNombre(String) -
 * Busca alumnos cuyo nombre contenga el fragmento (case-insensitive) 5.
 * filtrarPorEdad(int, int) - Obtiene alumnos en un rango de edad 6.
 * filtrarPorCurso(String) - Obtiene alumnos de un curso específico 7.
 * actualizar(Alumnos) - Actualiza un documento existente 8. eliminar(String) -
 * Elimina un alumno por su ID
 * 
 * Uso en examen: ejemplos claros de operaciones Firestore con comentarios
 */
public class AlumnosController {

	private static final String COLLECTION_NAME = "alumnos";
	private Firestore db;

	public AlumnosController() {
		this.db = DBConnection.getFirestore();
	}

	/**
	 * Crear un nuevo alumno en Firestore. El ID se genera automáticamente.
	 * 
	 * @param alumno objeto Alumnos a crear
	 * @return el ID del documento creado, o null si hay error
	 */
	public String crear(Alumnos alumno) {
		try {
			// Crear documento con ID autogenerado
			DocumentReference docRef = db.collection(COLLECTION_NAME).document();
			String id = docRef.getId();
			alumno.setId(id);

			// Convertir objeto a Map para Firestore
			Map<String, Object> data = new HashMap<>();
			data.put("id", alumno.getId());
			data.put("name", alumno.getName());
			data.put("age", alumno.getAge());
			data.put("birthDate", alumno.getBirthDate()); // Firestore soporta Date directamente
			data.put("idCurso", alumno.getIdCurso() != null ? alumno.getIdCurso() : "");

			// Escribir en Firestore (operación asíncrona)
			ApiFuture<WriteResult> result = docRef.set(data);
			result.get(); // Esperar a que se complete

			System.out.println("[INFO] Alumno creado con ID: " + id);
			return id;

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al crear alumno: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Leer todos los alumnos de la colección.
	 * 
	 * @return lista de todos los alumnos
	 */
	public List<Alumnos> leerTodos() {
		List<Alumnos> alumnos = new ArrayList<>();
		try {
			// Obtener todos los documentos de la colección
			ApiFuture<QuerySnapshot> query = db.collection(COLLECTION_NAME).get();
			QuerySnapshot querySnapshot = query.get();

			// Convertir cada documento a objeto Alumnos
			for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
				Alumnos alumno = documentToAlumno(document);
				alumnos.add(alumno);
			}

			System.out.println("[INFO] Se encontraron " + alumnos.size() + " alumnos");

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al leer alumnos: " + e.getMessage());
		}
		return alumnos;
	}

	/**
	 * Leer un alumno por su ID.
	 * 
	 * @param id identificador del documento
	 * @return objeto Alumnos, o null si no existe
	 */
	public Alumnos leerPorId(String id) {
		try {
			DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
			ApiFuture<DocumentSnapshot> future = docRef.get();
			DocumentSnapshot document = future.get();

			if (document.exists()) {
				return documentToAlumno(document);
			} else {
				System.out.println("[INFO] No se encontró alumno con ID: " + id);
				return null;
			}

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al leer alumno por ID: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Buscar alumnos por nombre (búsqueda parcial, case-insensitive). Nota:
	 * Firestore no tiene búsqueda full-text nativa, así que se hace client-side.
	 * 
	 * @param nombreFragmento fragmento del nombre a buscar
	 * @return lista de alumnos que coinciden
	 */
	public List<Alumnos> leerPorNombre(String nombreFragmento) {
		List<Alumnos> resultado = new ArrayList<>();
		List<Alumnos> todos = leerTodos();

		// Filtrar client-side (alternativa: usar Algolia o similar para búsqueda
		// compleja)
		String query = nombreFragmento.toLowerCase();
		for (Alumnos a : todos) {
			if (a.getName() != null && a.getName().toLowerCase().contains(query)) {
				resultado.add(a);
			}
		}

		System.out
				.println("[INFO] Se encontraron " + resultado.size() + " alumnos con nombre '" + nombreFragmento + "'");
		return resultado;
	}

	/**
	 * Filtrar alumnos por rango de edad. Usa consultas Firestore con
	 * whereGreaterThanOrEqualTo y whereLessThanOrEqualTo.
	 * 
	 * @param edadMin edad mínima (inclusive)
	 * @param edadMax edad máxima (inclusive)
	 * @return lista de alumnos en el rango
	 */
	public List<Alumnos> filtrarPorEdad(int edadMin, int edadMax) {
		List<Alumnos> alumnos = new ArrayList<>();
		try {
			// Consulta con filtros (importante: Firestore requiere índice para múltiples
			// where)
			ApiFuture<QuerySnapshot> query = db.collection(COLLECTION_NAME).whereGreaterThanOrEqualTo("age", edadMin)
					.whereLessThanOrEqualTo("age", edadMax).get();

			QuerySnapshot querySnapshot = query.get();

			for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
				alumnos.add(documentToAlumno(document));
			}

			System.out.println("[INFO] Se encontraron " + alumnos.size() + " alumnos entre " + edadMin + " y " + edadMax
					+ " años");

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al filtrar por edad: " + e.getMessage());
		}
		return alumnos;
	}

	/**
	 * Filtrar alumnos por curso (referencia). Demuestra relaciones entre
	 * colecciones.
	 * 
	 * @param idCurso ID del curso
	 * @return lista de alumnos del curso
	 */
	public List<Alumnos> filtrarPorCurso(String idCurso) {
		List<Alumnos> alumnos = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> query = db.collection(COLLECTION_NAME).whereEqualTo("idCurso", idCurso).get();

			QuerySnapshot querySnapshot = query.get();

			for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
				alumnos.add(documentToAlumno(document));
			}

			System.out.println("[INFO] Se encontraron " + alumnos.size() + " alumnos en el curso " + idCurso);

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al filtrar por curso: " + e.getMessage());
		}
		return alumnos;
	}

	/**
	 * Actualizar un alumno existente. Requiere que el objeto tenga el ID seteado.
	 * 
	 * @param alumno objeto con los nuevos datos (debe tener ID)
	 * @return true si se actualizó correctamente
	 */
	public boolean actualizar(Alumnos alumno) {
		if (alumno.getId() == null || alumno.getId().isEmpty()) {
			System.err.println("[ERROR] El alumno debe tener un ID para actualizar");
			return false;
		}

		try {
			DocumentReference docRef = db.collection(COLLECTION_NAME).document(alumno.getId());

			// Verificar que existe
			if (!docRef.get().get().exists()) {
				System.err.println("[ERROR] No existe alumno con ID: " + alumno.getId());
				return false;
			}

			// Preparar datos actualizados
			Map<String, Object> data = new HashMap<>();
			data.put("name", alumno.getName());
			data.put("age", alumno.getAge());
			data.put("birthDate", alumno.getBirthDate());
			data.put("idCurso", alumno.getIdCurso() != null ? alumno.getIdCurso() : "");

			// Actualizar documento
			ApiFuture<WriteResult> result = docRef.update(data);
			result.get();

			System.out.println("[INFO] Alumno actualizado: " + alumno.getId());
			return true;

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al actualizar alumno: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Eliminar un alumno por su ID.
	 * 
	 * @param id identificador del documento a eliminar
	 * @return true si se eliminó correctamente
	 */
	public boolean eliminar(String id) {
		try {
			DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);

			// Verificar que existe
			if (!docRef.get().get().exists()) {
				System.err.println("[ERROR] No existe alumno con ID: " + id);
				return false;
			}

			// Eliminar documento
			ApiFuture<WriteResult> result = docRef.delete();
			result.get();

			System.out.println("[INFO] Alumno eliminado: " + id);
			return true;

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al eliminar alumno: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Helper: convertir DocumentSnapshot a objeto Alumnos. Maneja valores nulos y
	 * conversiones de tipos.
	 */
	private Alumnos documentToAlumno(DocumentSnapshot document) {
		Alumnos alumno = new Alumnos();
		alumno.setId(document.getId());
		alumno.setName(document.getString("name"));

		// Age puede ser Long o Integer dependiendo de cómo se guardó
		Object ageObj = document.get("age");
		if (ageObj instanceof Long) {
			alumno.setAge(((Long) ageObj).intValue());
		} else if (ageObj instanceof Integer) {
			alumno.setAge((Integer) ageObj);
		}

		// Date se guarda como Timestamp en Firestore
		alumno.setBirthDate(document.getDate("birthDate"));
		alumno.setIdCurso(document.getString("idCurso"));

		return alumno;
	}
}
