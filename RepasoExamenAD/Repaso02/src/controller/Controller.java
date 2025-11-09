package controller;

import com.google.cloud.firestore.Firestore;

public class Controller {
	private static Controller instance;

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public static void initialize() {
		if (instance == null) {
			instance = new Controller();
		}
	}

	public Controller() {
	}

	public Firestore getDb() {
		return DBConnection.getFirestore();
	}

	public boolean isOnline() {
		return DBConnection.getFirestore() != null;
	}
}