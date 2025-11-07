package controller;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class DBConnection {

	private static Firestore firestoreInstance = null;

	public static Firestore initialize() {
		if (firestoreInstance != null) {
			return firestoreInstance;
		}

		try {
			if (FirebaseApp.getApps().isEmpty()) {
				FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

				FirebaseOptions options = FirebaseOptions.builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

				FirebaseApp.initializeApp(options);
				System.out.println("[INFO] Firebase conectado con éxito");
			}

			firestoreInstance = FirestoreClient.getFirestore();
			return firestoreInstance;

		} catch (IOException e) {
			System.err.println("[ERROR] Error al conectar con Firebase: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static Firestore getFirestore() {
		if (firestoreInstance == null) {
			return initialize();
		}
		return firestoreInstance;
	}
}