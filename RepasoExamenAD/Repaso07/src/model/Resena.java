package model;

public class Resena {
	private String usuario;
	private int puntuacion;
	private String fecha;
	private String comentario;

	public Resena() {
	}

	public Resena(String usuario, int puntuacion, String fecha, String comentario) {
		this.usuario = usuario;
		this.puntuacion = puntuacion;
		this.fecha = fecha;
		this.comentario = comentario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		return String.format("%s ★%d (%s): %s", usuario, puntuacion, fecha, comentario);
	}
}
