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

import model.Curso;

/**
 * Controlador SUPER COMPLETO para gestión de Cursos Incluye CRUD completo +
 * múltiples filtros y búsquedas avanzadas
 */
public class CursoController {

	private final Firestore db;
	private static final String COLLECTION = "cursos";

	public CursoController() {
		this.db = DBConnection.getFirestore();
	}

	// ==================== OPERACIONES CRUD BÁSICAS ====================

	/**
	 * Crear un nuevo curso
	 */
	public boolean crear(Curso curso) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document();
			curso.setId(docRef.getId());

			Map<String, Object> data = cursoToMap(curso);
			ApiFuture<WriteResult> result = docRef.set(data);
			result.get();

			System.out.println("✓ Curso creado con ID: " + curso.getId());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al crear curso: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Leer todos los cursos
	 */
	public List<Curso> leerTodos() {
		List<Curso> cursos = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Curso curso = mapToCurso(doc);
				if (curso != null) {
					cursos.add(curso);
				}
			}
			System.out.println("✓ Se encontraron " + cursos.size() + " cursos");
		} catch (Exception e) {
			System.err.println("✗ Error al leer cursos: " + e.getMessage());
		}
		return cursos;
	}

	/**
	 * Leer curso por ID
	 */
	public Curso leerPorId(String id) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document(id);
			ApiFuture<DocumentSnapshot> future = docRef.get();
			DocumentSnapshot document = future.get();

			if (document.exists()) {
				Curso curso = mapToCurso(document);
				System.out.println("✓ Curso encontrado: " + curso.getNombre());
				return curso;
			} else {
				System.out.println("✗ No se encontró curso con ID: " + id);
			}
		} catch (Exception e) {
			System.err.println("✗ Error al leer curso: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Actualizar un curso existente
	 */
	public boolean actualizar(Curso curso) {
		try {
			DocumentReference docRef = db.collection(COLLECTION).document(curso.getId());
			Map<String, Object> data = cursoToMap(curso);
			ApiFuture<WriteResult> result = docRef.update(data);
			result.get();

			System.out.println("✓ Curso actualizado: " + curso.getNombre());
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al actualizar curso: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Eliminar un curso
	 */
	public boolean eliminar(String id) {
		try {
			ApiFuture<WriteResult> result = db.collection(COLLECTION).document(id).delete();
			result.get();
			System.out.println("✓ Curso eliminado con ID: " + id);
			return true;
		} catch (Exception e) {
			System.err.println("✗ Error al eliminar curso: " + e.getMessage());
			return false;
		}
	}

	// ==================== BÚSQUEDAS Y FILTROS AVANZADOS ====================

	/**
	 * Buscar cursos por nombre (coincidencia parcial)
	 */
	public List<Curso> buscarPorNombre(String nombre) {
		List<Curso> resultados = new ArrayList<>();
		try {
			List<Curso> todos = leerTodos();
			String nombreLower = nombre.toLowerCase();

			for (Curso curso : todos) {
				if (curso.getNombre().toLowerCase().contains(nombreLower)) {
					resultados.add(curso);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " cursos con nombre: " + nombre);
		} catch (Exception e) {
			System.err.println("✗ Error en búsqueda por nombre: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Buscar curso por código
	 */
	public Curso buscarPorCodigo(String codigo) {
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("codigo", codigo).get();
			QuerySnapshot querySnapshot = future.get();

			if (!querySnapshot.isEmpty()) {
				DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
				Curso curso = mapToCurso(doc);
				System.out.println("✓ Curso encontrado con código: " + codigo);
				return curso;
			} else {
				System.out.println("✗ No se encontró curso con código: " + codigo);
			}
		} catch (Exception e) {
			System.err.println("✗ Error al buscar por código: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Filtrar cursos por nivel (BASICO, MEDIO, SUPERIOR)
	 */
	public List<Curso> filtrarPorNivel(String nivel) {
		List<Curso> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("nivel", nivel).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Curso curso = mapToCurso(doc);
				if (curso != null) {
					resultados.add(curso);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " cursos de nivel: " + nivel);
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por nivel: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar cursos por turno (MAÑANA, TARDE, NOCHE)
	 */
	public List<Curso> filtrarPorTurno(String turno) {
		List<Curso> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("turno", turno).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Curso curso = mapToCurso(doc);
				if (curso != null) {
					resultados.add(curso);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " cursos de turno: " + turno);
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por turno: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar cursos activos/inactivos
	 */
	public List<Curso> filtrarPorEstado(boolean activo) {
		List<Curso> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("activo", activo).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Curso curso = mapToCurso(doc);
				if (curso != null) {
					resultados.add(curso);
				}
			}
			System.out
					.println("✓ Se encontraron " + resultados.size() + " cursos " + (activo ? "activos" : "inactivos"));
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por estado: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar cursos con plazas disponibles
	 */
	public List<Curso> filtrarConPlazasDisponibles() {
		List<Curso> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereGreaterThan("plazasDisponibles", 0).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Curso curso = mapToCurso(doc);
				if (curso != null) {
					resultados.add(curso);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " cursos con plazas disponibles");
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar cursos con plazas: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar cursos por rango de precio
	 */
	public List<Curso> filtrarPorPrecio(double precioMin, double precioMax) {
		List<Curso> resultados = new ArrayList<>();
		try {
			List<Curso> todos = leerTodos();
			for (Curso curso : todos) {
				if (curso.getPrecio() >= precioMin && curso.getPrecio() <= precioMax) {
					resultados.add(curso);
				}
			}
			System.out.println(
					"✓ Se encontraron " + resultados.size() + " cursos entre " + precioMin + "€ y " + precioMax + "€");
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por precio: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar cursos por duración mínima (horas)
	 */
	public List<Curso> filtrarPorDuracion(int horasMinimas) {
		List<Curso> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION)
					.whereGreaterThanOrEqualTo("duracionHoras", horasMinimas).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Curso curso = mapToCurso(doc);
				if (curso != null) {
					resultados.add(curso);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " cursos con >= " + horasMinimas + " horas");
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por duración: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Filtrar cursos por coordinador
	 */
	public List<Curso> filtrarPorCoordinador(String idCoordinador) {
		List<Curso> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).whereEqualTo("idCoordinador", idCoordinador)
					.get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Curso curso = mapToCurso(doc);
				if (curso != null) {
					resultados.add(curso);
				}
			}
			System.out.println("✓ Se encontraron " + resultados.size() + " cursos del coordinador");
		} catch (Exception e) {
			System.err.println("✗ Error al filtrar por coordinador: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Obtener cursos más caros (top N)
	 */
	public List<Curso> obtenerCursosMasCaros(int limite) {
		List<Curso> resultados = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).orderBy("precio", Query.Direction.DESCENDING)
					.limit(limite).get();
			QuerySnapshot querySnapshot = future.get();

			for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
				Curso curso = mapToCurso(doc);
				if (curso != null) {
					resultados.add(curso);
				}
			}
			System.out.println("✓ Top " + limite + " cursos más caros obtenidos");
		} catch (Exception e) {
			System.err.println("✗ Error al obtener cursos más caros: " + e.getMessage());
		}
		return resultados;
	}

	/**
	 * Obtener cursos con mayor ocupación
	 */
	public List<Curso> obtenerCursosMayorOcupacion(int limite) {
		List<Curso> resultados = new ArrayList<>();
		try {
			List<Curso> todos = leerTodos();
			todos.sort((c1, c2) -> Double.compare(c2.getPorcentajeOcupacion(), c1.getPorcentajeOcupacion()));

			for (int i = 0; i < Math.min(limite, todos.size()); i++) {
				resultados.add(todos.get(i));
			}
			System.out.println("✓ Top " + limite + " cursos con mayor ocupación obtenidos");
		} catch (Exception e) {
			System.err.println("✗ Error al obtener cursos con mayor ocupación: " + e.getMessage());
		}
		return resultados;
	}

	// ==================== MÉTODOS AUXILIARES ====================

	/**
	 * Convierte un Curso a Map para Firestore
	 */
	private Map<String, Object> cursoToMap(Curso curso) {
		Map<String, Object> data = new HashMap<>();
		data.put("id", curso.getId());
		data.put("codigo", curso.getCodigo());
		data.put("nombre", curso.getNombre());
		data.put("descripcion", curso.getDescripcion());
		data.put("duracionHoras", curso.getDuracionHoras());
		data.put("nivel", curso.getNivel());
		data.put("turno", curso.getTurno());
		data.put("plazasDisponibles", curso.getPlazasDisponibles());
		data.put("plazasTotales", curso.getPlazasTotales());
		data.put("fechaInicio", curso.getFechaInicio());
		data.put("fechaFin", curso.getFechaFin());
		data.put("precio", curso.getPrecio());
		data.put("idCoordinador", curso.getIdCoordinador());
		data.put("activo", curso.isActivo());
		return data;
	}

	/**
	 * Convierte un DocumentSnapshot a Curso
	 */
	private Curso mapToCurso(DocumentSnapshot doc) {
		try {
			Curso curso = new Curso();
			curso.setId(doc.getId());
			curso.setCodigo(doc.getString("codigo"));
			curso.setNombre(doc.getString("nombre"));
			curso.setDescripcion(doc.getString("descripcion"));

			Long duracion = doc.getLong("duracionHoras");
			curso.setDuracionHoras(duracion != null ? duracion.intValue() : 0);

			curso.setNivel(doc.getString("nivel"));
			curso.setTurno(doc.getString("turno"));

			Long plazasDisp = doc.getLong("plazasDisponibles");
			curso.setPlazasDisponibles(plazasDisp != null ? plazasDisp.intValue() : 0);

			Long plazasTot = doc.getLong("plazasTotales");
			curso.setPlazasTotales(plazasTot != null ? plazasTot.intValue() : 0);

			curso.setFechaInicio(doc.getDate("fechaInicio"));
			curso.setFechaFin(doc.getDate("fechaFin"));

			Double precio = doc.getDouble("precio");
			curso.setPrecio(precio != null ? precio : 0.0);

			curso.setIdCoordinador(doc.getString("idCoordinador"));

			Boolean activo = doc.getBoolean("activo");
			curso.setActivo(activo != null ? activo : true);

			return curso;
		} catch (Exception e) {
			System.err.println("✗ Error al convertir documento a Curso: " + e.getMessage());
			return null;
		}
	}
}
