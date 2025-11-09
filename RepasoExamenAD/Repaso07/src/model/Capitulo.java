package model;

public class Capitulo {
	private int numero;
	private String titulo;
	private int paginas;
	private String resumen;

	public Capitulo() {
	}

	public Capitulo(int numero, String titulo, int paginas, String resumen) {
		this.numero = numero;
		this.titulo = titulo;
		this.paginas = paginas;
		this.resumen = resumen;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	@Override
	public String toString() {
		return String.format("Cap. %d: %s (%d págs)", numero, titulo, paginas);
	}
}
