using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosFirstOrDefault
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Libro> libros)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: FIRSTORDEFAULT()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: Encontrar el primer estudiante con nota mayor a 8.5
        Console.WriteLine("Ejercicio 1: Primer estudiante con nota > 8.5:");
        var mejorEstudiante = estudiantes.FirstOrDefault(e => e.NotaMedia > 8.5);
        if (mejorEstudiante != null)
            Console.WriteLine($"  • {mejorEstudiante}\n");
        else
            Console.WriteLine("  No encontrado\n");

        // Ejercicio 2: Buscar el primer libro de más de 500 páginas
        Console.WriteLine("Ejercicio 2: Primer libro con más de 500 páginas:");
        var libroLargo = libros.FirstOrDefault(l => l.Paginas > 500);
        if (libroLargo != null)
            Console.WriteLine($"  • {libroLargo}\n");
        else
            Console.WriteLine("  No encontrado\n");

        // Ejercicio 3: Primer estudiante suspendido
        Console.WriteLine("Ejercicio 3: Primer estudiante suspendido:");
        var primerSuspenso = estudiantes.FirstOrDefault(e => !e.Aprobado);
        if (primerSuspenso != null)
            Console.WriteLine($"  • {primerSuspenso}\n");
        else
            Console.WriteLine("  No encontrado\n");

        // Ejercicio 4: Primer libro publicado antes de 1950
        Console.WriteLine("Ejercicio 4: Primer libro publicado antes de 1950:");
        var libroAntiguo = libros.FirstOrDefault(l => l.AñoPublicacion < 1950);
        if (libroAntiguo != null)
            Console.WriteLine($"  • {libroAntiguo}\n");
        else
            Console.WriteLine("  No encontrado\n");
    }
}
