namespace Repaso01.Models;

public class Libro
{
    public int Id { get; set; }
    public string Titulo { get; set; }
    public string Autor { get; set; }
    public int Paginas { get; set; }
    public int AñoPublicacion { get; set; }
    public double Valoracion { get; set; }

    public Libro(int id, string titulo, string autor, int paginas, int añoPublicacion, double valoracion)
    {
        Id = id;
        Titulo = titulo;
        Autor = autor;
        Paginas = paginas;
        AñoPublicacion = añoPublicacion;
        Valoracion = valoracion;
    }

    public override string ToString()
    {
        return $"{Titulo} - {Autor} ({AñoPublicacion}) - {Paginas}p - ★{Valoracion}";
    }
}
