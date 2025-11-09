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

import model.Curso;

/**
 * CursosController - Gestión CRUD completa de Cursos en Firestore.
 * 
 * Colección: "cursos"
 * 
 * Operaciones implementadas: 1. crear(Curso) - Crea un nuevo documento con ID
 * autogenerado 2. leerTodos() - Obtiene todos los cursos 3. leerPorId(String) -
 * Busca un curso por su ID 4. actualizar(Curso) - Actualiza un curso existente
 * 5. eliminar(String) - Elimina un curso por su ID
 * 
 * Uso en examen: demostrar gestión de segunda colección y relaciones
 */
public class CursosController {

	private static final String COLLECTION_NAME = "cursos";
	private Firestore db;

	public CursosController() {
		this.db = DBConnection.getFirestore();
	}

	/**
	 * Crear un nuevo curso en Firestore. El ID se genera automáticamente.
	 * 
	 * @param curso objeto Curso a crear
	 * @return el ID del documento creado, o null si hay error
	 */
	public String crear(Curso curso) {
		try {
			// Crear documento con ID autogenerado
			DocumentReference docRef = db.collection(COLLECTION_NAME).document();
			String id = docRef.getId();
			curso.setId(id);

			// Convertir objeto a Map
			Map<String, Object> data = new HashMap<>();
			data.put("id", curso.getId());
			data.put("nombre", curso.getNombre());
			data.put("descripcion", curso.getDescripcion() != null ? curso.getDescripcion() : "");

			// Escribir en Firestore
			ApiFuture<WriteResult> result = docRef.set(data);
			result.get();

			System.out.println("[INFO] Curso creado con ID: " + id);
			return id;

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al crear curso: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Leer todos los cursos de la colección.
	 * 
	 * @return lista de todos los cursos
	 */
	public List<Curso> leerTodos() {
		List<Curso> cursos = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> query = db.collection(COLLECTION_NAME).get();
			QuerySnapshot querySnapshot = query.get();

			for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
				Curso curso = documentToCurso(document);
				cursos.add(curso);
			}

			System.out.println("[INFO] Se encontraron " + cursos.size() + " cursos");

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al leer cursos: " + e.getMessage());
		}
		return cursos;
	}

	/**
	 * Leer un curso por su ID.
	 * 
	 * @param id identificador del documento
	 * @return objeto Curso, o null si no existe
	 */
	public Curso leerPorId(String id) {
		try {
			DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
			ApiFuture<DocumentSnapshot> future = docRef.get();
			DocumentSnapshot document = future.get();

			if (document.exists()) {
				return documentToCurso(document);
			} else {
				System.out.println("[INFO] No se encontró curso con ID: " + id);
				return null;
			}

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al leer curso por ID: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Actualizar un curso existente.
	 * 
	 * @param curso objeto con los nuevos datos (debe tener ID)
	 * @return true si se actualizó correctamente
	 */
	public boolean actualizar(Curso curso) {
		if (curso.getId() == null || curso.getId().isEmpty()) {
			System.err.println("[ERROR] El curso debe tener un ID para actualizar");
			return false;
		}

		try {
			DocumentReference docRef = db.collection(COLLECTION_NAME).document(curso.getId());

			// Verificar que existe
			if (!docRef.get().get().exists()) {
				System.err.println("[ERROR] No existe curso con ID: " + curso.getId());
				return false;
			}

			// Preparar datos actualizados
			Map<String, Object> data = new HashMap<>();
			data.put("nombre", curso.getNombre());
			data.put("descripcion", curso.getDescripcion() != null ? curso.getDescripcion() : "");

			// Actualizar documento
			ApiFuture<WriteResult> result = docRef.update(data);
			result.get();

			System.out.println("[INFO] Curso actualizado: " + curso.getId());
			return true;

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al actualizar curso: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Eliminar un curso por su ID. Nota: en un caso real, deberías
	 * verificar/actualizar las referencias en alumnos.
	 * 
	 * @param id identificador del documento a eliminar
	 * @return true si se eliminó correctamente
	 */
	public boolean eliminar(String id) {
		try {
			DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);

			// Verificar que existe
			if (!docRef.get().get().exists()) {
				System.err.println("[ERROR] No existe curso con ID: " + id);
				return false;
			}

			// Eliminar documento
			ApiFuture<WriteResult> result = docRef.delete();
			result.get();

			System.out.println("[INFO] Curso eliminado: " + id);
			System.out.println("[WARN] Recuerda actualizar las referencias en alumnos si es necesario");
			return true;

		} catch (InterruptedException | ExecutionException e) {
			System.err.println("[ERROR] Error al eliminar curso: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Helper: convertir DocumentSnapshot a objeto Curso.
	 */
	private Curso documentToCurso(DocumentSnapshot document) {
		Curso curso = new Curso();
		curso.setId(document.getId());
		curso.setNombre(document.getString("nombre"));
		curso.setDescripcion(document.getString("descripcion"));
		return curso;
	}
}
