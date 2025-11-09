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

import model.Matricula;

/**
 * Controlador para gestión de Matrículas y Calificaciones
 */
public class MatriculaController {

	private final Firestore db;
	private static final String COLLECTION = "matriculas";

	public MatriculaController() {
		this.db = DBConnection.getFirestore();
	}

	public boolean crear(Matricula matricula) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document();
			matricula.setId(docRef.getId());
			ApiFuture<WriteResult> result = docRef.set(matriculaToMap(matricula));
			result.get();
			System.out.println("✓ Matrícula creada con ID: " + matricula.getId());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
			return false;
		}
	}

	public List<Matricula> leerTodas() {
		List<Matricula> matriculas = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				matriculas.add(mapToMatricula(doc));
			}
			System.out.println("✓ Se encontraron " + matriculas.size() + " matrículas");
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return matriculas;
	}

	public Matricula leerPorId(String id) {
		try {
			DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();
			if (doc.exists())
				return mapToMatricula(doc);
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return null;
	}

	public boolean actualizar(Matricula matricula) {
		try {
			db.collection(COLLECTION).document(matricula.getId()).update(matriculaToMap(matricula)).get();
			System.out.println("✓ Matrícula actualizada");
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
			return false;
		}
	}

	public boolean eliminar(String id) {
		try {
			db.collection(COLLECTION).document(id).delete().get();
			System.out.println("✓ Matrícula eliminada");
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
			return false;
		}
	}

	public List<Matricula> filtrarPorAlumno(String idAlumno) {
		List<Matricula> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("idAlumno", idAlumno).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToMatricula(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Matricula> filtrarPorAsignatura(String idAsignatura) {
		List<Matricula> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("idAsignatura", idAsignatura).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToMatricula(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Matricula> filtrarAprobadas() {
		List<Matricula> resultados = new ArrayList<>();
		for (Matricula m : leerTodas()) {
			if (m.estaAprobada()) {
				resultados.add(m);
			}
		}
		return resultados;
	}

	public List<Matricula> filtrarSuspensas() {
		List<Matricula> resultados = new ArrayList<>();
		for (Matricula m : leerTodas()) {
			if (!m.estaAprobada()) {
				resultados.add(m);
			}
		}
		return resultados;
	}

	public List<Matricula> filtrarPorCalificacion(String calificacion) {
		List<Matricula> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("calificacion", calificacion).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToMatricula(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Matricula> filtrarConvalidadas() {
		List<Matricula> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("convalidada", true).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToMatricula(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Matricula> filtrarPorConvocatoria(int convocatoria) {
		List<Matricula> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("convocatoria", convocatoria).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToMatricula(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Matricula> filtrarPorNotaMinima(double notaMinima) {
		List<Matricula> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereGreaterThanOrEqualTo("notaFinal", notaMinima).get()
					.get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToMatricula(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	private Map<String, Object> matriculaToMap(Matricula m) {
		Map<String, Object> data = new HashMap<>();
		data.put("id", m.getId());
		data.put("idAlumno", m.getIdAlumno());
		data.put("idAsignatura", m.getIdAsignatura());
		data.put("fechaMatricula", m.getFechaMatricula());
		data.put("notaParcial1", m.getNotaParcial1());
		data.put("notaParcial2", m.getNotaParcial2());
		data.put("notaParcial3", m.getNotaParcial3());
		data.put("notaFinal", m.getNotaFinal());
		data.put("calificacion", m.getCalificacion());
		data.put("convocatoria", m.getConvocatoria());
		data.put("fechaExamen", m.getFechaExamen());
		data.put("convalidada", m.isConvalidada());
		data.put("observaciones", m.getObservaciones());
		return data;
	}

	private Matricula mapToMatricula(DocumentSnapshot doc) {
		Matricula m = new Matricula();
		m.setId(doc.getId());
		m.setIdAlumno(doc.getString("idAlumno"));
		m.setIdAsignatura(doc.getString("idAsignatura"));
		m.setFechaMatricula(doc.getDate("fechaMatricula"));
		m.setNotaParcial1(doc.getDouble("notaParcial1") != null ? doc.getDouble("notaParcial1") : 0.0);
		m.setNotaParcial2(doc.getDouble("notaParcial2") != null ? doc.getDouble("notaParcial2") : 0.0);
		m.setNotaParcial3(doc.getDouble("notaParcial3") != null ? doc.getDouble("notaParcial3") : 0.0);
		m.setNotaFinal(doc.getDouble("notaFinal") != null ? doc.getDouble("notaFinal") : 0.0);
		m.setCalificacion(doc.getString("calificacion"));
		m.setConvocatoria(doc.getLong("convocatoria") != null ? doc.getLong("convocatoria").intValue() : 1);
		m.setFechaExamen(doc.getDate("fechaExamen"));
		m.setConvalidada(doc.getBoolean("convalidada") != null ? doc.getBoolean("convalidada") : false);
		m.setObservaciones(doc.getString("observaciones"));
		return m;
	}
}
