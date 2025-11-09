package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import model.Asignatura;

/**
 * Controlador para gestión de Asignaturas
 */
public class AsignaturaController {

	private final Firestore db;
	private static final String COLLECTION = "asignaturas";

	public AsignaturaController() {
		this.db = DBConnection.getFirestore();
	}

	public boolean crear(Asignatura asignatura) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document();
			asignatura.setId(docRef.getId());
			ApiFuture<WriteResult> result = docRef.set(asignaturaToMap(asignatura));
			result.get();
			System.out.println("✓ Asignatura creada: " + asignatura.getNombre());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
			return false;
		}
	}

	public List<Asignatura> leerTodas() {
		List<Asignatura> asignaturas = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				asignaturas.add(mapToAsignatura(doc));
			}
			System.out.println("✓ Se encontraron " + asignaturas.size() + " asignaturas");
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return asignaturas;
	}

	public Asignatura leerPorId(String id) {
		try {
			DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();
			if (doc.exists())
				return mapToAsignatura(doc);
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return null;
	}

	public boolean actualizar(Asignatura asignatura) {
		try {
			db.collection(COLLECTION).document(asignatura.getId()).update(asignaturaToMap(asignatura)).get();
			System.out.println("✓ Asignatura actualizada");
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
			return false;
		}
	}

	public boolean eliminar(String id) {
		try {
			db.collection(COLLECTION).document(id).delete().get();
			System.out.println("✓ Asignatura eliminada");
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
			return false;
		}
	}

	public Asignatura buscarPorCodigo(String codigo) {
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("codigo", codigo).get().get();
			if (!snapshot.isEmpty())
				return mapToAsignatura(snapshot.getDocuments().get(0));
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return null;
	}

	public List<Asignatura> filtrarPorCurso(String idCurso) {
		List<Asignatura> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("idCurso", idCurso).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToAsignatura(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Asignatura> filtrarPorProfesor(String idProfesor) {
		List<Asignatura> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("idProfesor", idProfesor).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToAsignatura(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Asignatura> filtrarPorTipo(String tipo) {
		List<Asignatura> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("tipo", tipo).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToAsignatura(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Asignatura> filtrarPorCreditos(int creditosMinimos) {
		List<Asignatura> resultados = new ArrayList<>();
		for (Asignatura a : leerTodas()) {
			if (a.getCreditos() >= creditosMinimos) {
				resultados.add(a);
			}
		}
		return resultados;
	}

	private Map<String, Object> asignaturaToMap(Asignatura a) {
		Map<String, Object> data = new HashMap<>();
		data.put("id", a.getId());
		data.put("codigo", a.getCodigo());
		data.put("nombre", a.getNombre());
		data.put("descripcion", a.getDescripcion());
		data.put("creditos", a.getCreditos());
		data.put("horasSemanales", a.getHorasSemanales());
		data.put("tipo", a.getTipo());
		data.put("idCurso", a.getIdCurso());
		data.put("idProfesor", a.getIdProfesor());
		data.put("aula", a.getAula());
		data.put("horario", a.getHorario());
		data.put("activa", a.isActiva());
		return data;
	}

	private Asignatura mapToAsignatura(DocumentSnapshot doc) {
		Asignatura a = new Asignatura();
		a.setId(doc.getId());
		a.setCodigo(doc.getString("codigo"));
		a.setNombre(doc.getString("nombre"));
		a.setDescripcion(doc.getString("descripcion"));
		a.setCreditos(doc.getLong("creditos") != null ? doc.getLong("creditos").intValue() : 0);
		a.setHorasSemanales(doc.getLong("horasSemanales") != null ? doc.getLong("horasSemanales").intValue() : 0);
		a.setTipo(doc.getString("tipo"));
		a.setIdCurso(doc.getString("idCurso"));
		a.setIdProfesor(doc.getString("idProfesor"));
		a.setAula(doc.getString("aula"));
		a.setHorario(doc.getString("horario"));
		a.setActiva(doc.getBoolean("activa") != null ? doc.getBoolean("activa") : true);
		return a;
	}
}
