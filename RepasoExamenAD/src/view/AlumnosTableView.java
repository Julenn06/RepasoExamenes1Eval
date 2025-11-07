package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import controller.DBConnection;
import model.Alumnos;

public class AlumnosTableView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	// keep list of document ids aligned with rows
	private final List<String> docIds = new ArrayList<>();

	public AlumnosTableView() {
		setTitle("Listado de Alumnos");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		model = new DefaultTableModel(new Object[] { "ID", "Nombre", "Edad" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		// hide ID column visually
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);

		add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnRefresh = new JButton("Refrescar");
		JButton btnAdd = new JButton("Añadir");
		JButton btnEdit = new JButton("Editar");
		JButton btnDelete = new JButton("Eliminar");
		JButton btnClose = new JButton("Cerrar");

		btnRefresh.addActionListener((ActionEvent e) -> loadData());

		btnAdd.addActionListener((ActionEvent e) -> {
			AlumnoDialog dlg = new AlumnoDialog(this, null, null, () -> loadData());
			dlg.setVisible(true);
		});

		btnEdit.addActionListener((ActionEvent e) -> {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona una fila para editar", "Info",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String id = docIds.get(row);
			String name = (String) model.getValueAt(row, 1);
			Integer age = (Integer) model.getValueAt(row, 2);
			AlumnoDialog dlg = new AlumnoDialog(this, id, new Alumnos(name, age == null ? 0 : age), () -> loadData());
			dlg.setVisible(true);
		});

		btnDelete.addActionListener((ActionEvent e) -> {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar", "Info",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String id = docIds.get(row);
			int opt = JOptionPane.showConfirmDialog(this, "¿Eliminar alumno seleccionado?", "Confirmar",
					JOptionPane.YES_NO_OPTION);
			if (opt == JOptionPane.YES_OPTION) {
				new Thread(() -> {
					try {
						DBConnection.getFirestore().collection("alumnos").document(id).delete().get();
					} catch (Exception ex) {
						ex.printStackTrace();
						SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
								"Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
					}
					SwingUtilities.invokeLater(() -> loadData());
				}).start();
			}
		});

		btnClose.addActionListener((ActionEvent e) -> dispose());

		buttons.add(btnRefresh);
		buttons.add(btnAdd);
		buttons.add(btnEdit);
		buttons.add(btnDelete);
		buttons.add(btnClose);

		add(buttons, BorderLayout.SOUTH);

		// initial load
		loadData();
	}

	private void loadData() {
		model.setRowCount(0);
		docIds.clear();

		new Thread(() -> {
			try {
				QuerySnapshot querySnapshot = DBConnection.getFirestore().collection("alumnos").get().get();
				for (QueryDocumentSnapshot doc : querySnapshot.getDocuments()) {
					String name = doc.getString("name");
					Long ageLong = doc.getLong("age");
					int age = ageLong != null ? ageLong.intValue() : 0;
					docIds.add(doc.getId());
					Object[] row = new Object[] { doc.getId(), name, age };
					SwingUtilities.invokeLater(() -> model.addRow(row));
				}
			} catch (Exception e) {
				e.printStackTrace();
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
						"Error al cargar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
			}
		}).start();
	}

	public void loadFromXML() {
		model.setRowCount(0);
		docIds.clear();

		new Thread(() -> {
			try {
				File file = new File("alumnos.xml");
				if (!file.exists() || file.length() == 0) {
					SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
							"No hay archivo XML disponible", "Info", JOptionPane.INFORMATION_MESSAGE));
					return;
				}

				Document docXML = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
				docXML.getDocumentElement().normalize();

				NodeList listaAlumnos = docXML.getElementsByTagName("Alumno");

				for (int i = 0; i < listaAlumnos.getLength(); i++) {
					Node nodo = listaAlumnos.item(i);
					if (nodo.getNodeType() == Node.ELEMENT_NODE) {
						Element elemento = (Element) nodo;
						String nombre = elemento.getElementsByTagName("Name").item(0).getTextContent();
						int edad = Integer.parseInt(elemento.getElementsByTagName("Age").item(0).getTextContent());
						docIds.add("");
						Object[] row = new Object[] { "", nombre, edad };
						SwingUtilities.invokeLater(() -> model.addRow(row));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
						"Error al leer XML: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
			}
		}).start();
	}

	public void loadFromDAT() {
		model.setRowCount(0);
		docIds.clear();

		new Thread(() -> {
			try {
				File file = new File("alumnos.dat");
				if (!file.exists() || file.length() == 0) {
					SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
							"No hay archivo DAT disponible", "Info", JOptionPane.INFORMATION_MESSAGE));
					return;
				}

				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
					Object obj = ois.readObject();
					if (!(obj instanceof List<?>)) {
						SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
								"Contenido DAT inesperado", "Error", JOptionPane.ERROR_MESSAGE));
						return;
					}
					List<?> rawList = (List<?>) obj;
					for (Object item : rawList) {
						if (item instanceof Alumnos) {
							Alumnos a = (Alumnos) item;
							docIds.add("");
							Object[] row = new Object[] { "", a.getName(), a.getAge() };
							SwingUtilities.invokeLater(() -> model.addRow(row));
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
						"Error al leer DAT: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
			}
		}).start();
	}

}
