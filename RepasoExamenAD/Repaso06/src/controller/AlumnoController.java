package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import model.Alumno;

/**
 * Controlador SUPER COMPLETO para gestión de Alumnos Incluye CRUD completo +
 * múltiples filtros y búsquedas avanzadas
 */
public class AlumnoController {

	private final Firestore db;
	private static final String COLLECTION = "alumnos";

	public AlumnoController() {
		this.db = DBConnection.getFirestore();
	}

	// ==================== OPERACIONES CRUD BÁSICAS ====================

	/**
	 * Crear un nuevo alumno en Firestore
	 */
	public boolean crear(Alumno alumno) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document();
			alumno.setId(docRef.getId());

			Map<String, Object> data = alumnoToMap(alumno);
			ApiFuture<WriteResult> result = docRef.set(data);
			result.get();

			System.out.println("✓ Alumno creado con ID: " + alumno.getId());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al crear alumno: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Leer todos los alumnos
	 */
	public List<Alumno> leerTodos() {
		List<Alumno> alumnos = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Alumno alumno = mapToAlumno(doc);
				if (alumno != null) {
					alumnos.add(alumno);
				}
			}
			System.out.println("✓ Se encontraron " + alumnos.size() + " alumnos");
		} catch (Exception e) {
			System.err.println("✗ Error al leer alumnos: " + e.getMessage());
		}
		return alumnos;
	}

	/**
	 * Leer alumno por ID
	 */
	public Alumno leerPorId(String id) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document(id);
			ApiFuture<DocumentSnapshot> future = docRef.get();
			DocumentSnapshot document = future.get();

			if (document.exists()) {
				Alumno alumno = mapToAlumno(document);
				System.out.println("✓ Alumno encontrado: " + alumno.getNombreCompleto());
				return alumno;
			} else {
				System.out.println("✗ No se encontró alumno con ID: " + id);
			}
		} catch (Exception e) {
			System.err.println("✗ Error al leer alumno: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Actualizar un alumno existente
	 */
	public boolean actualizar(Alumno alumno) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document(alumno.getId());
			Map<String, Object> data = alumnoToMap(alumno);
			ApiFuture<WriteResult> result = docRef.update(data);
			result.get();

			System.out.println("✓ Alumno actualizado: " + alumno.getNombreCompleto());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al actualizar alumno: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Eliminar un alumno
	 */
	public boolean eliminar(String id) {
		try {
			ApiFuture<WriteResult> result = db.collection(COLLECTION).document(id).delete();
			result.get();
			System.out.println("✓ Alumno eliminado con ID: " + id);
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al eliminar alumno: " + e.getMessage());
			return false;
		}
	}

	// ==================== BÚSQUEDAS Y FILTROS AVANZADOS ====================

	/**
	 * Buscar alumnos por nombre (coincidencia parcial)
	 */
	public List<Alumno> buscarPorNombre(String nombre) {
		List<Alumno> resultados = new ArrayList<>();
		try {
			List<Alumno> todos = leerTodos();
			String nombreLower = nombre.toLowerCase();

			for (Alumno alumno : todos) {
				if (alumno.getNombre().toLowerCase().contains(nombreLower)
						|| alumno.getApellidos().toLowerCase().contains(nombreLower)) {
					resultados.add(alumno);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " alumnos con nombre: " + nombre);
		} catch (Exception e) {
			System.err.println("✗ Error en búsqueda por nombre: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Buscar alumno por DNI
	 */
	public Alumno buscarPorDni(String dni) {
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("dni", dni).get();
			QuerySnapshot querySnapshot = future.get();

			if (!querySnapshot.isEmpty()) {
				DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
				Alumno alumno = mapToAlumno(doc);
				System.out.println("✓ Alumno encontrado con DNI: " + dni);
				return alumno;
			} else {
				System.out.println("✗ No se encontró alumno con DNI: " + dni);
			}
		} catch (Exception e) {
			System.err.println("✗ Error al buscar por DNI: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Buscar alumno por email
	 */
	public Alumno buscarPorEmail(String email) {
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("email", email).get();
			QuerySnapshot querySnapshot = future.get();

			if (!querySnapshot.isEmpty()) {
				DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
				Alumno alumno = mapToAlumno(doc);
				System.out.println("✓ Alumno encontrado con email: " + email);
				return alumno;
			} else {
				System.out.println("✗ No se encontró alumno con email: " + email);
			}
		} catch (Exception e) {
			System.err.println("✗ Error al buscar por email: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Filtrar alumnos por curso
	 */
	public List<Alumno> filtrarPorCurso(String idCurso) {
		List<Alumno> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("idCurso", idCurso).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Alumno alumno = mapToAlumno(doc);
				if (alumno != null) {
					resultados.add(alumno);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " alumnos en el curso");
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por curso: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar alumnos por ciudad
	 */
	public List<Alumno> filtrarPorCiudad(String ciudad) {
		List<Alumno> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("ciudad", ciudad).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Alumno alumno = mapToAlumno(doc);
				if (alumno != null) {
					resultados.add(alumno);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " alumnos en " + ciudad);
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por ciudad: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar alumnos por estado (ACTIVO, BAJA, SUSPENDIDO)
	 */
	public List<Alumno> filtrarPorEstado(String estado) {
		List<Alumno> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("estado", estado).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Alumno alumno = mapToAlumno(doc);
				if (alumno != null) {
					resultados.add(alumno);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " alumnos con estado: " + estado);
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por estado: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar alumnos por edad (rango)
	 */
	public List<Alumno> filtrarPorEdad(int edadMin, int edadMax) {
		List<Alumno> resultados = new ArrayList<>();
		try {
			List<Alumno> todos = leerTodos();
			for (Alumno alumno : todos) {
				int edad = alumno.getEdad();
				if (edad >= edadMin && edad <= edadMax) {
					resultados.add(alumno);
				}
			}
			System.out.println(
					"✓ Se encontraron " + resultados.size() + " alumnos entre " + edadMin + " y " + edadMax + " años");
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por edad: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar alumnos por nota media (mínima)
	 */
	public List<Alumno> filtrarPorNotaMedia(double notaMinima) {
		List<Alumno> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION)
					.whereGreaterThanOrEqualTo("notaMedia", notaMinima).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Alumno alumno = mapToAlumno(doc);
				if (alumno != null) {
					resultados.add(alumno);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " alumnos con nota >= " + notaMinima);
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por nota media: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar alumnos por créditos aprobados (mínimo)
	 */
	public List<Alumno> filtrarPorCreditos(int creditosMinimos) {
		List<Alumno> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION)
					.whereGreaterThanOrEqualTo("creditosAprobados", creditosMinimos).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Alumno alumno = mapToAlumno(doc);
				if (alumno != null) {
					resultados.add(alumno);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " alumnos con >= " + creditosMinimos
					+ " créditos aprobados");
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por créditos: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Obtener top N alumnos por nota media
	 */
	public List<Alumno> obtenerTopAlumnos(int limite) {
		List<Alumno> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).orderBy("notaMedia", Query.Direction.DESCENDING)
					.limit(limite).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Alumno alumno = mapToAlumno(doc);
				if (alumno != null) {
					resultados.add(alumno);
				}
			}
			System.out.println("✓ Top " + limite + " alumnos obtenidos");
		} catch (Exception e) {
			System.err.println("✗ Error al obtener top alumnos: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Buscar alumnos sin curso asignado
	 */
	public List<Alumno> buscarSinCurso() {
		List<Alumno> resultados = new ArrayList<>();
		try {
			List<Alumno> todos = leerTodos();
			for (Alumno alumno : todos) {
				if (alumno.getIdCurso() == null || alumno.getIdCurso().isEmpty()) {
					resultados.add(alumno);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " alumnos sin curso asignado");
		} catch (Exception e) {
			System.err.println("✗ Error al buscar alumnos sin curso: " + e.getMessage());
		}
		return resultados;
	}

	// ==================== MÉTODOS AUXILIARES ====================

	/**
	 * Convierte un Alumno a Map para Firestore
	 */
	private Map<String, Object> alumnoToMap(Alumno alumno) {
		Map<String, Object> data = new HashMap<>();
		data.put("id", alumno.getId());
		data.put("nombre", alumno.getNombre());
		data.put("apellidos", alumno.getApellidos());
		data.put("dni", alumno.getDni());
		data.put("email", alumno.getEmail());
		data.put("telefono", alumno.getTelefono());
		data.put("fechaNacimiento", alumno.getFechaNacimiento());
		data.put("direccion", alumno.getDireccion());
		data.put("ciudad", alumno.getCiudad());
		data.put("codigoPostal", alumno.getCodigoPostal());
		data.put("idCurso", alumno.getIdCurso());
		data.put("estado", alumno.getEstado());
		data.put("fechaMatriculacion", alumno.getFechaMatriculacion());
		data.put("notaMedia", alumno.getNotaMedia());
		data.put("creditosAprobados", alumno.getCreditosAprobados());
		return data;
	}

	/**
	 * Convierte un DocumentSnapshot a Alumno
	 */
	private Alumno mapToAlumno(DocumentSnapshot doc) {
		try {
			Alumno alumno = new Alumno();
			alumno.setId(doc.getId());
			alumno.setNombre(doc.getString("nombre"));
			alumno.setApellidos(doc.getString("apellidos"));
			alumno.setDni(doc.getString("dni"));
			alumno.setEmail(doc.getString("email"));
			alumno.setTelefono(doc.getString("telefono"));
			alumno.setFechaNacimiento(doc.getDate("fechaNacimiento"));
			alumno.setDireccion(doc.getString("direccion"));
			alumno.setCiudad(doc.getString("ciudad"));
			alumno.setCodigoPostal(doc.getString("codigoPostal"));
			alumno.setIdCurso(doc.getString("idCurso"));
			alumno.setEstado(doc.getString("estado"));
			alumno.setFechaMatriculacion(doc.getDate("fechaMatriculacion"));

			Double notaMedia = doc.getDouble("notaMedia");
			alumno.setNotaMedia(notaMedia != null ? notaMedia : 0.0);

			Long creditos = doc.getLong("creditosAprobados");
			alumno.setCreditosAprobados(creditos != null ? creditos.intValue() : 0);

			return alumno;
		} catch (Exception e) {
			System.err.println("✗ Error al convertir documento a Alumno: " + e.getMessage());
			return null;
		}
	}
}
