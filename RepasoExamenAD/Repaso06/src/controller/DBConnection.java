package controller;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

/**
 * Clase singleton para manejar la conexión con Firebase Firestore
 */
public class DBConnection {

	private static Firestore db;

	/**
	 * Inicializa la conexión con Firebase usando el archivo serviceAccountKey.json
	 */
	public static void initialize() {
		try {
			if (FirebaseApp.getApps().isEmpty()) {
				FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

				FirebaseOptions options = FirebaseOptions.builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

				FirebaseApp.initializeApp(options);
				System.out.println("✓ Firebase inicializado correctamente");
			}
			db = FirestoreClient.getFirestore();
		} catch (IOException e) {
			System.err.println("✗ Error al inicializar Firebase: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la instancia de Firestore
	 * 
	 * @return Instancia de Firestore
	 */
	public static Firestore getFirestore() {
		if (db == null) {
			initialize();
		}
		return db;
	}
}
