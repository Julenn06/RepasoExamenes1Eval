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

import model.Profesor;

/**
 * Controlador para gestión de Profesores
 */
public class ProfesorController {

	private final Firestore db;
	private static final String COLLECTION = "profesores";

	public ProfesorController() {
		this.db = DBConnection.getFirestore();
	}

	public boolean crear(Profesor profesor) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document();
			profesor.setId(docRef.getId());
			ApiFuture<WriteResult> result = docRef.set(profesorToMap(profesor));
			result.get();
			System.out.println("✓ Profesor creado: " + profesor.getNombreCompleto());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al crear profesor: " + e.getMessage());
			return false;
		}
	}

	public List<Profesor> leerTodos() {
		List<Profesor> profesores = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				profesores.add(mapToProfesor(doc));
			}
			System.out.println("✓ Se encontraron " + profesores.size() + " profesores");
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return profesores;
	}

	public Profesor leerPorId(String id) {
		try {
			DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();
			if (doc.exists()) {
				return mapToProfesor(doc);
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return null;
	}

	public boolean actualizar(Profesor profesor) {
		try {
			db.collection(COLLECTION).document(profesor.getId()).update(profesorToMap(profesor)).get();
			System.out.println("✓ Profesor actualizado");
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
			return false;
		}
	}

	public boolean eliminar(String id) {
		try {
			db.collection(COLLECTION).document(id).delete().get();
			System.out.println("✓ Profesor eliminado");
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
			return false;
		}
	}

	public List<Profesor> buscarPorNombre(String nombre) {
		List<Profesor> resultados = new ArrayList<>();
		for (Profesor p : leerTodos()) {
			if (p.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase())) {
				resultados.add(p);
			}
		}
		return resultados;
	}

	public Profesor buscarPorDni(String dni) {
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("dni", dni).get().get();
			if (!snapshot.isEmpty()) {
				return mapToProfesor(snapshot.getDocuments().get(0));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return null;
	}

	public List<Profesor> filtrarPorDepartamento(String departamento) {
		List<Profesor> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("departamento", departamento).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToProfesor(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Profesor> filtrarPorEspecialidad(String especialidad) {
		List<Profesor> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("especialidad", especialidad).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToProfesor(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Profesor> filtrarPorTipoContrato(String tipo) {
		List<Profesor> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereEqualTo("tipoContrato", tipo).get().get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToProfesor(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Profesor> filtrarPorSalarioMinimo(double salarioMin) {
		List<Profesor> resultados = new ArrayList<>();
		try {
			QuerySnapshot snapshot = db.collection(COLLECTION).whereGreaterThanOrEqualTo("salario", salarioMin).get()
					.get();
			for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
				resultados.add(mapToProfesor(doc));
			}
		} catch (Exception e) {
			System.err.println("✗ Error: " + e.getMessage());
		}
		return resultados;
	}

	public List<Profesor> filtrarPorExperiencia(int añosMinimos) {
		List<Profesor> resultados = new ArrayList<>();
		for (Profesor p : leerTodos()) {
			if (p.getAñosExperiencia() >= añosMinimos) {
				resultados.add(p);
			}
		}
		return resultados;
	}

	private Map<String, Object> profesorToMap(Profesor p) {
		Map<String, Object> data = new HashMap<>();
		data.put("id", p.getId());
		data.put("nombre", p.getNombre());
		data.put("apellidos", p.getApellidos());
		data.put("dni", p.getDni());
		data.put("email", p.getEmail());
		data.put("telefono", p.getTelefono());
		data.put("fechaNacimiento", p.getFechaNacimiento());
		data.put("especialidad", p.getEspecialidad());
		data.put("departamento", p.getDepartamento());
		data.put("tipoContrato", p.getTipoContrato());
		data.put("fechaContratacion", p.getFechaContratacion());
		data.put("salario", p.getSalario());
		data.put("titulacion", p.getTitulacion());
		data.put("añosExperiencia", p.getAñosExperiencia());
		data.put("activo", p.isActivo());
		return data;
	}

	private Profesor mapToProfesor(DocumentSnapshot doc) {
		Profesor p = new Profesor();
		p.setId(doc.getId());
		p.setNombre(doc.getString("nombre"));
		p.setApellidos(doc.getString("apellidos"));
		p.setDni(doc.getString("dni"));
		p.setEmail(doc.getString("email"));
		p.setTelefono(doc.getString("telefono"));
		p.setFechaNacimiento(doc.getDate("fechaNacimiento"));
		p.setEspecialidad(doc.getString("especialidad"));
		p.setDepartamento(doc.getString("departamento"));
		p.setTipoContrato(doc.getString("tipoContrato"));
		p.setFechaContratacion(doc.getDate("fechaContratacion"));
		p.setSalario(doc.getDouble("salario") != null ? doc.getDouble("salario") : 0.0);
		p.setTitulacion(doc.getString("titulacion"));
		p.setAñosExperiencia(doc.getLong("añosExperiencia") != null ? doc.getLong("añosExperiencia").intValue() : 0);
		p.setActivo(doc.getBoolean("activo") != null ? doc.getBoolean("activo") : true);
		return p;
	}
}
