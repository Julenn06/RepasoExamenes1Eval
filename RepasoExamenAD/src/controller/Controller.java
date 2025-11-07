package controller;

import com.google.cloud.firestore.Firestore;

import view.FirstView;

public class Controller {
	private static Controller instance;
	private FirstView firstView;

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

	public FirstView getFirstView(Boolean connect) {
		if (firstView == null) {
			firstView = new FirstView();
		}
		return firstView;
	}

	public boolean isOnline() {
		return DBConnection.getFirestore() != null;
	}
}