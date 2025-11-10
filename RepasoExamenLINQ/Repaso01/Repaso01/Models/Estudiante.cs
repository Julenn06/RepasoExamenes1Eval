namespace Repaso01.Models;

public class Estudiante
{
    public int Id { get; set; }
    public string Nombre { get; set; }
    public int Edad { get; set; }
    public double NotaMedia { get; set; }
    public bool Aprobado { get; set; }
    public string Carrera { get; set; }

    public Estudiante(int id, string nombre, int edad, double notaMedia, bool aprobado, string carrera)
    {
        Id = id;
        Nombre = nombre;
        Edad = edad;
        NotaMedia = notaMedia;
        Aprobado = aprobado;
        Carrera = carrera;
    }

    public override string ToString()
    {
        return $"{Nombre} ({Edad} años) - Nota: {NotaMedia} - {(Aprobado ? "APROBADO" : "SUSPENSO")} - {Carrera}";
    }
}
