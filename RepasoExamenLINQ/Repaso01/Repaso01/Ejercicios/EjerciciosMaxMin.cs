using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosMaxMin
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Libro> libros)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: MAX() / MIN()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: Edad máxima y mínima de estudiantes
        Console.WriteLine("Ejercicio 1: Edad máxima y mínima de estudiantes:");
        int edadMaxima = estudiantes.Max(e => e.Edad);
        int edadMinima = estudiantes.Min(e => e.Edad);
        Console.WriteLine($"  Máxima: {edadMaxima} años");
        Console.WriteLine($"  Mínima: {edadMinima} años\n");

        // Ejercicio 2: Libro con más y menos páginas
        Console.WriteLine("Ejercicio 2: Libro con más y menos páginas:");
        int maxPaginas = libros.Max(l => l.Paginas);
        int minPaginas = libros.Min(l => l.Paginas);
        var libroMasLargo = libros.First(l => l.Paginas == maxPaginas);
        var libroMasCorto = libros.First(l => l.Paginas == minPaginas);
        Console.WriteLine($"  Más largo: {libroMasLargo.Titulo} ({maxPaginas}p)");
        Console.WriteLine($"  Más corto: {libroMasCorto.Titulo} ({minPaginas}p)\n");

        // Ejercicio 3: Nota máxima y mínima de todos los estudiantes
        Console.WriteLine("Ejercicio 3: Nota máxima y mínima general:");
        double notaMax = estudiantes.Max(e => e.NotaMedia);
        double notaMin = estudiantes.Min(e => e.NotaMedia);
        Console.WriteLine($"  Nota máxima: {notaMax}");
        Console.WriteLine($"  Nota mínima: {notaMin}\n");

        // Ejercicio 4: Mejor y peor valoración de libros
        Console.WriteLine("Ejercicio 4: Mejor y peor valoración de libros:");
        double mejorValoracion = libros.Max(l => l.Valoracion);
        double peorValoracion = libros.Min(l => l.Valoracion);
        var libroMejorValorado = libros.First(l => l.Valoracion == mejorValoracion);
        var libroPeorValorado = libros.First(l => l.Valoracion == peorValoracion);
        Console.WriteLine($"  Mejor: {libroMejorValorado.Titulo} ({mejorValoracion})");
        Console.WriteLine($"  Peor: {libroPeorValorado.Titulo} ({peorValoracion})\n");
    }
}
